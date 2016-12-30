package com.jianyujianyu.repository;

import com.jianyujianyu.model.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aimreant on 11/12/16.
 */
@Repository
public interface LevelRepository extends JpaRepository<LevelEntity, Integer> {
}
