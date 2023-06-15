package ru.pet.socialnetwork.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.pet.socialnetwork.dto.PostDTO;
import ru.pet.socialnetwork.dto.SubscriptionDTO;
import ru.pet.socialnetwork.mapper.PostMapper;
import ru.pet.socialnetwork.model.Post;
import ru.pet.socialnetwork.repository.PostRepository;
import ru.pet.socialnetwork.util.FileHelper;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService extends GenericService<Post, PostDTO> {

    private final SubscriptionService subscriptionService;
    private final FileHelper fileHelper;



    public PostService(PostRepository postRepository, PostMapper postMapper,
                       SubscriptionService subscriptionService, FileHelper fileHelper) {
        repository = postRepository;
        mapper = postMapper;
        this.subscriptionService = subscriptionService;
        this.fileHelper = fileHelper;
    }



    @Override
    public PostDTO create(PostDTO postDTO) {
        Post post = mapper.toEntity(postDTO);
        post.setCreatedWhen(LocalDateTime.now());
        MultipartFile file = postDTO.getImageFile();
        if (file != null && !file.isEmpty()) {
            String imageFileName = fileHelper.createFile(file);
            if (imageFileName == null) return null;
            post.setImageFileName(imageFileName);
        }
        return mapper.toDTO(repository.save(post));
    }

    @Override
    public PostDTO update(Long id, PostDTO updatedPostDTO) {
        Post existingPost = repository.findById(id).orElse(null);
        if (existingPost != null) {
            existingPost.setTitle(updatedPostDTO.getTitle());
            existingPost.setText(updatedPostDTO.getText());
            MultipartFile newImageFile = updatedPostDTO.getImageFile();
            if (newImageFile != null && !newImageFile.isEmpty()) {
                String imageFileName = existingPost.getImageFileName();
                if (imageFileName != null && !imageFileName.isBlank()) {
                    fileHelper.updateFile(imageFileName, newImageFile);
                } else {
                    imageFileName = fileHelper.createFile(newImageFile);
                    existingPost.setImageFileName(imageFileName);
                }
            }
            return mapper.toDTO(repository.save(existingPost));
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Post existingPost;
        if (id == null || (existingPost = repository.findById(id).orElse(null)) == null) {
            return false;
        }
        boolean imageFileDeleted = fileHelper.deleteFile(existingPost.getImageFileName());
        if (imageFileDeleted) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<PostDTO> getAllByUserId(Long userId, Pageable pageable) {
        Page<Post> postPage = ((PostRepository) repository).findAllByUserId(userId, pageable);

        return mapper.toDTOs(postPage.getContent());
    }

    public List<PostDTO> getFeedOfPosts(Long userId, Pageable pageable) {
        List<Long> userIds = subscriptionService.getAllByUserId(userId)
                .stream()
                .map(SubscriptionDTO::getTargetUserId)
                .toList();
        Page<Post> postPage = ((PostRepository) repository).findAllByUserIdIn(userIds, pageable);

        return mapper.toDTOs(postPage.getContent());
    }

    public File getImageFile(Long postId) {
        Post post = repository.findById(postId).orElse(null);
        if (post != null) {
            String imageFileName = post.getImageFileName();
            if (imageFileName != null && !imageFileName.isBlank()) {
                return fileHelper.getFile(imageFileName);
            }
        }
        return null;
    }
}
