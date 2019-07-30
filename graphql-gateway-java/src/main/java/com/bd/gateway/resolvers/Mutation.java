package com.bd.gateway.resolvers;

import com.bd.gateway.clients.PostClient;
import com.bd.gateway.inputs.AddPostInput;
import com.bd.gateway.types.AddPostOutput;
import com.bd.gateway.types.Post;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.google.common.util.concurrent.Futures;
import lombok.AllArgsConstructor;
import net.badata.protobuf.converter.Converter;
import org.springframework.stereotype.Component;
import sample.PostProto;

import java.util.concurrent.Future;

@AllArgsConstructor
@Component
public class Mutation implements GraphQLMutationResolver {

    private final PostClient postClient;

    public Future<AddPostOutput> addPost(AddPostInput data){
        return Futures.lazyTransform(postClient.addPost(Converter.create().toProtobuf(PostProto.addPostRequest.class,data)), in -> {
            Post post = Converter.create().toDomain(Post.class, in);
            AddPostOutput output = new AddPostOutput();
            output.setMessage("post created");
            output.setResult(post);
            return output;
        });
    }

}
