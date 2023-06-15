package ru.pet.socialnetwork.mapper;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.pet.socialnetwork.dto.PostDTO;
import ru.pet.socialnetwork.model.Post;
import ru.pet.socialnetwork.model.User;
import ru.pet.socialnetwork.repository.UserRepository;
import ru.pet.socialnetwork.util.FileHelper;

@Component
public class PostMapper extends GenericMapper<Post, PostDTO>
        implements SpecificFieldsConverter<Post, PostDTO> {

    private final UserRepository userRepository;
    private final FileHelper fileHelper;

    protected PostMapper(UserRepository userRepository, FileHelper fileHelper) {
        super(Post.class, PostDTO.class);
        this.userRepository = userRepository;
        this.fileHelper = fileHelper;
    }

    @Override
    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Post.class, PostDTO.class)
                .addMappings(m -> {
                    m.skip(PostDTO::setUserId);
                    m.skip(PostDTO::setImageFile);
                })
                .setPostConverter(getConverterToDTO());
        modelMapper.createTypeMap(PostDTO.class, Post.class)
                .addMappings(m -> {
                    m.skip(Post::setUser);
                    m.skip(Post::setImageFileName);
                })
                .setPostConverter(getConverterToEntity());
    }

    @Override
    public void mapSpecificFields(Post source, PostDTO destination) {
        if (source != null && destination != null) {
            User user = source.getUser();
            if (user != null) {
                destination.setUserId(user.getId());
            }
//            String imageFileName = source.getImageFileName();
//            if (imageFileName != null) {
//                destination.setImageFile(fileHelper.getFile(imageFileName));
//            }
        }
    }

    @Override
    public void mapSpecificFields(PostDTO source, Post destination) {
        if (source != null && destination != null) {
            Long userId = source.getUserId();
            if (userId != null) {
                userRepository.findById(userId).ifPresent(destination::setUser);
            }
        }
    }
}
