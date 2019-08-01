package com.bd.post.repository;

import com.bd.post.model.Author;
import com.bd.post.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author <a href="mailto:hilin2333@gmail.com">created by silencecorner 2019/7/10 3:47 PM</a>
 */
public interface AuthorRepository extends PagingAndSortingRepository<Author,Integer> {
}
