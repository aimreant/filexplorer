package com.jianyujianyu.service;

import com.jianyujianyu.model.DirectoryEntity;
import com.jianyujianyu.model.LinkEntity;
import com.jianyujianyu.model.UserEntity;
import com.jianyujianyu.repository.DirectoryRepository;
import com.jianyujianyu.repository.LinkRepository;
import com.jianyujianyu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by aimreant on 12/10/16.
 */
@Service
@Transactional
public class DirectoryService {

    @Autowired DirectoryRepository directoryRepository;
    @Autowired LinkRepository linkRepository;
    @Autowired LogService logService;
    @Autowired LinkService linkService;
    @Autowired UserRepository userRepository;

    public List<DirectoryEntity> getCurrentDirs(Integer dirId, UserEntity userEntity){

        if(dirId == null){
            dirId = directoryRepository.findOneByNameAndUserByUserId("/", userEntity).getId();
        }

        List<DirectoryEntity> directoryEntityList = null;
        DirectoryEntity parentDir = directoryRepository.findOne(dirId);
        directoryEntityList = directoryRepository.findByParentIdAndUserByUserIdAndDeletedAtIsNull(
                parentDir, userEntity
        );

        // directoryEntityList = directoryRepository.findChildren(dirId);
        return directoryEntityList;
    }


    public List<LinkEntity> getCurrentLinks(Integer dirId, UserEntity userEntity){

        if(dirId == null){
            dirId = directoryRepository.findOneByNameAndUserByUserId("/", userEntity).getId();
        }

        List<LinkEntity> linkEntityList = null;
        DirectoryEntity parentDir = directoryRepository.findOne(dirId);
        linkEntityList = linkRepository.findByDirectoryByDirectoryIdAndUserByUserIdAndDeletedAtIsNull(
                parentDir, userEntity
        );
        return linkEntityList;
    }

    public List<DirectoryEntity> getCurrentDeletedDirs(Integer dirId, UserEntity userEntity){

        if(dirId == null){
            dirId = directoryRepository.findOneByNameAndUserByUserId("/", userEntity).getId();
        }

        List<DirectoryEntity> directoryEntityList = null;
        DirectoryEntity parentDir = directoryRepository.findOne(dirId);
        directoryEntityList = directoryRepository.findByParentIdAndUserByUserIdAndDeletedAtIsNotNull(
                parentDir, userEntity
        );

        // directoryEntityList = directoryRepository.findChildren(dirId);
        return directoryEntityList;
    }


    public List<LinkEntity> getCurrentDeletedLinks(Integer dirId, UserEntity userEntity){

        if(dirId == null){
            dirId = directoryRepository.findOneByNameAndUserByUserId("/", userEntity).getId();
        }

        List<LinkEntity> linkEntityList = null;
        DirectoryEntity parentDir = directoryRepository.findOne(dirId);
        linkEntityList = linkRepository.findByDirectoryByDirectoryIdAndUserByUserIdAndDeletedAtIsNotNull(
                parentDir, userEntity
        );
        return linkEntityList;
    }


    public DirectoryEntity createDirectory(Integer dirId, UserEntity userEntity, String name){

        if(dirId == null){
            dirId = directoryRepository.findOneByNameAndUserByUserId("/", userEntity).getId();
        }

        DirectoryEntity parentDir = directoryRepository.findOne(dirId);
        DirectoryEntity directoryEntity = new DirectoryEntity(
                name, userEntity, parentDir
        );
        directoryRepository.saveAndFlush(directoryEntity);

        logService.createLog("Create Directory " + directoryEntity.getName(), null, userEntity);
        return directoryEntity;

    }

    @Transactional
    public void deleteDirectory(DirectoryEntity directoryEntity, UserEntity userEntity){

        logService.createLog("Delete Directory " + directoryEntity.getName(), null, userEntity);
        directoryRepository.delete(directoryEntity);

//        Long useSpace = 0L;
//        for(LinkEntity linkEntity : linkRepository.findByUserByUserId(userEntity)){
//            useSpace += linkEntity.getFileByFileId().getSize();
//        }
//
//        userEntity.setSpaceUsage(userEntity.getLevelByLevelId().getSpaceAllow()-useSpace);
        //userRepository.saveAndFlush(userEntity);
    }


}
