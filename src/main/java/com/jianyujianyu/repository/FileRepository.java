package com.jianyujianyu.repository;

import com.jianyujianyu.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aimreant on 11/12/16.
 */
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    public FileEntity findByHash(String hash);

}
