package com.danieloliveira.restwithspringbootandjava.services;

import com.danieloliveira.restwithspringbootandjava.VO.v1.PersonVO;
import com.danieloliveira.restwithspringbootandjava.VO.v2.PersonVOv2;
import com.danieloliveira.restwithspringbootandjava.exceptions.ResourceNotFoundException;
import com.danieloliveira.restwithspringbootandjava.mapper.DozerMapper;
import com.danieloliveira.restwithspringbootandjava.mapper.custom.PersonMapper;
import com.danieloliveira.restwithspringbootandjava.model.Person;
import com.danieloliveira.restwithspringbootandjava.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;


@Service
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());
    @Autowired
    PersonRepository repository;
    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll() {

        logger.info("Finding all people!");

        return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO createV2(PersonVO personVO) {

        logger.info("Creating one person!");

        var entity = DozerMapper.parseObject(personVO, Person.class);

        // salva a entidade e dps retorna o VO dela
        return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public PersonVOv2 createV2(PersonVOv2 personVOv2) {

        logger.info("Creating one person!");

        var entity = mapper.convertVOToEntity(personVOv2);

        // salva a entidade e dps retorna o VO dela
        return mapper.convertEntityToVO(repository.save(entity));
    }

    public PersonVO update(PersonVO person) {

        logger.info("Updating one person!");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}
