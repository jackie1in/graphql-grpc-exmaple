package com.bd.gateway.configure;

import com.coxautodev.graphql.tools.SchemaParserOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.google.common.util.concurrent.ListenableFuture;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import com.spotify.futures.ListenableFuturesExtra;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class GraphqlToolConfiguration {
	@Bean
	SchemaParserOptions options(){
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
			.modules(new ProtobufModule(), new Jdk8Module(), new KotlinModule(), new JavaTimeModule())
			.build();
		return SchemaParserOptions.newOptions()
			.genericWrappers(SchemaParserOptions.GenericWrapper
				.withTransformer(ListenableFuture.class,0,ListenableFuturesExtra::toCompletableFuture, type -> type))
			.objectMapperProvider(fieldDefinition -> objectMapper )
			.useDefaultGenericWrappers(true)
			.build();
	}
	@Bean
	ExceptionHandlerConfiguration configuration(){
		return new ExceptionHandlerConfiguration();
	}
}
