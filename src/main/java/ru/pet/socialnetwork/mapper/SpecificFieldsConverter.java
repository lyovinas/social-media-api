package ru.pet.socialnetwork.mapper;

import org.modelmapper.Converter;
import ru.pet.socialnetwork.dto.GenericDTO;
import ru.pet.socialnetwork.model.GenericModel;

public interface SpecificFieldsConverter<E extends GenericModel, D extends GenericDTO> {

    @SuppressWarnings("unused")
    void setupMapper();

    void mapSpecificFields(E source, D destination);

    void mapSpecificFields(D source, E destination);

    default Converter<E, D> getConverterToDTO() {
        return mappingContext -> {
            E source = mappingContext.getSource();
            D destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return destination;
        };
    }

    default Converter<D, E> getConverterToEntity() {
        return mappingContext -> {
            D source = mappingContext.getSource();
            E destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return destination;
        };
    }
}
