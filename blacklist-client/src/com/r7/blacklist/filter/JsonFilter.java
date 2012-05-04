package com.r7.blacklist.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.r7.blacklist.business.CommentBusiness;

public class JsonFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		((HttpServletRequest) request).setAttribute("repository", new CommentBusiness());

		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		
	}

	public void destroy() {
		
	}

}
