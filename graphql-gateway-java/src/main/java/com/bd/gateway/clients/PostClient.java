package com.bd.gateway.clients;

import com.bd.gateway.types.Post;
import com.bd.gateway.types.Posts;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import net.badata.protobuf.converter.Converter;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import sample.PostProto;
import sample.PostServiceGrpc;

import java.util.concurrent.Future;

@Service
public class PostClient {
    @GrpcClient("post-gprc-server")
    private PostServiceGrpc.PostServiceFutureStub postServiceFutureStub;


    public ListenableFuture<PostProto.Post> addPost(sample.PostProto.addPostRequest request){
        return postServiceFutureStub.addPost(request);
    }

    public ListenableFuture<PostProto.Posts> listPost(sample.PostProto.listPostRequest request){
        return postServiceFutureStub.listPosts(request);
    }

}
