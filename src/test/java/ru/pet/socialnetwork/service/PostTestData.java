package ru.pet.socialnetwork.service;

import ru.pet.socialnetwork.dto.PostDTO;
import ru.pet.socialnetwork.model.Post;

import java.util.List;

public interface PostTestData {

    Post POST_1 = new Post(null, "", "", "testFileName");
    Post POST_2 = new Post();
    List<Post> POSTS = List.of(POST_1, POST_2);

    PostDTO POST_DTO_1 = new PostDTO();
    PostDTO POST_DTO_2 = new PostDTO();
    List<PostDTO> POST_DTOS = List.of(POST_DTO_1, POST_DTO_2);
}
