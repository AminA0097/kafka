package com.UserService.User.Repo;

import com.UserService.User.Entities.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends JpaRepository<PersonEntity,Integer> {
    Page<PersonEntity> findAll(Pageable pageable);
    @Query("SELECT p from PersonEntity p where p.email = ?1")
    PersonEntity findUser(String email);
}
