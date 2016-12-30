package com.jianyujianyu.repository;

import com.jianyujianyu.model.LinkEntity;
import com.jianyujianyu.model.LogEntity;
import com.jianyujianyu.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by aimreant on 12/17/16.
 */
public interface LogRepository extends JpaRepository<LogEntity, Integer> {
    public List<LogEntity> findByUserByUserId(UserEntity userEntity);
    public List<LogEntity> findByUserByUserIdOrderByIdDesc(UserEntity userEntity);
    public List<LogEntity> findByLinkByLinkId(LinkEntity linkEntity);
}
