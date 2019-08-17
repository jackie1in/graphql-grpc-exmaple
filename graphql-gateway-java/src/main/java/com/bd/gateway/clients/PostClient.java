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
	private PostServiceGrpc.PostServiceBlockingStub postServiceBlockingStub;
    @GrpcClient("post-grpc-server")
    private PostServiceGrpc.PostServiceFutureStub postServiceFutureStub;

	@GrpcClient("post-grpc-server")
	private PostServiceGrpc.PostServiceStub postServiceStub;


    public ListenableFuture<PostProto.Post> addPost(sample.PostProto.AddPostRequest request){
        return postServiceFutureStub.addPost(request);
    }

    public ListenableFuture<PostProto.Posts> listPost(sample.PostProto.ListPostRequest request){
        return postServiceFutureStub.listPosts(request);
    }

	public void listPosts(sample.PostProto.ListPostRequest request){

    	postServiceStub.listPosts(request, new io.grpc.stub.StreamObserver<sample.PostProto.Posts>(){
			@Override
			public void onNext(PostProto.Posts value) {

			}

			@Override
			public void onError(Throwable t) {

			}

			@Override
			public void onCompleted() {

			}
		} );
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
