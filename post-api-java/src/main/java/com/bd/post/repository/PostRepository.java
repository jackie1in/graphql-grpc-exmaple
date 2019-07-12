package com.bd.post.repository;

import com.bd.post.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author <a href="mailto:hilin2333@gmail.com">created by silencecorner 2019/7/10 3:47 PM</a>
 */
public interface PostRepository extends PagingAndSortingRepository<Post,Integer> {
}
