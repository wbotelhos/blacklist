package com.r7.blacklist.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jintegrity.core.JIntegrity;
import com.jintegrity.helper.SQLHelper;
import com.r7.blacklist.business.CommentBusiness;
import com.r7.blacklist.exception.CommonException;
import com.r7.blacklist.model.Comment;
import com.r7.blacklist.repository.CommentRepository;

public class CommentBusinessTest {

	private final JIntegrity helper = new JIntegrity();
	private final SQLHelper sqlHelper = new SQLHelper();

	private CommentRepository repository;

	@Before
	public void setup() throws IOException, SQLException {
		repository = new CommentBusiness();

		sqlHelper.run("sql/create-schema", "sql/create-blacklist", "sql/create-comment");
		helper.cleanAndInsert();
	}

	@After
	public void tearDown() throws IOException, SQLException {
		sqlHelper.run("sql/drop-blacklist", "sql/drop-comment");
	}

	@Test
	public void shouldLoadAll() throws IOException, SQLException, CommonException {
		// given

		// when
		List<Comment> commentList = (List<Comment>) repository.loadAll();

		Comment comment1 = commentList.get(0);
		Comment comment2 = commentList.get(1);
		Comment comment3 = commentList.get(2);

		// then
		assertEquals("deve encontrar os 3 registros", 3, commentList.size());

		assertEquals("deve buscar a censura correta", "A minha xxxx é para atirar no xxxx dos à toas do xxxx. Tudo xxxx!", comment1.getCensured());
		assertEquals("deve buscar o original correto", "A minha àrmá é para atirar no bumbum dos à toas do BBB. Tudo armado!", comment1.getOriginal());
		assertTrue("deve ser invalido", comment3.isValid());

		assertEquals("deve buscar a censura correta", "comentário xxxx para censura!", comment2.getCensured());
		assertEquals("deve buscar o original correto", "comentário armado para censura!", comment2.getOriginal());
		assertFalse("deve ser invalido", comment2.isValid());

		assertEquals("deve buscar a censura correta", "meu comentário fofo!", comment3.getCensured());
		assertEquals("deve buscar o original correto", "meu comentário fofo!", comment3.getOriginal());
		assertFalse("deve ser valido", comment1.isValid());
	}

	@Test
	public void shouldSave() throws IOException, SQLException, CommonException {
		// given
		Comment comment = new Comment();
		comment.setCensured("Minha xxx!");
		comment.setOriginal("Minha arma!");
		comment.setValid(false);

		// when
		repository.save(comment);

		List<Comment> commentList = (List<Comment>) repository.loadAll();

		// then
		assertEquals("deve encontrar os 4 registros", 4, commentList.size());

		assertEquals("deve buscar a censura correta", "Minha xxx!", commentList.get(0).getCensured());
		assertEquals("deve buscar o original correto", "Minha arma!", commentList.get(0).getOriginal());
		assertFalse("deve ser invalido", commentList.get(0).isValid());
	}

}
