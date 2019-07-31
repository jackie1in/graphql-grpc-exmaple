package com.bd.gateway.clients;

import com.google.common.util.concurrent.ListenableFuture;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import sample.PostProto;
import sample.PostServiceGrpc;

@Service
public class PostClient {
    @GrpcClient("post-grpc-server")
    private PostServiceGrpc.PostServiceFutureStub postServiceFutureStub;


    public ListenableFuture<PostProto.Post> addPost(sample.PostProto.AddPostRequest request){
        return postServiceFutureStub.addPost(request);
    }

    public ListenableFuture<PostProto.Posts> listPost(sample.PostProto.ListPostRequest request){
        return postServiceFutureStub.listPosts(request);
    }

//    @GrpcClient("post-grpc-server")
//    private PostServiceGrpc.PostServiceBlockingStub postServiceBlockingStub;
//    public PostProto.Post addPost(sample.PostProto.addPostRequest request){
//        return postServiceBlockingStub.addPost(request);
//    }
//
//    public PostProto.Posts listPost(sample.PostProto.listPostRequest request){
//        return postServiceBlockingStub.listPosts(request);
//    }

}
