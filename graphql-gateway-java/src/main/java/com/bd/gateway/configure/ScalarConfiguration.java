package com.bd.gateway.configure;

import com.bd.gateway.scalar.CustomScalars;
import graphql.schema.GraphQLScalarType;
import graphql.servlet.core.ApolloScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScalarConfiguration {
    @Bean
    public GraphQLScalarType uploadScalar(){
        return ApolloScalars.Upload;
    }

    @Bean
    public GraphQLScalarType dateTimeScalar(){
        return CustomScalars.DateTime;
    }
}
