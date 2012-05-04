package com.r7.blacklist.server.business;

import java.sql.SQLException;
import java.util.Collection;

import com.r7.blacklist.server.dao.BlacklistDAO;
import com.r7.blacklist.server.model.Blacklist;
import com.r7.blacklist.server.repository.BlacklistRepository;

public class BlacklistBusiness implements BlacklistRepository {

	private final BlacklistDAO dao = new BlacklistDAO();

	public Collection<Blacklist> loadAll() throws SQLException {
		return dao.getBlacklist();
	}

}
