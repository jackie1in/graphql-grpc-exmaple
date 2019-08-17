package com.bd.gateway.resolvers;

import com.bd.gateway.clients.AuthorClient;
import com.bd.gateway.types.Author;
import com.bd.gateway.types.Post;
import com.coxautodev.graphql.tools.GraphQLResolver;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sample.author.AuthorProto;

@Component
public class PostResolver implements GraphQLResolver<Post> {

	@Autowired
	private AuthorClient authorClient;

	public Author author(Post post){
		AuthorProto.Author author = authorClient.getAuthor(post.getAuthorId());
		Author graphqlAuthor = new Author();
		graphqlAuthor.setId(author.getId());
		graphqlAuthor.setName(author.getName());
		return graphqlAuthor;
	}

}
