package com.bd.post.service;

import com.bd.post.model.Post;
import com.bd.post.repository.PostRepository;
import com.bd.post.util.CopyUtils;
import io.envoyproxy.pgv.ExplicitValidatorIndex;
import io.envoyproxy.pgv.ReflectiveValidatorIndex;
import io.envoyproxy.pgv.ValidationException;
import io.envoyproxy.pgv.ValidatorIndex;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.badata.protobuf.converter.Configuration;
import net.badata.protobuf.converter.Converter;
import net.badata.protobuf.converter.FieldsIgnore;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sample.PostProto;
import sample.PostProtoValidator;
import sample.PostServiceGrpc;

/**
 * @author <a href="mailto:hilin2333@gmail.com">created by silencecorner 2019/7/10 3:25 PM</a>
 */
@Slf4j
@AllArgsConstructor
@GrpcService
public class PostServiceImpl extends PostServiceGrpc.PostServiceImplBase {
	private final PostRepository postRepository;

	@Transactional
	@Override
	public void addPost(PostProto.AddPostRequest request, StreamObserver<PostProto.Post> responseObserver) {
		Post post = postRepository.save(new Post(request.getTitle(), request.getBody(), request.getAuthorId()));
		responseObserver.onNext(modelToRpc(post));
		responseObserver.onCompleted();
	}

	@Override
	public void listPosts(PostProto.ListPostRequest request, StreamObserver<PostProto.Posts> responseObserver) {
		Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
		Page<Post> page = request.getAuthorId() != 0
			? postRepository.findByAuthorId(request.getAuthorId(), pageable)
			: postRepository.findAll(pageable);
		responseObserver.onNext(PostProto.Posts.newBuilder()
			.addAllNodes(page.map(this::modelToRpc).getContent())
			.setCount((int) page.getTotalElements())
			.setPage(request.getPage())
			.setLimit(request.getLimit())
			.build());
		responseObserver.onCompleted();
	}

	@Transactional
	@Override
	public void updatePost(PostProto.UpdatePostRequest request, StreamObserver<PostProto.Post> responseObserver){
		check(request);
		// field_mask 填充字段
		Configuration configuration = Configuration.builder().addIgnoredFields(new FieldsIgnore().add(Post.class, "authorId", "createdAt")).build();
		Post post = Converter.create(configuration).toDomain(Post.class, request);
		Post newPost = postRepository.getOne(post.getId());
		CopyUtils.copyProperties(post, newPost, true);
		responseObserver.onNext(modelToRpc(postRepository.save(newPost)));
		responseObserver.onCompleted();
	}

	private PostProto.Post modelToRpc(Post post) {
		return Converter.create().toProtobuf(PostProto.Post.class, post);
	}

	/**
	 * 从新增条件copy验证条件
	 * @see PostProtoValidator.AddPostRequestValidator#assertValid
	 * @param request 请求参数
	 */
	private void check(PostProto.UpdatePostRequest request){
		boolean checkFlag = false;
		if (request.getId() == 0){
			throw Status.INVALID_ARGUMENT.asRuntimeException();
		}
		try {
			if (!request.getTitle().equals( "")){
				io.envoyproxy.pgv.StringValidation.minLength(".sample.UpdatePostRequest.title", request.getTitle(), 1);
				io.envoyproxy.pgv.StringValidation.maxLength(".sample.UpdatePostRequest.title", request.getTitle(), 100);
			}
			if (!request.getBody().equals( "")){
				io.envoyproxy.pgv.StringValidation.minLength(".sample.UpdatePostRequest.body", request.getBody(), 1);
				io.envoyproxy.pgv.StringValidation.maxLength(".sample.UpdatePostRequest.body", request.getBody(), 20000);
			}
		}catch (ValidationException e){
			throw Status.INVALID_ARGUMENT.withCause(e).withDescription(e.getMessage()).asRuntimeException();
		}

	}




}
