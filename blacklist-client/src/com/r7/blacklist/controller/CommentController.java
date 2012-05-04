package com.r7.blacklist.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.r7.blacklist.exception.CommonException;
import com.r7.blacklist.model.Comment;
import com.r7.blacklist.repository.CommentRepository;
import com.r7.blacklist.util.Utils;

public class CommentController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private CommentRepository repository;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = "";

		try {
			String action = request.getParameter("action");

			repository = (CommentRepository) request.getAttribute("repository");

			if (action != null) {
				if (action.equals("save")) {
					boolean isSuccess = save(request);

					json = "{ \"success\": " + isSuccess + ", \"message\": \"Mensagem enviada com sucesso!\" }";
				} else if (action.equals("list")) {
					json = loadAll();
				}
			}

	        response.setStatus(200);
		} catch (CommonException e) {
			json = e.getMessage();
		}

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json);
	}

	private boolean save(HttpServletRequest request) throws CommonException {
		String censured = request.getParameter("censured");
		censured = Utils.decoderText(censured);

		String original = request.getParameter("original");
		original = Utils.decoderText(original);

		String isValid = request.getParameter("valid");

		Comment entity = new Comment();
		entity.setCensured(censured);
		entity.setOriginal(original);
		entity.setValid(Boolean.parseBoolean(isValid));

		return repository.save(entity);
	}

	private String loadAll() throws CommonException {
		Collection<Comment> commentList = repository.loadAll();

		return repository.serialize(commentList);
	}

}
