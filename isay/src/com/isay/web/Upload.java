package com.isay.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller("upload")
public class Upload {
	
	@ResponseBody
	@RequestMapping(value="/upload/img", method=RequestMethod.POST)
	public String uploadImg(MultipartFile file,HttpServletRequest request) throws Exception{
		String returnUrl = "";
		if (file!=null) {// 判断上传的文件是否为空
            String path=null;// 文件路径
            String type=null;// 文件类型
            String fileName=file.getOriginalFilename();// 文件原名称
            System.out.println("上传的文件原名称:"+fileName);
            // 判断文件类型
            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            if (type!=null) { // 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())||"JPEG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
                    String realPath=request.getSession().getServletContext().getRealPath("/");
                    String tomcatWebappsUrl = "";
                    if(realPath.indexOf("isay")>0){
                    	tomcatWebappsUrl = realPath.split(request.getContextPath().substring(1))[0];
                    }else{
                    	tomcatWebappsUrl = realPath;
                    }
                    // 自定义的文件名称
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                    String trueFileName = formatter.format(new Date())+"."+type;
                    returnUrl = trueFileName;
                    // 设置存放图片文件的路径
        	     	//TODO
                    path = tomcatWebappsUrl+"/upload"+File.separator+"image"+File.separator+trueFileName;
                    // 转存文件到指定的路径
                    
                    System.out.println(path);
                    try {
						file.transferTo(new File(path));
					} catch (Exception e) {
						e.printStackTrace();
					}
                    returnUrl = "upload"+File.separator+"image"+File.separator+trueFileName;
                    System.out.println(returnUrl);
                    return returnUrl;
                }
                returnUrl = "文件类型错误,请按要求重新上传";
                return returnUrl;
            }
        }
        returnUrl = "没有找到相对应的文件";
        
		System.out.println("返回图片路径: "+returnUrl);
		return returnUrl;
	}
	

}
