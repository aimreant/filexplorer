package com.jianyujianyu.controller;

import com.jianyujianyu.model.DirectoryEntity;
import com.jianyujianyu.model.FileEntity;
import com.jianyujianyu.model.LinkEntity;
import com.jianyujianyu.model.UserEntity;
import com.jianyujianyu.repository.DirectoryRepository;
import com.jianyujianyu.repository.FileRepository;
import com.jianyujianyu.repository.LinkRepository;
import com.jianyujianyu.service.DirectoryService;
import com.jianyujianyu.service.FileService;
import com.jianyujianyu.service.LinkService;
import com.jianyujianyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by aimreant on 11/10/16.
 */
@Controller
public class FileController extends BaseController {

    @Autowired FileRepository fileRepository;
    @Autowired LinkRepository linkRepository;
    @Autowired DirectoryRepository directoryRepository;

    @Autowired FileService fileService;
    @Autowired LinkService linkService;
    @Autowired DirectoryService directoryService;
    @Autowired UserService userService;

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String fileGet(
            HttpSession httpSession,
            ModelMap modelMap,
            @RequestParam(value="dir", required=false) Integer dir,
            @RequestParam(value="deleted", required=false) Integer deleted
    ) {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("filexplorer_o");

        // Get current directory
        DirectoryEntity currentDirectoryEntity = null;
        if(dir != null) {
            currentDirectoryEntity = directoryRepository.findOne(dir);
            if(currentDirectoryEntity.getParentId() == null){
                // If it's root dir, do not return the 'back' button
                currentDirectoryEntity = null;
            }
        }else{
            currentDirectoryEntity = directoryRepository.findOneByNameAndUserByUserId("/", userEntity);
            dir = currentDirectoryEntity.getId();
        }
        modelMap.addAttribute("currentDirectoryEntity", currentDirectoryEntity);
        modelMap.addAttribute("currentDir", dir);

        // Get deleted parameter
        if(deleted != null && deleted == 1) {

            // Get all deleted links under current directories
            List<LinkEntity> linkEntityList = directoryService.getCurrentDeletedLinks(
                    dir, userEntity
            );

            // Get all deleted directories under current directories
            List<DirectoryEntity> directoryEntityList = directoryService.getCurrentDeletedDirs(
                    dir, userEntity
            );

            modelMap.addAttribute("deleted", true);
            modelMap.addAttribute("linkEntityList", linkEntityList);
            modelMap.addAttribute("directoryEntityList", directoryEntityList);

        }else {

            // Get all links under current directories
            List<LinkEntity> linkEntityList = directoryService.getCurrentLinks(
                    dir, userEntity
            );

            // Get all directories under current directories
            List<DirectoryEntity> directoryEntityList = directoryService.getCurrentDirs(
                    dir, userEntity
            );

            modelMap.addAttribute("deleted", false);
            modelMap.addAttribute("linkEntityList", linkEntityList);
            modelMap.addAttribute("directoryEntityList", directoryEntityList);
        }

        return "dashboard/file";
    }

    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public String filePost(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes attr,
            @RequestParam(value="dir", required=false) Integer dir) {

        UserEntity userEntity = (UserEntity) request.getSession().getAttribute("filexplorer_o");

        if(!file.isEmpty()) {

            // Need to check space according by user's level
            if(!userService.checkSpace(file, userEntity)){
                returnMsg(attr, ERROR, "Space not allow! Please contact the Admin");
                return "redirect:/files?dir=" + dir;
            }

            FileEntity fileEntity = fileService.createFile(request, file);
            if(fileEntity == null){
                returnMsg(attr, ERROR, "Database error!");
                return "redirect:/files?dir=" + dir;
            }
            System.out.println("[FileController]Get file " + fileEntity.getHash());
            Boolean fileExist = false;

            try {

                linkService.createLink(
                        fileEntity,
                        userEntity,
                        dir,
                        file
                );

                returnMsg(attr, SUCCESS, "Save file to server successfully!");
            } catch (Exception e) {
                returnMsg(attr, ERROR, e.toString());
            }
        }else{
            returnMsg(attr, ERROR, "Empty file!");
        }
        return "redirect:/files?dir=" + dir;
    }

    @RequestMapping(value = "/files/{link_id}", method = RequestMethod.PUT)
    public String fileRename(
            HttpServletRequest request,
            HttpSession httpSession,
            RedirectAttributes attr,
            @RequestParam(value = "dir", required = false) Integer dir,
            @RequestParam(value="pre", required=false) Integer pre,
            @RequestParam(value="forever", required=false) Integer forever,
            @RequestParam(value="deleted", required=false) Integer deleted,
            @PathVariable("link_id") Integer linkId) {
        // Notice: For user, a file is a link in essence

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("filexplorer_o");

        if(dir == null){
            dir = directoryRepository.findOneByNameAndUserByUserId("/", userEntity).getId();
        }

        // Not File entity, but Link entity
        LinkEntity linkEntity = linkRepository.findOne(linkId);
        if(linkEntity == null) {
            // The file not found
            returnMsg(attr, ERROR, "Invalid file!");
            return "redirect:/files?dir=" + dir;
        }

        if(pre != null && pre == 1) {
            // Pre-rename
            returnRenameMsg(
                    attr,
                    "Ready to rename file " + linkEntity.getFilename(),
                    "/files/" + linkId + "?forever=1&dir=" + dir
            );
            return "redirect:/files?dir=" + dir;
        }

        if(forever != null && forever == 1) {
            String name = request.getParameter("newname").trim();
            linkService.renameLink(name, linkEntity, userEntity);
            returnMsg(attr, SUCCESS, "Rename file successfully!");
            return "redirect:/files?dir=" + dir;
        }

        if(deleted != null && deleted == 1) {
            linkEntity.setDeletedAt(null);
            linkRepository.saveAndFlush(linkEntity);
            returnMsg(attr, SUCCESS, "Restore file successfully!");
            return "redirect:/files?deleted=1&dir=" + dir;
        }

        return "redirect:/files?dir=" + dir;

    }

    @Transactional
    @RequestMapping(value = "/files/{link_id}", method = RequestMethod.DELETE)
    public String fileRename(
            HttpSession httpSession,
            RedirectAttributes attr,
            @RequestParam(value = "dir", required = false) Integer dir,
            @RequestParam(value="forever", required=false) Integer forever,
            @PathVariable("link_id") Integer linkId){

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("filexplorer_o");

        if(dir == null){
            dir = directoryRepository.findOneByNameAndUserByUserId("/", userEntity).getId();
        }

        LinkEntity linkEntity = linkRepository.findByIdAndUserByUserId(linkId, userEntity);
        if(linkEntity == null) {
            // The file not found
            returnMsg(attr, ERROR, "Invalid file!");
            return "redirect:/files?dir=" + dir;
        }

        if(forever != null && forever == 1) {
            linkService.deleteLink(linkEntity, userEntity);
            returnMsg(attr, SUCCESS, "Delete file "+ linkEntity.getFilename() +" forever succeed!");
            return "redirect:/files?deleted=1&dir=" + dir;
        }

        linkEntity.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        linkRepository.saveAndFlush(linkEntity);
        returnMsg(attr, SUCCESS, "Delete succeed!");
        return "redirect:/files?dir=" + dir;
    }

    @RequestMapping(value = "/files/{link_id}", method = RequestMethod.GET)
    public void fileDownload(
            HttpSession httpSession,
            @PathVariable("link_id") Integer linkId,
            HttpServletRequest request ,
            HttpServletResponse response
    ){
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("filexplorer_o");

        LinkEntity linkEntity = linkRepository.findByIdAndUserByUserId(linkId, userEntity);

        if(linkEntity != null){
            try {
                File file=new File(
                        request.getSession().getServletContext().getRealPath("WEB-INF")
                        + File.separator +
                        "store"
                        + File.separator +
                        linkEntity.getFileByFileId().getHash()
                );
                response.setCharacterEncoding("utf-8");
                response.setContentType("multipart/form-data");
                response.setHeader(
                        "Content-Disposition",
                        "attachment;filename="+
                        java.net.URLEncoder.encode(linkEntity.getFilename() ,"UTF-8")
                );
                response.setHeader("Content-Length", "" + file.length());

                InputStream in = new BufferedInputStream(new FileInputStream(file));
                OutputStream out=response.getOutputStream();
                byte[] b=new byte[1024];
                int length;
                while((length=in.read(b)) != -1){
                    out.write(b, 0, length);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Permission for admin
     * @return
     */
    @RequestMapping(value = "/admin/files/{file_id}", method = RequestMethod.GET)
    public String fileDetail(
            HttpSession httpSession,
            RedirectAttributes attr,
            ModelMap modelMap,
            @PathVariable("file_id") Integer fileId
    ){

        FileEntity fileEntity = fileRepository.findOne(fileId);
        if(fileEntity == null) {
            // The file not found
            returnMsg(attr, ERROR, "File not exists!");
            return "redirect:/admin/files";
        }

        List<LinkEntity> linkEntityList = (List<LinkEntity>)fileEntity.getLinksById();

        modelMap.addAttribute("fileEntity", fileEntity);
        modelMap.addAttribute("linkEntityList", linkEntityList);

        return "dashboard/admin/file_detail";
    }
}
