package com.bd.gateway.resolvers;

import com.bd.gateway.clients.AuthorClient;
import com.bd.gateway.clients.PostClient;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sample.PostProto;
import sample.author.AuthorProto;


@AllArgsConstructor
@Component
public class Query implements GraphQLQueryResolver {
    private final PostClient postClient;
	private final AuthorClient authorClient;
    public ListenableFuture<PostProto.Posts> listPosts(PostProto.ListPostRequest request){
        return postClient.listPost(request);
    }

	public ListenableFuture<AuthorProto.Author> getAuthor(AuthorProto.GetAuthorRequest request){
		return authorClient.getAuthor(request.getId());
	}
}

