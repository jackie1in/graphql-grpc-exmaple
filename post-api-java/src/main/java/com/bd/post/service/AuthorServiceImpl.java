package com.bd.post.service;

import com.bd.post.model.Author;
import com.bd.post.repository.AuthorRepository;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import sample.author.AuthorProto;
import sample.author.AuthorServiceGrpc;

@GrpcService
public class AuthorServiceImpl extends AuthorServiceGrpc.AuthorServiceImplBase {

    @Autowired
    private  AuthorRepository authorRepository;
    @Override
    public void addAuthor(AuthorProto.AddAuthorRequest request, StreamObserver<AuthorProto.Author> responseObserver) {
        // 参数校验，"" "    "
        Author author = new Author();
        author.setName(request.getName());
        author = authorRepository.save(author);

        responseObserver.onNext(AuthorProto.Author.newBuilder().setId(author.getId()).setName(author.getName()).build());
        responseObserver.onCompleted();

    }
}
