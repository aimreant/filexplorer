package com.jianyujianyu.service;

import com.jianyujianyu.model.DirectoryEntity;
import com.jianyujianyu.model.FileEntity;
import com.jianyujianyu.model.LinkEntity;
import com.jianyujianyu.model.UserEntity;
import com.jianyujianyu.repository.DirectoryRepository;
import com.jianyujianyu.repository.LinkRepository;
import com.jianyujianyu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by aimreant on 12/9/16.
 */
@Service
@Transactional
public class LinkService {

    @Autowired LinkRepository linkRepository;
    @Autowired DirectoryRepository directoryRepository;
    @Autowired UserRepository userRepository;
    @Autowired LogService logService;

    public boolean createLink(
            FileEntity fileEntity,
            UserEntity userEntity,
            Integer dirId,
            MultipartFile file
    ){

        if(dirId == null){
            dirId = directoryRepository.findOneByNameAndUserByUserId("/", userEntity).getId();
        }

        DirectoryEntity directoryEntity = directoryRepository.findOneByIdAndUserByUserId(
                dirId, userEntity
        );

        LinkEntity linkEntity = new LinkEntity(
                file.getOriginalFilename(),
                directoryEntity,
                fileEntity,
                userEntity
        );
        linkRepository.saveAndFlush(linkEntity);

        userEntity.setSpaceUsage(userEntity.getSpaceUsage()+fileEntity.getSize());
        userRepository.saveAndFlush(userEntity);

        logService.createLog("Upload File " + linkEntity.getFilename(), linkEntity, userEntity);

        return true;
    }

    public void renameLink(
            String name,
            LinkEntity linkEntity,
            UserEntity userEntity
    ){
        String originName = linkEntity.getFilename();
        linkEntity.setFilename(name);
        linkRepository.saveAndFlush(linkEntity);

        logService.createLog("Rename File " + originName + " to " + linkEntity.getFilename(), linkEntity, userEntity);
    }

    public void deleteLink(
            LinkEntity linkEntity,
            UserEntity userEntity
    ){
        linkRepository.delete(linkEntity);
        System.out.println(
                "[FileService]User "+userEntity.getUsername()+" deleted link "+linkEntity.getFilename()
        );

        userEntity.setSpaceUsage(userEntity.getSpaceUsage()-linkEntity.getFileByFileId().getSize());
        userRepository.saveAndFlush(userEntity);

        logService.createLog("Delete File " + linkEntity.getFilename(), null, userEntity);
    }
}
