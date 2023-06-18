package ru.pet.socialnetwork.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.pet.socialnetwork.dto.PostDTO;
import ru.pet.socialnetwork.mapper.PostMapper;
import ru.pet.socialnetwork.model.Post;
import ru.pet.socialnetwork.repository.PostRepository;
import ru.pet.socialnetwork.util.FileHelper;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostServiceTest extends GenericServiceTest<Post, PostDTO> {

    private final SubscriptionService subscriptionService;
    private final FileHelper fileHelper;


    public PostServiceTest() {
        mapper = Mockito.mock(PostMapper.class);
        repository = Mockito.mock(PostRepository.class);
        subscriptionService = Mockito.mock(SubscriptionService.class);
        fileHelper = Mockito.mock(FileHelper.class);
        service = new PostService((PostRepository) repository, (PostMapper) mapper,
                subscriptionService, fileHelper);
    }


    @Test
    void getAllByUserId() {
        PageRequest pageRequest = PageRequest.of(
                0, 5, Sort.by(Sort.Direction.DESC, "createdWhen"));
        Mockito.when(((PostRepository) repository).findAllByUserId(1L, pageRequest))
                .thenReturn(new PageImpl<>(PostTestData.POSTS));
        Mockito.when(mapper.toDTOs(PostTestData.POSTS)).thenReturn(PostTestData.POST_DTOS);

        List<PostDTO> postDTOS = ((PostService) service).getAllByUserId(1L, pageRequest);
        assertEquals(PostTestData.POST_DTOS, postDTOS);
    }

    @Test
    void getFeedOfPosts() {
        PageRequest pageRequest = PageRequest.of(
                0, 5, Sort.by(Sort.Direction.DESC, "createdWhen"));
        Mockito.when(subscriptionService.getAllByUserId(1L))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_DTOS);
        Mockito.when(((PostRepository) repository).findAllByUserIdIn(List.of(1L, 2L), pageRequest))
                .thenReturn(new PageImpl<>(PostTestData.POSTS));
        Mockito.when(mapper.toDTOs(PostTestData.POSTS)).thenReturn(PostTestData.POST_DTOS);

        List<PostDTO> postDTOS = ((PostService) service).getFeedOfPosts(1L, pageRequest);
        assertEquals(PostTestData.POST_DTOS, postDTOS);
    }

    @Test
    void getImageFile() {
        File file = Mockito.mock(File.class);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(PostTestData.POST_1));
        Mockito.when(fileHelper.getFile("testFileName")).thenReturn(file);
        File imageFile = ((PostService) service).getImageFile(1L);
        assertEquals(imageFile, file);
    }

    @Test
    @Override
    void getById() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(PostTestData.POST_1));
        Mockito.when(mapper.toDTO(PostTestData.POST_1)).thenReturn(PostTestData.POST_DTO_1);
        PostDTO postDTO = service.getById(1L);
        assertEquals(PostTestData.POST_DTO_1, postDTO);
    }

    @Test
    @Override
    void create() {
        Mockito.when(repository.save(PostTestData.POST_1)).thenReturn(PostTestData.POST_1);
        Mockito.when(mapper.toDTO(PostTestData.POST_1)).thenReturn(PostTestData.POST_DTO_1);
        Mockito.when(mapper.toEntity(PostTestData.POST_DTO_1)).thenReturn(PostTestData.POST_1);
        PostDTO postDTO = service.create(PostTestData.POST_DTO_1);
        assertEquals(PostTestData.POST_DTO_1, postDTO);
    }

    @Test
    @Override
    void update() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(PostTestData.POST_1));
        Mockito.when(repository.save(PostTestData.POST_1)).thenReturn(PostTestData.POST_1);
        Mockito.when(mapper.toDTO(PostTestData.POST_1)).thenReturn(PostTestData.POST_DTO_1);
        Mockito.when(mapper.toEntity(PostTestData.POST_DTO_1)).thenReturn(PostTestData.POST_1);
        PostDTO postDTO = service.update(1L, PostTestData.POST_DTO_1);
        assertEquals(PostTestData.POST_DTO_1, postDTO);
    }

    @Test
    @Override
    void delete() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(PostTestData.POST_1));
        Mockito.when(fileHelper.deleteFile("testFileName")).thenReturn(true);
        boolean deleted = service.delete(1L);
        assertTrue(deleted);
    }
}