package com.jianyujianyu.repository;

import com.jianyujianyu.model.DirectoryEntity;
import com.jianyujianyu.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jca.cci.core.InteractionCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aimreant on 11/12/16.
 */
@Repository
public interface DirectoryRepository extends JpaRepository<DirectoryEntity, Integer> {
    public DirectoryEntity findOneByNameAndUserByUserId(String name, UserEntity userEntity);
    public List<DirectoryEntity> findByParentIdAndUserByUserIdAndDeletedAtIsNull(DirectoryEntity parentId, UserEntity userByUserId);
    public List<DirectoryEntity> findByParentIdAndUserByUserIdAndDeletedAtIsNotNull(DirectoryEntity parentId, UserEntity userByUserId);
    public DirectoryEntity findOneByIdAndUserByUserId(Integer id, UserEntity userEntity);

}
