package com.bd.gateway.clients;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.stub.ServerCallStreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import sample.PostProto;
import sample.PostServiceGrpc;

import java.util.concurrent.CompletableFuture;

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


	public ListenableFuture<PostProto.Post> updatePost(sample.PostProto.UpdatePostRequest request){
		return postServiceFutureStub.updatePost(request);
	}

}
