package com.UserService.User.Dto;

import com.UserService.User.Entities.PersonEntity;
import com.UserService.User.Entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonMapper {
    public PersonResponse<UserDto> toEntityResponse(Page<UserEntity> all) {
        PersonResponse<UserDto> personResponseList = new PersonResponse<>();
        List<UserDto> collect = all.stream().map(UserDto::new).collect(Collectors.toList());
        personResponseList.setContent(collect);
        personResponseList.setTotal(all.getTotalElements());
        personResponseList.setPageNumber(all.getNumber() + 1);
        personResponseList.setPageSize(all.getSize());
        personResponseList.setTotalPages(all.getTotalPages());
        return personResponseList;
    }

}
