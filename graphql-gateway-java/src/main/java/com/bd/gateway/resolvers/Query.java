package com.bd.gateway.resolvers;

import com.bd.gateway.clients.PostClient;
import com.bd.gateway.clients.RpcGraphqlConverter;
import com.bd.gateway.types.AddPostOutput;
import com.bd.gateway.types.Posts;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sample.PostProto;

import java.util.concurrent.Future;


@AllArgsConstructor
@Component
public class Query implements GraphQLQueryResolver {
    private final PostClient postClient;
    //  listPosts(page: Int limit: Int): Posts
    public Future<Posts> listPosts(Integer page, Integer limit, DataFetchingEnvironment env){
        return RpcGraphqlConverter.protobufToGraphql(postClient.listPost(PostProto.listPostRequest.newBuilder().setPage(page).setLimit(limit).build()),Posts.class);
    }

}