/*
 * Copyright 2022 Webull
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webull.openapi.core.http;

import com.webull.openapi.core.auth.composer.DefaultSignatureComposer;
import com.webull.openapi.core.common.Headers;
import com.webull.openapi.core.config.ProjectReaderHelper;
import com.webull.openapi.core.execption.ClientException;
import com.webull.openapi.core.execption.ErrorCode;
import com.webull.openapi.core.execption.ServerException;
import com.webull.openapi.core.http.common.HttpStatus;
import com.webull.openapi.core.http.okhttp.OkHttpClientPool;
import com.webull.openapi.core.http.okhttp.OkHttpRequestBuilder;
import com.webull.openapi.core.http.retry.DefaultHttpRetryCondition;
import com.webull.openapi.core.http.retry.HttpRetryContext;
import com.webull.openapi.core.http.retry.SynchronousHttpRetryable;
import com.webull.openapi.core.logger.Logger;
import com.webull.openapi.core.logger.LoggerFactory;
import com.webull.openapi.core.retry.RetriedFailedException;
import com.webull.openapi.core.retry.RetryPolicy;
import com.webull.openapi.core.retry.Retryable;
import com.webull.openapi.core.retry.backoff.DefaultMixedBackoffStrategy;
import com.webull.openapi.core.serialize.JsonSerializer;
import com.webull.openapi.core.serialize.SerializeConfig;
import com.webull.openapi.core.utils.Assert;
import com.webull.openapi.core.utils.StringUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.function.Supplier;

public class HttpApiClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpApiClient.class);
    private final HttpApiConfig config;
    private final RetryPolicy retryPolicy;
    private final RuntimeOptions runtimeOptions;
    private String token;

    public HttpApiClient(HttpApiConfig config) {
        Assert.notNull("config", config);
        this.config = config;
        this.retryPolicy = this.config.getAutoRetry() ?
                new RetryPolicy(new DefaultHttpRetryCondition(this.config.getMaxRetries()), new DefaultMixedBackoffStrategy()) :
                RetryPolicy.never();
        this.runtimeOptions = config.getRuntimeOptions();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HttpApiConfig getConfig() {
        return config;
    }

    public Action request(HttpRequest httpRequest) {
        return new Action(httpRequest);
    }

    public class Action {

        private final HttpRequest request;
        private Type responseType;
        private SerializeConfig serializeConfig = SerializeConfig.httpDefault();

        private Action(HttpRequest request) {
            Assert.notNull("request", request);
            this.request = request;
        }

        public Action responseType(Type responseType) {
            Assert.notNull("responseType", responseType);
            this.responseType = responseType;
            return this;
        }

        public Action serializeWith(SerializeConfig serializeConfig) {
            Assert.notNull("serializeConfig", serializeConfig);
            this.serializeConfig = serializeConfig;
            return this;
        }

        public <T> T doAction() {
            Assert.notNull("responseType", responseType);
            try (HttpResponse response = doRequest(this.request)) {
                if (response.getException() != null) {
                    throw response.getException();
                } else {
                    String responseStr = response.getResponseBody();
                    if (logger.isTraceEnabled()) {
                        logger.trace("HTTP request uri={}, response body={}", this.request.getUri(), responseStr);
                    }
                    return JsonSerializer.fromJson(responseStr, this.responseType, this.serializeConfig);
                }
            }
        }

        public void doVoidAction() {
            try (HttpResponse response = doRequest(this.request)) {
                if (response.getException() != null) {
                    throw response.getException();
                }
            }
        }

        private HttpResponse doRequest(HttpRequest request) {
            Assert.notNull("request", request);
            request.setEndpointIfAbsent(config.getEndpoint());
            request.setPortIfAbsent(config.getPort());

            Supplier<HttpResponse> singleRequest = () -> this.doSingleRequest(request);
            HttpRetryContext retryContext;
            try {
                HttpResponse response = singleRequest.get();
                if (response.isSuccess()) {
                    return response;
                }
                retryContext = new HttpRetryContext(request.getUri(), request.getMethod(), response.getStatusCode(), 1,
                        response.getException());
            } catch (Exception ex) {
                retryContext = new HttpRetryContext(request.getUri(), request.getMethod(), HttpStatus.BAD_REQUEST, 1, ex);
            }
            Retryable<HttpResponse> retryable = new SynchronousHttpRetryable(singleRequest, retryPolicy);
            try {
                return retryable.retry(retryContext);
            } catch (RetriedFailedException ex) {
                // 异常情况用error，输出所有信息，反馈问题时提供所有信息
                logger.error("Http request error, Host:{}, SDK version:{}, Request:{}",
                        request.getEndpoint(), ProjectReaderHelper.getClientSDKInfo(), JsonSerializer.toJson(request), ex);
                if (ex.getCause() instanceof ClientException) {
                    throw (ClientException) ex.getCause();
                } else if (ex.getCause() instanceof ServerException) {
                    throw (ServerException) ex.getCause();
                } else {
                    throw new ClientException(ErrorCode.INVALID_REQUEST, ex.getCause());
                }
            }
        }

        private HttpResponse doSingleRequest(HttpRequest request) {
            String sign = DefaultSignatureComposer.getSign(request.getEndpoint(), config.getAppKey(), config.getAppSecret(), request);
            request.getHeaders().putIfAbsent(Headers.SIGNATURE, sign);
            request.getHeaders().putIfAbsent(Headers.VERSION, request.getVersion());
            if(StringUtils.isNotBlank(token)){
                request.getHeaders().putIfAbsent(Headers.ACCESS_TOKEN, token);
            }
            try {
                URL url = new URL(request.getURL());
                RuntimeOptions options = request.getRuntimeOptions() != null ?
                        request.getRuntimeOptions().parent(runtimeOptions) : runtimeOptions;
                OkHttpClient okHttpClient = OkHttpClientPool.get(url, options);
                Request okHttpRequest = OkHttpRequestBuilder.newRequest(request).url(url).header(request.getHeaders()).build();
                Response okHttpResponse = okHttpClient.newCall(okHttpRequest).execute();
                return new HttpResponse(okHttpResponse);
            } catch (Exception e) {
                // 异常情况用error，输出所有信息，反馈问题时提供所有信息
                logger.error("Http request error, Host:{}, SDK version:{}, Request:{}",
                        request.getEndpoint(), ProjectReaderHelper.getClientSDKInfo(), JsonSerializer.toJson(request), e);
                throw new ClientException(ErrorCode.INVALID_REQUEST, e);
            }
        }
    }
}
