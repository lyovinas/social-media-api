package ru.pet.socialnetwork.service;

import ru.pet.socialnetwork.dto.GenericDTO;
import ru.pet.socialnetwork.mapper.GenericMapper;
import ru.pet.socialnetwork.model.GenericModel;
import ru.pet.socialnetwork.repository.GenericRepository;

import java.time.LocalDateTime;

public abstract class GenericService<E extends GenericModel, D extends GenericDTO> {

    protected GenericRepository<E> repository;
    protected GenericMapper<E, D> mapper;


    public D getById(Long id) {
        return id != null
                ? mapper.toDTO(repository.findById(id).orElse(null))
                : null;
    }

    public D create(D newDTO) {
        if (newDTO == null) {
            return null;
        }
        E newEntity = mapper.toEntity(newDTO);
        newEntity.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(newEntity));
    }

    public D update(Long id, D dto) {
        if (dto == null || id == null) {
            return null;
        }
        E existingEntity = repository.findById(id).orElse(null);
        if (existingEntity == null) {
            return null;
        }
        E updatedEntity = mapper.toEntity(dto);
        updatedEntity.setId(id);
        updatedEntity.setCreatedWhen(existingEntity.getCreatedWhen());
        return mapper.toDTO(repository.save(updatedEntity));
    }

    public boolean delete(Long id) {
        if (id != null && repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
