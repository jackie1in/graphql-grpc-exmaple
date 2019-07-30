package com.bd.gateway.clients;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Message;
import net.badata.protobuf.converter.Converter;

import java.util.concurrent.Future;

public class RpcGraphqlConverter {
    public static <I extends Message, O> Future<O> protobufToGraphql(ListenableFuture<I> convertRpcResult, Class<O> domainClass) {
        return Futures.lazyTransform(convertRpcResult, in -> Converter.create().toDomain(domainClass, in));
    }
}
