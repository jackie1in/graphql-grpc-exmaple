package com.bd.gateway.inputs;

import lombok.Data;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import sample.PostProto;

@Data
@ProtoClass(PostProto.AddPostRequest.class)
public class AddPostInput {
    @ProtoField
    private String title;
    @ProtoField
    private String body;
}
