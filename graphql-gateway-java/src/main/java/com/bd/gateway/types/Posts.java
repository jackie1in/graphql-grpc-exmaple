package com.bd.gateway.types;

import lombok.Data;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import sample.PostProto;

import java.util.List;

@ProtoClass(PostProto.Posts.class)
@Data
public class Posts {
//  # 总数
//    count: Int
//  # 当前页
//    page: Int
//  # 条数
//    limit: Int
//  # 结点
//  nodes: [Post]
    @ProtoField
    private Integer count;
    @ProtoField
    private Integer page;
    @ProtoField
    private Integer limit;
    @ProtoField
    private List<Post> nodes;
}
