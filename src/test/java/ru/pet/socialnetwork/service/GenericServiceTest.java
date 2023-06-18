package ru.pet.socialnetwork.service;

import ru.pet.socialnetwork.dto.GenericDTO;
import ru.pet.socialnetwork.mapper.GenericMapper;
import ru.pet.socialnetwork.model.GenericModel;
import ru.pet.socialnetwork.repository.GenericRepository;

abstract class GenericServiceTest<E extends GenericModel, D extends GenericDTO> {

    protected GenericService<E, D> service;
    protected GenericRepository<E> repository;
    protected GenericMapper<E, D> mapper;


    abstract void getById();

    abstract void create();

    abstract void update();

    abstract void delete();
}