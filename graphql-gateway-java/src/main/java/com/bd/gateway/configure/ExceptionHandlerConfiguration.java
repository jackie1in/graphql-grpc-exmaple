package com.bd.gateway.configure;

import com.oembedler.moon.graphql.boot.error.ThrowableGraphQLError;
import graphql.GraphQLError;
import io.grpc.StatusRuntimeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerConfiguration {

	@ExceptionHandler(StatusRuntimeException.class)
	ThrowableGraphQLError handle(StatusRuntimeException e) {
		return new ThrowableGraphQLError(e, e.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	GraphQLError handle(Throwable e) {
		return new ThrowableGraphQLError(e, e.getMessage());
	}
}
