package com.verifycode.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

/**
 * <p><b>AuthImage Description:</b> (验证码)</p>
 * <b>DATE:</b> 2016年6月2日 下午3:53:12
 */
public class AuthImage extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {  
    static final long serialVersionUID = 1L;  
    private static String verifyCode = null;
    @SuppressWarnings("static-access")
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
          
        //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session  
        HttpSession session = request.getSession(true);  
        //删除以前的
        session.removeAttribute("verifyCode");
        session.setAttribute("verifyCode", verifyCode.toLowerCase());
        this.verifyCode = verifyCode.toLowerCase();
        System.out.println(session.getAttribute("verifyCode"));
        //生成图片  
        int w = 100, h = 30;  
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);  

    }
    public String getVerifyCode(){
		return this.verifyCode;
    	
    }
} 