package com.bd.post.service;

import com.bd.post.model.Post;
import com.bd.post.repository.PostRepository;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sample.PostProto;
import sample.PostServiceGrpc;

import java.time.ZoneOffset;
import java.util.Optional;

/**
 * @author <a href="mailto:hilin2333@gmail.com">created by silencecorner 2019/7/10 3:25 PM</a>
 */
@AllArgsConstructor
@GrpcService
public class PostServiceImpl extends PostServiceGrpc.PostServiceImplBase {
    private final PostRepository postRepository;

    @Override
    public void addPost(PostProto.AddPostRequest request, StreamObserver<PostProto.Post> responseObserver) {
        Post post = postRepository.save(new Post(request.getTitle(), request.getBody()));
        responseObserver.onNext(modelToRpc(post));
        responseObserver.onCompleted();
    }

    @Override
    public void listPosts(PostProto.ListPostRequest request, StreamObserver<PostProto.Posts> responseObserver) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
        Page<Post> page = postRepository.findAll(pageable);
        responseObserver.onNext(PostProto.Posts.newBuilder()
                .addAllNodes(page.map(this::modelToRpc).getContent())
                .setCount((int) page.getTotalElements())
                .setPage(request.getPage())
                .setLimit(request.getLimit())
                .build());
        responseObserver.onCompleted();
    }

    private PostProto.Post modelToRpc(Post post) {
        return PostProto.Post.newBuilder()
                .setBody(post.getBody())
                .setTitle(post.getTitle())
                .setId(post.getId())
                .setCreatedAt(Optional.ofNullable(post.getCreatedAt())
                        .map(postMap -> Timestamps.fromSeconds(postMap.toEpochSecond(ZoneOffset.of("+8"))))
                        .orElse(Timestamp.getDefaultInstance()))
                .build();
    }
}
