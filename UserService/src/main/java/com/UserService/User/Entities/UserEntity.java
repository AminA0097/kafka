package com.UserService.User.Entities;

import com.UserService.User.Configs.MyConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "PUSERTABLE")
public class UserEntity {
    @Id
    @SequenceGenerator(name = "kafka_seq",sequenceName = "generic_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "kafka_seq")
    @Column(name = "fld_id")
    private Integer id;
    @Column(name = "fld_username")
    private String userName;
    @Column(name = "fld_password")
    private String passWord;
    @Convert(converter = MyConverter.class)
    @Column(name = "fld_enabled")
    private Boolean enabled;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fld_user_id")
    private PersonEntity personEntity;

    public PersonEntity getPersonEntity() {
        return personEntity;
    }

    public void setPersonEntity(PersonEntity personEntity) {
        this.personEntity = personEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
