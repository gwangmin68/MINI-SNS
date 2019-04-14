package kr.hs.dgsw.web190326.Controller;

import kr.hs.dgsw.web190326.Domain.Comment;
import kr.hs.dgsw.web190326.Domain.User;
import kr.hs.dgsw.web190326.Protocol.AttachmentProtocol;
import kr.hs.dgsw.web190326.Service.CommentService;
import kr.hs.dgsw.web190326.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
public class AttachmentController {

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @PostMapping("/attachment")
    public AttachmentProtocol upload(@RequestPart MultipartFile srcFile){
        String destFilename
                = "C:\\Users\\정광민\\ideaProjects\\web190326\\upload\\"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"))
                + UUID.randomUUID().toString() + "-"
                + srcFile.getOriginalFilename();
        try{
            File destFile = new File(destFilename);
            destFile.getParentFile().mkdirs();
            srcFile.transferTo(destFile);
            return new AttachmentProtocol(destFilename, srcFile.getOriginalFilename());
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/attachmentdown/{type}/{id}")
    public void download(@PathVariable String type, @PathVariable Long id, HttpServletRequest req, HttpServletResponse resp){
        try{
            String filePath;
            String fileName;
            if(type.equals("user")){
                User user = this.userService.view(id);
                filePath = user.getImgPath();
                fileName = user.getImgName();
            }else{
                Comment comment = this.commentService.view(id);
                filePath = comment.getImgPath();
                fileName = comment.getImgName();
            }

            File file = new File(filePath);
            if(file.exists() == false) return;

            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if(mimeType == null) mimeType = "application/octet-stream";

            resp.setContentType(mimeType);
            resp.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\";");
            resp.setContentLength((int)file.length());

            InputStream is = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(is, resp.getOutputStream());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
