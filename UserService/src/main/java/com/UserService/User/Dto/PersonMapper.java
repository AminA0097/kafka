package com.UserService.User.Dto;

import com.UserService.User.Entities.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonMapper {
    public PersonResponse<PersonDto> toEntityResponse(Page<PersonEntity> all) {
        PersonResponse<PersonDto> personResponseList = new PersonResponse<>();
        List<PersonDto> collect = all.stream().map(PersonDto::new).collect(Collectors.toList());
        personResponseList.setContent(collect);
        personResponseList.setTotal(all.getTotalElements());
        personResponseList.setPageNumber(all.getNumber() + 1);
        personResponseList.setPageSize(all.getSize());
        personResponseList.setTotalPages(all.getTotalPages());
        return personResponseList;
    }

}
