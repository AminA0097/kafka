package com.UserService.User.Dto;

import com.UserService.User.Entities.PersonEntity;
import com.UserService.User.Entities.UserEntity;

public class UserDto {
    private boolean enabled;
    private String userName;
    private String email;
    private String name;
    public UserDto(UserEntity userEntity) {
        this.enabled = userEntity.getEnabled();
        this.userName = userEntity.getUserName();
        this.email = userEntity.getPersonEntity().getEmail();
        this.name = userEntity.getPersonEntity().getName();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
