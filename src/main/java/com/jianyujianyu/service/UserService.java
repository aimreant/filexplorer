package com.jianyujianyu.service;

import com.jianyujianyu.model.DirectoryEntity;
import com.jianyujianyu.model.LevelEntity;
import com.jianyujianyu.model.UserEntity;
import com.jianyujianyu.repository.DirectoryRepository;
import com.jianyujianyu.repository.LevelRepository;
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
public class UserService extends BaseService{

    @Autowired UserRepository userRepository;
    @Autowired LevelRepository levelRepository;
    @Autowired DirectoryRepository directoryRepository;
    @Autowired LogService logService;

    @Transactional
    public UserEntity createUser(String username, String password, LevelEntity levelByLevelId){

        try {
            password = getHashCode(password);
            UserEntity userEntity = new UserEntity(
                    username,
                    password,
                    levelByLevelId
            );
            userRepository.saveAndFlush(userEntity);

            // Create root directory after creating user
            DirectoryEntity directoryEntity = new DirectoryEntity(
                    "/",
                    userEntity,
                    null
            );

            System.out.println("[UserService]Create new user and create a root directory for him");
            directoryRepository.saveAndFlush(directoryEntity);

            logService.createLog("Create User " + userEntity.getUsername(), null, userEntity);
            return userEntity;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean checkSpace(MultipartFile file, UserEntity userEntity){
        return file.getSize() <= userEntity.getLevelByLevelId().getSpaceAllow() - userEntity.getSpaceUsage();
    }

    public void deleteUser(UserEntity userEntity){
        userRepository.delete(userEntity);
        // No logging
    }
}
