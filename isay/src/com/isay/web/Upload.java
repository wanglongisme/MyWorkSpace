package com.isay.web;

import java.io.File;

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
		if (file!=null) {// �ж��ϴ����ļ��Ƿ�Ϊ��
            String path=null;// �ļ�·��
            String type=null;// �ļ�����
            String fileName=file.getOriginalFilename();// �ļ�ԭ����
            System.out.println("�ϴ����ļ�ԭ����:"+fileName);
            // �ж��ļ�����
            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            if (type!=null) {// �ж��ļ������Ƿ�Ϊ��
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())||"JPEG".equals(type.toUpperCase())) {
                    // ��Ŀ��������ʵ�ʷ������еĸ�·��
                    String realPath=request.getSession().getServletContext().getRealPath("/");
                    String tomcatWebappsUrl = "";
                    if(realPath.indexOf("isay")>0){
                    	tomcatWebappsUrl = realPath.split(request.getContextPath().substring(1))[0];
                    }else{
                    	tomcatWebappsUrl = realPath;
                    }
                    // �Զ�����ļ�����
                    String trueFileName=String.valueOf(System.currentTimeMillis())+fileName;
                    returnUrl = trueFileName;
                    // ���ô��ͼƬ�ļ���·��
                    path = tomcatWebappsUrl+"upload"+File.separator+"image"+File.separator+trueFileName;
                    // ת���ļ���ָ����·��
                    try {
						file.transferTo(new File(path));
					} catch (Exception e) {
						// TODO
						e.printStackTrace();
					}
                    returnUrl = "upload"+File.separator+"image"+File.separator+trueFileName;
                }else {
                    returnUrl = "����������Ҫ���ļ�����,�밴Ҫ�������ϴ�";
                }
            }else {
                returnUrl = "�ļ�����Ϊ��";
            }
        }else {
            returnUrl = "û���ҵ����Ӧ���ļ�";
        }
		System.out.println("����ͼƬ·��: upload"+File.separator+"image"+File.separator+returnUrl);
		return returnUrl;
	}
	

}
