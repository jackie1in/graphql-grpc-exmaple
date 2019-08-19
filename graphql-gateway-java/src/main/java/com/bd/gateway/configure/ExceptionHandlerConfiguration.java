package com.bd.gateway.configure;

import graphql.servlet.core.GenericGraphQLError;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
/**
 * @see com.oembedler.moon.graphql.boot.error.GraphQLErrorHandlerFactory
 */
public class ExceptionHandlerConfiguration {

	@ExceptionHandler(StatusRuntimeException.class)
	GenericGraphQLError handle(StatusRuntimeException e) {
		switch (e.getStatus().getCode()){
			case INVALID_ARGUMENT:
				return new GenericGraphQLError(e.getMessage());
			case UNAVAILABLE:
				return new GenericGraphQLError("服务不可用，请稍后再试！");
			default:
				return new GenericGraphQLError(e.getMessage());
		}
	}

	@ExceptionHandler(Throwable.class)
	GenericGraphQLError handle(Throwable e) {
		return new GenericGraphQLError(e.getMessage());
	}
}
