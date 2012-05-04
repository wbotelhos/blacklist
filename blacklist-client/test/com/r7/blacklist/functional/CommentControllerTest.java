package com.r7.blacklist.functional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.r7.blacklist.controller.CommentController;
import com.r7.blacklist.exception.CommonException;
import com.r7.blacklist.model.Comment;
import com.r7.blacklist.repository.CommentRepository;

public class CommentControllerTest {

	private CommentController controller;

	@Mock private HttpServletRequest request;
	@Mock private HttpServletResponse response;
	@Mock private CommentRepository repository;

	@Before
	public void setup() throws IOException, SQLException {
		MockitoAnnotations.initMocks(this);

		controller = new CommentController();
	}

	@Test
	public void shouldRequestSave() throws CommonException, IOException, ServletException  {
		// given
		Comment comment = new Comment();
		comment.setOriginal("bumbum");
		comment.setCensured("xxxx");
		comment.setValid(false);

		Comment expected = new Comment();
		expected.setOriginal("bumbum");
		expected.setCensured("xxxx");
		expected.setValid(false);

		when(request.getAttribute("repository")).thenReturn(repository);
		when(request.getParameter("action")).thenReturn("save");
		when(request.getParameter("original")).thenReturn(comment.getOriginal());
		when(request.getParameter("censured")).thenReturn(comment.getCensured());
		when(request.getParameter("valid")).thenReturn(comment.isValid().toString());

		when(repository.save(comment)).thenReturn(comment.isValid());

		when(request.getContentType()).thenReturn("application/json");
		when(request.getHeader("Cache-Control")).thenReturn("no-cache");
		when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

		// when
		controller.service(request, response);

		// then
		verify(repository).save(Mockito.any(Comment.class));
		verify(response).getWriter();

		assertEquals("Deve ter o content type json", "application/json", request.getContentType());
		assertEquals("Deve ter o cache desativado", "no-cache", request.getHeader("Cache-Control"));
		assertEquals("Deve recuperar o comentario original", "bumbum", request.getParameter("original"));
		assertEquals("Deve recuperar o comentario censurado", "xxxx", request.getParameter("censured"));
		assertEquals("Deve ser invalido", "false", request.getParameter("valid"));
	}

	@Test
	public void shouldRequestList() throws CommonException, ServletException, IOException {
		// given
		Comment comment = new Comment();
		comment.setOriginal("bumbum");
		comment.setCensured("xxxx");
		comment.setValid(false);

		Comment expected = new Comment();
		expected.setOriginal("bumbum");
		expected.setCensured("xxxx");
		expected.setValid(false);

		Collection<Comment> commentList = new ArrayList<Comment>();
		commentList.add(expected);

		when(request.getAttribute("repository")).thenReturn(repository);
		when(request.getParameter("action")).thenReturn("list");

		when(repository.loadAll()).thenReturn(commentList);
		when(repository.serialize(commentList)).thenReturn("{ \"censured\": \"" + expected.getCensured() + "\", \"original\": \"" + expected.getOriginal() + "\", \"valid\": " + expected.isValid() + " }");

		when(request.getContentType()).thenReturn("application/json");
		when(request.getHeader("Cache-Control")).thenReturn("no-cache");
		when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

		// when
		controller.service(request, response);

		// then
		verify(repository).loadAll();
		verify(repository).serialize(commentList);

		assertEquals("Deve ter o content type json", "application/json", request.getContentType());
		assertEquals("Deve ter o cache desativado", "no-cache", request.getHeader("Cache-Control"));
	}

}
