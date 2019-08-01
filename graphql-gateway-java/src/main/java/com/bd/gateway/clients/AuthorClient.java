package com.bd.gateway.clients;

import com.google.common.util.concurrent.ListenableFuture;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import sample.PostProto;
import sample.PostServiceGrpc;
import sample.author.AuthorProto;
import sample.author.AuthorServiceGrpc;

@Service
public class AuthorClient {
    @GrpcClient("author-grpc-server")
    private AuthorServiceGrpc.AuthorServiceBlockingStub authorServiceBlockingStub;


//   public ListenableFuture<AuthorProto.Author> addAuthor(String name) {
//       if (name == null) {
//           throw new RuntimeException("参数不能为空");
//       }
//       return authorServiceFutureStub.addAuthor(AuthorProto.AddAuthorRequest.newBuilder().setName(name).build());
//   }
    public AuthorProto.Author addAuthor(String name){
        return authorServiceBlockingStub.addAuthor(AuthorProto.AddAuthorRequest.newBuilder().setName(name).build());
    }

}
