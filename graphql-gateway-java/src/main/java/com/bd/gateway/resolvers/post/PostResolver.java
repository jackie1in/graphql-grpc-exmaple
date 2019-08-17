package com.bd.gateway.resolvers.post;

import com.bd.gateway.clients.AuthorClient;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sample.PostProto;
import sample.author.AuthorProto;

@Component
public class PostResolver implements GraphQLResolver<PostProto.Post> {

	@Autowired
	private AuthorClient authorClient;

	public ListenableFuture<AuthorProto.Author> author(PostProto.Post post){
		return authorClient.getAuthor(post.getAuthorId());
	}

}
