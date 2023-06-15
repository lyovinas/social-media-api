package ru.pet.socialnetwork.mapper;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.pet.socialnetwork.dto.MessageDTO;
import ru.pet.socialnetwork.model.Message;
import ru.pet.socialnetwork.model.User;
import ru.pet.socialnetwork.repository.UserRepository;

@Component
public class MessageMapper extends GenericMapper<Message, MessageDTO>
        implements SpecificFieldsConverter<Message, MessageDTO> {
    
    private final UserRepository userRepository;



    protected MessageMapper(UserRepository userRepository) {
        super(Message.class, MessageDTO.class);
        this.userRepository = userRepository;
    }



    @Override
    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Message.class, MessageDTO.class)
                .addMappings(m -> {
                    m.skip(MessageDTO::setUserId);
                    m.skip(MessageDTO::setTargetUserId);
                })
                .setPostConverter(getConverterToDTO());
        modelMapper.createTypeMap(MessageDTO.class, Message.class)
                .addMappings(m -> {
                    m.skip(Message::setUser);
                    m.skip(Message::setTargetUser);
                })
                .setPostConverter(getConverterToEntity());
    }

    @Override
    public void mapSpecificFields(Message source, MessageDTO destination) {
        if (source != null && destination != null) {
            User user = source.getUser();
            if (user != null) destination.setUserId(user.getId());
            user = source.getTargetUser();
            if (user != null) destination.setTargetUserId(user.getId());
        }
    }

    @Override
    public void mapSpecificFields(MessageDTO source, Message destination) {
        if (source != null && destination != null) {
            Long id = source.getUserId();
            if (id != null) userRepository.findById(id).ifPresent(destination::setUser);
            id = source.getTargetUserId();
            if (id != null) userRepository.findById(id).ifPresent(destination::setTargetUser);
        }
    }
}
