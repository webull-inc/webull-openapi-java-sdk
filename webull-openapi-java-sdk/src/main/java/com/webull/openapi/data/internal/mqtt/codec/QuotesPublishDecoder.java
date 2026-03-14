package com.webull.openapi.data.internal.mqtt.codec;

import com.webull.openapi.data.quotes.domain.QuotesBasic;
import com.webull.openapi.data.quotes.subscribe.codec.Decoder;
import com.webull.openapi.data.quotes.subscribe.message.QuotesPublish;

import java.nio.ByteBuffer;

public interface QuotesPublishDecoder<T extends QuotesBasic> extends Decoder<ByteBuffer, QuotesPublish<T>> {
}
