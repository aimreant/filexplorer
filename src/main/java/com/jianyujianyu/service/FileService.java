package com.jianyujianyu.service;

import com.jianyujianyu.model.FileEntity;
import com.jianyujianyu.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by aimreant on 12/8/16.
 */
@Service
@Transactional
public class FileService {

    @Autowired FileRepository fileRepository;

    public FileEntity createFile(HttpServletRequest request , MultipartFile file){
        FileEntity fileEntity = null;
        Boolean fileExist = false;

        try {
            // Position to save file
            String rootPath = request.getSession().getServletContext().getRealPath("WEB-INF");
            File dir = new File(rootPath + File.separator + "store");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            String fileMD5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(inputStream);
            inputStream.close();

            // Write file to server
            File serverFile = new File(dir.getAbsolutePath() + File.separator + fileMD5);
            fileEntity = fileRepository.findByHash(fileMD5);

            // Deal with four situations
            if((!serverFile.exists())&&(fileEntity != null)){
                // DB has record while file not exist, delete the record
                file.transferTo(serverFile);
                System.out.println("[FileService]File not exists but db has record");
                // Then return null, meaning that file not exist
                return fileEntity;

            }else if((serverFile.exists())&&(fileEntity == null)){
                // DB has no record while file exists, restore the file into DB
                fileEntity = new FileEntity(
                        fileMD5,
                        file.getSize()
                );
                fileRepository.saveAndFlush(fileEntity);
                System.out.println("[FileService]File exists but db has no record");
                // Then return the file entity;
                return fileEntity;

            }else if((!serverFile.exists())&&(fileEntity == null)) {
                // This is a new file
                // Need to check space according by user's level, using UserService called by FileController
                file.transferTo(serverFile);
                fileEntity = new FileEntity(
                        fileMD5,
                        file.getSize()
                );
                fileRepository.saveAndFlush(fileEntity);
                // fileEntity need committing

                System.out.println("[FileService]Receive new file " + file.getOriginalFilename() + " (" + fileMD5 + ")");
                return fileEntity;

            }else if((serverFile.exists())&&(fileEntity != null)){
                // File exist and record in DB
                fileExist = true;
                System.out.println("[FileService]Receive repeated file " + file.getOriginalFilename() + " (" + fileMD5 + ")");
                return fileEntity;

            }

            // Unknown error
            return null;

        } catch (Exception e) {
            return null;
        }
    }


}
