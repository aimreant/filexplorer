package com.jianyujianyu.repository;

import com.jianyujianyu.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aimreant on 11/12/16.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public UserEntity findByUsername(String username);
    public UserEntity getByUsernameAndPassword(String username, String password);
    public List<UserEntity> findByDeletedAtIsNotNull();
    public UserEntity findOneByIdAndDeletedAtIsNotNull(Integer id);
    public List<UserEntity> findByDeletedAtIsNull();
    public UserEntity findOneByIdAndDeletedAtIsNull(Integer id);
}
