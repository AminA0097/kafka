package com.UserService.User.Dto;

import com.UserService.User.Entities.PersonEntity;

public class PersonDto {
    private boolean enabled;
    private String email;
    private String name;
    private Integer id;
    public PersonDto(PersonEntity personEntity) {
        this.email = personEntity.getEmail();
        this.name = personEntity.getName();
        this.id = personEntity.getId();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
