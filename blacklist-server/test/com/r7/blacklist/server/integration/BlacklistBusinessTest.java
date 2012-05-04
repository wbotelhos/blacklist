package com.r7.blacklist.server.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jintegrity.core.JIntegrity;
import com.jintegrity.helper.SQLHelper;
import com.r7.blacklist.server.business.BlacklistBusiness;
import com.r7.blacklist.server.model.Blacklist;
import com.r7.blacklist.server.repository.BlacklistRepository;

public class BlacklistBusinessTest {

	private final JIntegrity helper = new JIntegrity();
	private final SQLHelper sqlHelper = new SQLHelper();

	private BlacklistRepository repository;

	@Before
	public void setup() throws IOException, SQLException {
		repository = new BlacklistBusiness();

		sqlHelper.run("sql/create-schema", "sql/create-blacklist", "sql/create-comment");
		helper.cleanAndInsert();
	}

	@After
	public void tearDown() throws IOException, SQLException {
		sqlHelper.run("sql/drop-blacklist", "sql/drop-comment");
	}

	@Test
	public void shouldLoadAll() throws IOException, SQLException {
		// given

		// when
		List<Blacklist> blacklist = (List<Blacklist>) repository.loadAll();

		Blacklist censure1 = blacklist.get(0);
		Blacklist censure2 = blacklist.get(1);
		Blacklist censure3 = blacklist.get(2);

		// then
		assertEquals("deve encontrar os 3 registros", 3, blacklist.size());

		assertEquals("deve buscar o ID da censura 1", 1, censure1.getId().intValue());
		assertEquals("deve buscar a censura 1", "arma", censure1.getWord());

		assertEquals("deve buscar o ID da censura 2", 2, censure2.getId().intValue());
		assertEquals("deve buscar a censura 2", "bumbum", censure2.getWord());

		assertEquals("deve buscar o ID da censura 3", 3, censure3.getId().intValue());
		assertEquals("deve buscar a censura 3", "BBB", censure3.getWord());
	}

}
