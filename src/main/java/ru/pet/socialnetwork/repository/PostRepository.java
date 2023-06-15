package ru.pet.socialnetwork.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.pet.socialnetwork.model.Post;

import java.util.List;

public interface PostRepository extends GenericRepository<Post> {

    Page<Post> findAllByUserId(Long userId, Pageable pageable);

//    List<Post> findAllByUserIdIn(List<Long> ids);
    Page<Post> findAllByUserIdIn(List<Long> ids, Pageable pageable);
}
