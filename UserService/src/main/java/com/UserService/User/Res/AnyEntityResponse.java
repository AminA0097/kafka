package com.UserService.User.Res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class AnyEntityResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private Long total;
    private int totalPages;
}
