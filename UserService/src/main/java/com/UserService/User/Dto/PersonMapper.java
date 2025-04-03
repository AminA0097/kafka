package com.UserService.User.Dto;

import com.UserService.User.Entities.UserEntity;
import com.UserService.User.Res.AnyEntityResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonMapper {
    public AnyEntityResponse<UserDto> toEntityResponse(Page<UserEntity> all) {
        AnyEntityResponse<UserDto> anyEntityResponse = new AnyEntityResponse<>();
        List<UserDto> collect = all.stream().map(UserDto::new).collect(Collectors.toList());
        anyEntityResponse.setContent(collect);
        anyEntityResponse.setTotal(all.getTotalElements());
        anyEntityResponse.setPageNumber(all.getNumber() + 1);
        anyEntityResponse.setPageSize(all.getSize());
        anyEntityResponse.setTotalPages(all.getTotalPages());
        return anyEntityResponse;
    }

}
