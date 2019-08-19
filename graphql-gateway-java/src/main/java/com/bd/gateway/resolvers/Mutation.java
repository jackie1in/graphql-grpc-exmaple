package com.bd.gateway.resolvers;

import com.bd.gateway.clients.AuthorClient;
import com.bd.gateway.clients.PostClient;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sample.PostProto;
import sample.author.AuthorProto;

@AllArgsConstructor
@Component
public class Mutation implements GraphQLMutationResolver {

    private final PostClient postClient;

    private final AuthorClient authorClient;

    public ListenableFuture<PostProto.Post> addPost(PostProto.AddPostRequest request){
		return postClient.addPost(request.toBuilder().setAuthorId(1).build());
    }


	public ListenableFuture<PostProto.Post> updatePost(PostProto.UpdatePostRequest request){
		return postClient.updatePost(request);
	}

    public ListenableFuture<AuthorProto.Author> addAuthor(AuthorProto.AddAuthorRequest request){
    	  return authorClient.addAuthor(request);
    }
}
