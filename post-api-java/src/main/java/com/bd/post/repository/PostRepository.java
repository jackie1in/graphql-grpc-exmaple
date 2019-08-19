package com.bd.post.repository;

import com.bd.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="mailto:hilin2333@gmail.com">created by silencecorner 2019/7/10 3:47 PM</a>
 */
public interface PostRepository extends JpaRepository<Post,Integer> {
	Page<Post> findByAuthorId(Integer authorId, Pageable pageable);
}
