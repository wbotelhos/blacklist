package com.r7.blacklist.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.r7.blacklist.server.exception.CommonException;
import com.r7.blacklist.server.model.Comment;
import com.r7.blacklist.server.repository.CommentRepository;
import com.r7.blacklist.server.util.Utils;

public class CommentService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private CommentRepository repository;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = "";
	
		try {
			repository = (CommentRepository) request.getAttribute("repository");

			String comment = request.getParameter("comment");
			comment = Utils.decoderText(comment);

			Comment entity = repository.censure(comment);

			json = repository.serialize(entity);

	        response.setStatus(200);
		} catch (CommonException e) {
			json = e.getMessage();
		}

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json);
	}

}
