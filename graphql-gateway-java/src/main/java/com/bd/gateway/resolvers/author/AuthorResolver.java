package com.bd.gateway.resolvers.author;

import com.bd.gateway.clients.PostClient;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sample.PostProto;
import sample.author.AuthorProto;

@AllArgsConstructor
@Component
public class AuthorResolver implements GraphQLResolver<AuthorProto.Author> {
	private final PostClient postClient;

	public ListenableFuture<PostProto.Posts> posts(AuthorProto.Author author, PostProto.ListPostRequest request){
		return postClient.listPost(request.toBuilder().setAuthorId(author.getId()).build());
	}

}
