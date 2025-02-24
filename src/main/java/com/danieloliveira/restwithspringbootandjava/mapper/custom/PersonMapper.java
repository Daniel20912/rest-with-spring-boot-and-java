package com.danieloliveira.restwithspringbootandjava.mapper.custom;

import com.danieloliveira.restwithspringbootandjava.VO.v2.PersonVOv2;
import com.danieloliveira.restwithspringbootandjava.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonVOv2 convertEntityToVO(Person person) {
        PersonVOv2 personVOv2 = new PersonVOv2();
        personVOv2.setId(person.getId());
        personVOv2.setFirstName(person.getFirstName());
        personVOv2.setLastName(person.getLastName());
        personVOv2.setAddress(person.getAddress());
        personVOv2.setBirthDate(new Date());
        personVOv2.setGender(person.getGender());
        return personVOv2;
    }

    public Person convertVOToEntity(PersonVOv2 personVOv2) {
        Person person = new Person();
        person.setId(personVOv2.getId());
        person.setFirstName(personVOv2.getFirstName());
        person.setLastName(personVOv2.getLastName());
        person.setAddress(personVOv2.getAddress());
        person.setGender(personVOv2.getGender());

        return person;
    }
}
