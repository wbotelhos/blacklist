package com.r7.blacklist.server.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jintegrity.core.JIntegrity;
import com.jintegrity.helper.SQLHelper;
import com.r7.blacklist.server.business.CommentBusiness;
import com.r7.blacklist.server.exception.CommonException;
import com.r7.blacklist.server.model.Comment;
import com.r7.blacklist.server.repository.CommentRepository;

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
	public void shouldNotChangeTheCase() throws IOException, SQLException, CommonException {
		// given
		String comment = "MAIUSCULA minuscula";

		// when
		Comment actual = repository.censure(comment);

		// then
		assertEquals("deveria manter o case", "MAIUSCULA minuscula", actual.getCensured());
	}

	@Test
	public void shouldCensureAlphabet() throws IOException, SQLException, CommonException {
		// given
		String comment = "A minha àrmá é para atirar no bumbum dos à toas do BBB.";

		// when
		Comment actual = repository.censure(comment);

		// then
		assertEquals("deveria censurar as letras", "A minha xxxx é para atirar no xxxx dos à toas do xxxx.", actual.getCensured());
	}

	@Test
	public void shouldCensureCharacters() throws IOException, SQLException, CommonException {
		// given
		String comment = "A minha àrm@ é para atirar no bumbum dos à toas do BBB.";

		// when
		Comment actual = repository.censure(comment);

		// then
		assertEquals("deveria censurar os caracteres", "A minha xxxx é para atirar no xxxx dos à toas do xxxx.", actual.getCensured());
	}

	@Test
	public void shouldCensureRepeatedLetters() throws IOException, SQLException, CommonException {
		// given
		String comment = "A minha àrma@a@ é para atirar no bumbum dos à toas do BBB.";

		// when
		Comment actual = repository.censure(comment);

		// then
		assertEquals("deveria censurar as letras repetidas", "A minha xxxx é para atirar no xxxx dos à toas do xxxx.", actual.getCensured());
	}

	@Test
	public void shouldCensureAllWordThatContainsPartOfWord() throws IOException, SQLException, CommonException {
		// given
		String comment = "Isso é uma armação Bino!";

		// when
		Comment actual = repository.censure(comment);

		// then
		assertEquals("deveria censurar as palavras contidas", "Isso é uma xxxx Bino!", actual.getCensured());
	}

	@Test
	public void shouldSerializeComment() throws IOException, SQLException {
		// given
		Comment comment = new Comment();
		comment.setOriginal("original");
		comment.setCensured("censured");
		comment.setValid(true);

		// when
		String json = repository.serialize(comment);

		// then
		assertEquals("deveria converte para sintaxe JSON", "{ \"original\": \"original\", \"censured\": \"censured\", \"valid\": true }", json);
	}

	@Test
	public void shouldNotReplaceDotsBetweenCensuredWord() throws IOException, SQLException, CommonException {
		// given
		String comment = "AAA BBB.ccc DDD";

		// when
		Comment actual = repository.censure(comment);

		// then
		assertEquals("deveria censurar e substituir o restante", "AAA xxxx.ccc DDD", actual.getCensured());
	}

	@Test
	public void shouldBeAValidComment() throws IOException, SQLException, CommonException {
		// given
		String comment = "normal comment";

		// when
		Comment actual = repository.censure(comment);

		// then
		assertTrue("deveria ser um comentario valido", actual.isValid());
	}

	@Test
	public void shouldBeAInvalidComment() throws IOException, SQLException, CommonException {
		// given
		String comment = "BBB: not normal comment";

		// when
		Comment actual = repository.censure(comment);

		// then
		assertFalse("deveria ser um comentario invalido", actual.isValid());
	}

}
