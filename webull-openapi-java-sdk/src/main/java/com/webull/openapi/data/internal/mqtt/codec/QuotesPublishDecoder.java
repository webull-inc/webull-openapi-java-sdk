package com.webull.openapi.data.internal.mqtt.codec;

import com.webull.openapi.data.quotes.domain.QuotesBasic;
import com.webull.openapi.data.quotes.subsribe.codec.Decoder;
import com.webull.openapi.data.quotes.subsribe.message.QuotesPublish;

import java.nio.ByteBuffer;

public interface QuotesPublishDecoder<T extends QuotesBasic> extends Decoder<ByteBuffer, QuotesPublish<T>> {
}
