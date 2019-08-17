package com.bd.gateway.resolvers;

import com.bd.gateway.clients.PostClient;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sample.PostProto;


@AllArgsConstructor
@Component
public class Query implements GraphQLQueryResolver {
    private final PostClient postClient;
    public ListenableFuture<PostProto.Posts> listPosts(PostProto.ListPostRequest request){
        return postClient.listPost(request);
    }
}
