package com.yunpan.corsfilter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
	

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		if (session.getAttribute("user") != null) {
			chain.doFilter(req, resp);
		} else
			json.put("status", 0);
		out.write(json.toString());
		out.flush();
		out.close();

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		

	}

}
