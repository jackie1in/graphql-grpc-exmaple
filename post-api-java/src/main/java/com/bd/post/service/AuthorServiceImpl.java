package com.bd.post.service;

import com.bd.post.model.Author;
import com.bd.post.repository.AuthorRepository;
import com.bd.post.security.annotation.NoSecure;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sample.author.AuthorProto;
import sample.author.AuthorServiceGrpc;

import java.util.Optional;

@GrpcService
public class AuthorServiceImpl extends AuthorServiceGrpc.AuthorServiceImplBase {

	@Autowired
	private AuthorRepository authorRepository;

	@Transactional
	@Override
	@NoSecure
	public void addAuthor(AuthorProto.AddAuthorRequest request, StreamObserver<AuthorProto.Author> responseObserver) {
		// 参数校验，"" "    "
		Author author = new Author();
		author.setName(request.getName());
		author = authorRepository.save(author);

		responseObserver.onNext(AuthorProto.Author.newBuilder().setId(author.getId()).setName(author.getName()).build());
		responseObserver.onCompleted();

	}


	@Override
	public void getAuthor(AuthorProto.GetAuthorRequest request, StreamObserver<AuthorProto.Author> responseObserver) {
		Optional<Author> author = authorRepository.findById(request.getId());
		if (author.isPresent()){
			responseObserver.onNext(AuthorProto.Author.newBuilder().setId(author.get().getId()).setName(author.get().getName()).build());
		}else{
			responseObserver.onNext(AuthorProto.Author.getDefaultInstance());
		}
		responseObserver.onCompleted();
	}
}


