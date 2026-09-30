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
package com.webull.openapi.core.sse.internal.auth;

import com.webull.openapi.core.auth.composer.DefaultSignatureComposer;
import com.webull.openapi.core.auth.signer.SignAlgorithm;
import com.webull.openapi.core.auth.signer.Signer;
import com.webull.openapi.core.auth.signer.SignerFactory;
import com.webull.openapi.core.common.Headers;
import com.webull.openapi.core.utils.DateUtils;
import com.webull.openapi.core.utils.GUID;
import com.webull.openapi.core.utils.MD5Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Builds the authentication headers of a SSE subscribe request.
 *
 * <p>Unlike the gRPC channel, the body hash is calculated over the raw JSON bytes that are
 * actually sent, so the caller must pass the very same byte array to the request body.
 */
public final class SseSignatureHeaders {

    private static final Signer SIGNER = SignerFactory.getInstance().get(SignAlgorithm.HMAC_SHA1);

    private SseSignatureHeaders() {
    }

    /**
     * @param bodyBytes the exact request body bytes to be sent
     * @return the headers to apply, a fresh request id and nonce on every call
     */
    public static Map<String, String> build(String appKey, String appSecret, byte[] bodyBytes) {
        String signAlgorithm = SIGNER.signerName();
        String signVersion = SIGNER.signerVersion();
        String nonce = GUID.get();
        String timestamp = DateUtils.getTimestamp();

        // sign param key should be lowercase.
        Map<String, String> signParams = new HashMap<>();
        signParams.put(Headers.APP_KEY, appKey);
        signParams.put(Headers.SIGN_ALGORITHM, signAlgorithm);
        signParams.put(Headers.SIGN_VERSION, signVersion);
        signParams.put(Headers.NONCE, nonce);
        signParams.put(Headers.TIMESTAMP, timestamp);

        String payload = MD5Utils.md5(bodyBytes);
        String signature = DefaultSignatureComposer.getSign(signParams, null, payload, appSecret, SIGNER);

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(Headers.REQUEST_ID, GUID.get());
        headers.put(Headers.APP_KEY, appKey);
        headers.put(Headers.SIGN_ALGORITHM, signAlgorithm);
        headers.put(Headers.SIGN_VERSION, signVersion);
        headers.put(Headers.NONCE, nonce);
        headers.put(Headers.TIMESTAMP, timestamp);
        headers.put(Headers.SIGNATURE, signature);
        return headers;
    }
}
