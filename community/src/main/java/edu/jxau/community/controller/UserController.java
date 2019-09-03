package edu.jxau.community.controller;

import edu.jxau.community.annotation.LoginRequired;
import edu.jxau.community.entity.User;
import edu.jxau.community.service.UserService;
import edu.jxau.community.utils.CommunityUtil;
import edu.jxau.community.utils.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @title: community
 * @ClassName UserController.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
@Controller
@RequestMapping(path = "/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${community.file.type}")
    private String suffixList;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @LoginRequired
    @RequestMapping(path = "/setting",method = RequestMethod.GET)
    public String getSettingPage(){
        return "/site/setting";
    }

    @LoginRequired
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model){

        if (headerImage == null){
            model.addAttribute("error","未选择图片！");
            return "/site/setting";
        }

        String fileName = headerImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件格式不正确");
            return "/site/setting";
        }
        if (!suffixList.contains(suffix.trim().toLowerCase())) {
            model.addAttribute("error","文件格式不正确");
            return "/site/setting";
        }

        /**
         * hostHolder.getUser().getName() + "_" + CommunityUtil.generateUUID() + suffix;
         * 前缀可以用来查看历史头像
         */
        fileName = CommunityUtil.generateUUID() + "." + suffix;
        File desc = new File(uploadPath + "/" + fileName);
        try {
            headerImage.transferTo(desc);
        } catch (IOException e) {
            logger.error("文件上传失败"+e.getMessage());
            throw new RuntimeException("文件上传失败，服务端异常：", e);
        }
        // 更新当前用户的头像的路径(web访问路径)
        // http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);
        return "redirect:/index";
    }

    @RequestMapping(path = "/header/{fileName}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response){

        fileName = uploadPath + "/" + fileName;
        // 设置响应图片格式
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        response.setContentType("image/"+suffix);

        try (
                FileInputStream fileInputStream = new FileInputStream(fileName);
                OutputStream outputStream = response.getOutputStream();
                ) {
            byte[] bytes = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0, b);
            }
        }catch (IOException e){
            logger.error("响应图片失败：" + e.getMessage());
        }
    }

    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(@Param("old_password") String old_password,
                                 @Param("new_password") String new_password,
                                 Model model){
        User user = hostHolder.getUser();
        if(!user.getPassword().equals(CommunityUtil.md5(old_password + user.getSalt()))){
            model.addAttribute("old_password_msg","密码错误！");
            return "/site/setting";
        }

        String password = CommunityUtil.md5(new_password + user.getSalt());
        userService.updatePassword(user.getId(), password);
        return "redirect:/logout";
    }
}
