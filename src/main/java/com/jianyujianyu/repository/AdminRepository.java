package com.jianyujianyu.repository;

import com.jianyujianyu.model.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aimreant on 11/12/16.
 */
@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
    public AdminEntity findByUsername(String username);
    public AdminEntity getByUsernameAndPassword(String username, String password);
    public List<AdminEntity> findByDeletedAtIsNotNull();
    public AdminEntity findOneByIdAndDeletedAtIsNotNull(Integer id);
    public List<AdminEntity> findByDeletedAtIsNull();
    public AdminEntity findOneByIdAndDeletedAtIsNull(Integer id);
}
