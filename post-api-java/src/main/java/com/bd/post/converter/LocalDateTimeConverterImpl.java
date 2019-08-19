package com.bd.post.converter;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import net.badata.protobuf.converter.type.TypeConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeConverterImpl implements TypeConverter<LocalDateTime, Timestamp> {
    @Override
    public LocalDateTime toDomainValue(Object instance) {
        if (!instance.equals(Timestamp.getDefaultInstance())){
            Timestamp timestamp = (Timestamp)instance;
            return LocalDateTime.ofEpochSecond(timestamp.getSeconds(),timestamp.getNanos(), ZoneOffset.of("+8"));
        }
        return null;
    }

    @Override
    public Timestamp toProtobufValue(Object instance) {
        if (instance != null){
            return Timestamps.fromNanos(((LocalDateTime)instance).toInstant(ZoneOffset.of("+8")).toEpochMilli() * 1000000 +((LocalDateTime)instance).getNano() );
        }
        return Timestamp.getDefaultInstance();
    }
}
