package com.jianyujianyu.repository;

import com.jianyujianyu.model.DirectoryEntity;
import com.jianyujianyu.model.FileEntity;
import com.jianyujianyu.model.LinkEntity;
import com.jianyujianyu.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by aimreant on 11/12/16.
 */
@Repository
@Transactional
public interface LinkRepository extends JpaRepository<LinkEntity, Integer> {
    public Integer countByUserByUserId(UserEntity userEntity);
    public List<LinkEntity> findByDirectoryByDirectoryId(DirectoryEntity directoryByDirectoryId);
    public LinkEntity findByIdAndUserByUserId(Integer id, UserEntity userByUserId);
    public List<LinkEntity> findByDirectoryByDirectoryIdAndUserByUserIdAndDeletedAtIsNull(
            DirectoryEntity directoryByDirectoryId,
            UserEntity userByUserId
    );

    public List<LinkEntity> findByDirectoryByDirectoryIdAndUserByUserIdAndDeletedAtIsNotNull(
            DirectoryEntity directoryByDirectoryId,
            UserEntity userByUserId
    );
}
