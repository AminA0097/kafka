package com.UserService.User.Repo;

import com.UserService.User.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    @Query("SELECT count(*) from UserEntity p where p.id =?1")
    Long findUser(Integer id);
    @Query("select p from UserEntity p where p.userName =?1")
    UserEntity findUserByUserName(String userName);
}
