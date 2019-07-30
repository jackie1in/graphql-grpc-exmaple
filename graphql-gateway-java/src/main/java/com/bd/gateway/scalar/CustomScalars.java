package com.bd.gateway.scalar;

import graphql.language.StringValue;
import graphql.schema.*;

import java.time.LocalDateTime;

public class CustomScalars {
    public static final GraphQLScalarType DateTime = GraphQLScalarType.newScalar()
            .name("DateTime")
            .coercing(new Coercing<LocalDateTime, String>() {
                private LocalDateTime convertImpl(Object input) {
                    if (input instanceof String) {
                        return DateTimeHelper.parseDate((String) input);
                    }
                    return null;
                }

                @Override
                public String serialize(Object input) {
                    if (input instanceof LocalDateTime) {
                        return DateTimeHelper.toUsualString((LocalDateTime) input);
                    } else {
                        LocalDateTime result = convertImpl(input);
                        if (result == null) {
                            throw new CoercingSerializeException("Invalid value '" + input + "' for DateTime");
                        }
                        return DateTimeHelper.toUsualString(result);
                    }
                }

                @Override
                public LocalDateTime parseValue(Object input) {
                    LocalDateTime result = convertImpl(input);
                    if (result == null) {
                        throw new CoercingParseValueException("Invalid value '" + input + "' for DateTime");
                    }
                    return result;
                }

                @Override
                public LocalDateTime parseLiteral(Object input) {
                    if (!(input instanceof StringValue)) return null;
                    String value = ((StringValue) input).getValue();
                    return convertImpl(value);
                }
            })
            .description("日期类型")
            .build();
}
