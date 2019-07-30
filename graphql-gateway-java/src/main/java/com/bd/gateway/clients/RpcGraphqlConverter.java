package com.bd.gateway.clients;

import com.google.common.base.Function;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Message;
import com.spotify.futures.ListenableFuturesExtra;
import net.badata.protobuf.converter.Converter;

import java.util.concurrent.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class RpcGraphqlConverter {
    public static <I extends Message, O> CompletableFuture<O> protobufToGraphql(ListenableFuture<I> convertRpcResult, Class<O> domainClass) {
        return RpcGraphqlConverter.lazyTransform(convertRpcResult, in -> Converter.create().toDomain(domainClass, in));
    }

    public static <I, O> CompletableFuture<O> lazyTransform(
            final ListenableFuture<I> input, final Function<? super I, ? extends O> function) {
        checkNotNull(input);
        checkNotNull(function);
        return ListenableFuturesExtra.toCompletableFuture(input).thenApply(function);
    }
}
