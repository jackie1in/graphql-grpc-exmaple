package com.bd.gateway.resolvers.post;

import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.stereotype.Component;
import sample.PostProto;

import java.util.List;

@Component
public class PostsResolver implements GraphQLResolver<PostProto.Posts> {

	public List<PostProto.Post> nodes(PostProto.Posts posts){
		return posts.getNodesList();
	}
}
