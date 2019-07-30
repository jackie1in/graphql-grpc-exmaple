package com.bd.gateway.types;

import lombok.Data;

import java.util.List;

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

    private Integer count;
    private Integer page;
    private Integer limit;
    private List<Post> nodes;
}
