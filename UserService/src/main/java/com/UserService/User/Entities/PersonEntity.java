package com.UserService.User.Entities;
import com.UserService.User.Configs.MyConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "USERTABLE")
public class PersonEntity {
    @Id
    @SequenceGenerator(name = "kafka_seq",sequenceName = "generic_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "kafka_seq")
    @Column(name = "fld_id")
    private Integer id;
    @Column(name = "fld_name")
    private String name;
    @Column(name = "fld_email")
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
