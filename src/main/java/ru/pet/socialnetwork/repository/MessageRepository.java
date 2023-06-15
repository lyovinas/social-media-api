package ru.pet.socialnetwork.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.pet.socialnetwork.model.Message;

import java.util.List;

@Repository
public interface MessageRepository extends GenericRepository<Message>{

    List<Message> findAllByUserIdInAndTargetUserIdIn(List<Long> userIds, List<Long> targetUserIds, Sort sort);
}
