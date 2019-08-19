package com.bd.gateway.clients;

import com.google.common.util.concurrent.ListenableFuture;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import sample.author.AuthorProto;
import sample.author.AuthorServiceGrpc;

@Service
public class AuthorClient {
    @GrpcClient("author-grpc-server")
    private AuthorServiceGrpc.AuthorServiceFutureStub authorServiceFutureStub;

    public ListenableFuture<AuthorProto.Author> addAuthor(AuthorProto.AddAuthorRequest request){
        return authorServiceFutureStub.addAuthor(request);
    }


	public ListenableFuture<AuthorProto.Author> getAuthor(Integer id){
		return authorServiceFutureStub.getAuthor(AuthorProto.GetAuthorRequest.newBuilder().setId(id).build());
	}

	public ListenableFuture<AuthorProto.Author> getAuthor(AuthorProto.GetAuthorRequest req){
		return authorServiceFutureStub.getAuthor(req);
	}
}
