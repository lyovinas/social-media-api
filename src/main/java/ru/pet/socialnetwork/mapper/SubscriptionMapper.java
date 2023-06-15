package ru.pet.socialnetwork.mapper;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.pet.socialnetwork.dto.SubscriptionDTO;
import ru.pet.socialnetwork.model.Subscription;
import ru.pet.socialnetwork.model.User;
import ru.pet.socialnetwork.repository.UserRepository;

@Component
public class SubscriptionMapper extends GenericMapper<Subscription, SubscriptionDTO>
        implements SpecificFieldsConverter<Subscription, SubscriptionDTO> {

    private final UserRepository userRepository;



    protected SubscriptionMapper(UserRepository userRepository) {
        super(Subscription.class, SubscriptionDTO.class);
        this.userRepository = userRepository;
    }



    @Override
    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Subscription.class, SubscriptionDTO.class)
                .addMappings(m -> {
                    m.skip(SubscriptionDTO::setUserId);
                    m.skip(SubscriptionDTO::setTargetUserId);
                })
                .setPostConverter(getConverterToDTO());
        modelMapper.createTypeMap(SubscriptionDTO.class, Subscription.class)
                .addMappings(m -> {
                    m.skip(Subscription::setUser);
                    m.skip(Subscription::setTargetUser);
                })
                .setPostConverter(getConverterToEntity());
    }

    @Override
    public void mapSpecificFields(Subscription source, SubscriptionDTO destination) {
        if (source != null && destination != null) {
            User user = source.getUser();
            if (user != null) destination.setUserId(user.getId());
            user = source.getTargetUser();
            if (user != null) destination.setTargetUserId(user.getId());
        }
    }

    @Override
    public void mapSpecificFields(SubscriptionDTO source, Subscription destination) {
        if (source != null && destination != null) {
            Long id = source.getUserId();
            if (id != null) userRepository.findById(id).ifPresent(destination::setUser);
            id = source.getTargetUserId();
            if (id != null) userRepository.findById(id).ifPresent(destination::setTargetUser);
        }
    }
}
