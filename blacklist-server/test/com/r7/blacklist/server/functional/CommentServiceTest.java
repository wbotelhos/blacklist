package com.r7.blacklist.server.functional;

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
import org.mockito.MockitoAnnotations;

import com.r7.blacklist.server.exception.CommonException;
import com.r7.blacklist.server.model.Comment;
import com.r7.blacklist.server.repository.CommentRepository;
import com.r7.blacklist.server.service.CommentService;

public class CommentServiceTest {

	private CommentService service;

	@Mock private HttpServletRequest request;
	@Mock private HttpServletResponse response;
	@Mock private CommentRepository repository;

	@Before
	public void setup() throws IOException, SQLException {
		MockitoAnnotations.initMocks(this);

		service = new CommentService();
	}

	@Test
	public void shouldRequestList() throws ServletException, IOException, SQLException, CommonException {
		// given
		String comment = "bumbum";

		Comment expected = new Comment();
		expected.setOriginal("bumbum");
		expected.setCensured("xxxx");
		expected.setValid(false);

		Collection<Comment> commentList = new ArrayList<Comment>();
		commentList.add(expected);

		when(request.getAttribute("repository")).thenReturn(repository);
		when(request.getParameter("comment")).thenReturn(comment);

		when(repository.censure(comment)).thenReturn(expected);
		when(repository.serialize(expected)).thenReturn("{ \"censured\": \"" + expected.getCensured() + "\", \"original\": \"" + expected.getOriginal() + "\", \"valid\": " + expected.isValid() + " }");

		when(request.getContentType()).thenReturn("application/json");
		when(request.getHeader("Cache-Control")).thenReturn("no-cache");
		when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

		// when
		service.service(request, response);

		// then
		verify(repository).censure(comment);
		verify(repository).serialize(expected);

		assertEquals("Deve ter o content type json", "application/json", request.getContentType());
		assertEquals("Deve ter o cache desativado", "no-cache", request.getHeader("Cache-Control"));
	}

}
