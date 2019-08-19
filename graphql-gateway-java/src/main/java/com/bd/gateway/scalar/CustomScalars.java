package com.bd.gateway.scalar;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import graphql.language.StringValue;
import graphql.schema.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CustomScalars {
	public static final GraphQLScalarType DateTime = GraphQLScalarType.newScalar()
		.name("DateTime")
		.coercing(new Coercing<Timestamp, String>() {
			private Timestamp convertImpl(Object input) {
				if (input instanceof String) {
					return Timestamps.fromSeconds(DateTimeHelper.parseDate((String) input).toEpochSecond(ZoneOffset.of("+8")));
				}
				return null;
			}

			private LocalDateTime convert(Timestamp input) {
				if (!Timestamp.getDefaultInstance().equals(input)) {
					return LocalDateTime.ofEpochSecond(((Timestamp) input).getSeconds(), ((Timestamp) input).getNanos(), ZoneOffset.of("+8"));
				}
				return null;
			}

			@Override
			public String serialize(Object input) {
				if (input instanceof Timestamp) {
					if (Timestamp.getDefaultInstance().equals(input)){
						return null;
					}
					return DateTimeHelper.toUsualString(convert((Timestamp) input));
				}else {
					Timestamp result = convertImpl(input);
					if (result == null) {
						throw new CoercingSerializeException("Invalid value '" + input + "' for DateTime");
					}
					return DateTimeHelper.toUsualString(convert((Timestamp) input));
				}
			}

			@Override
			public Timestamp parseValue(Object input) {
				Timestamp result = convertImpl(input);
				if (result == null) {
					throw new CoercingParseValueException("Invalid value '" + input + "' for DateTime");
				}
				return result;
			}

			@Override
			public Timestamp parseLiteral(Object input) {
				if (!(input instanceof StringValue)) return null;
				String value = ((StringValue) input).getValue();
				return convertImpl(value);
			}
		})
		.description("日期类型")
		.build();
}
