package com.bd.gateway.types;

import com.bd.gateway.clients.converter.LocalDateTimeConverterImpl;
import com.bd.gateway.clients.converter.ProtobufNullValueInspectorImpl;
import lombok.Data;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import sample.PostProto;

import java.time.LocalDateTime;

@Data
@ProtoClass(PostProto.Post.class)
public class Post {
    @ProtoField(name = "id")
    private Integer _id;
    @ProtoField
    private String title;
    @ProtoField
    private String body;
    @ProtoField(converter = LocalDateTimeConverterImpl.class,nullValue = ProtobufNullValueInspectorImpl.class)
    private LocalDateTime createdAt;

	@ProtoField
    private Integer authorId;
}
