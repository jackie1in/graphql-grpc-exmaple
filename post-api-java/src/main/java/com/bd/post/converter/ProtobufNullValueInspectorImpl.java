package com.bd.post.converter;

import com.google.protobuf.Duration;
import com.google.protobuf.Timestamp;
import net.badata.protobuf.converter.inspection.NullValueInspector;

public class ProtobufNullValueInspectorImpl implements NullValueInspector {
    @Override
    public boolean isNull(Object value) {
        if (value instanceof Timestamp){
            return Timestamp.getDefaultInstance().equals(value);
        } else if (value instanceof Duration){
            return Duration.getDefaultInstance().equals(value);
        }
        return false;
    }
}
