package ru.pet.socialnetwork.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pet.socialnetwork.dto.GenericDTO;
import ru.pet.socialnetwork.model.GenericModel;

import java.util.List;

public abstract class GenericMapper<E extends GenericModel, D extends GenericDTO> {

    @Autowired
    protected ModelMapper modelMapper;
    private final Class<E> ENTITY_CLASS;
    private final Class<D> DTO_CLASS;



    protected GenericMapper(Class<E> entity_class, Class<D> dto_class) {
        ENTITY_CLASS = entity_class;
        DTO_CLASS = dto_class;
    }



    public E toEntity(D dto) {
        return dto == null
                ? null
                : modelMapper.map(dto, ENTITY_CLASS);
    }

    public D toDTO(E entity) {
        return entity == null
                ? null
                : modelMapper.map(entity, DTO_CLASS);
    }

    public List<D> toDTOs(List<E> entities) {
        return entities == null
                ? null
                : entities.stream().map(this::toDTO).toList();
    }
}
