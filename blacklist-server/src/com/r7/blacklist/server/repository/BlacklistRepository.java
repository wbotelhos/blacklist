package com.r7.blacklist.server.repository;

import java.sql.SQLException;
import java.util.Collection;

import com.r7.blacklist.server.model.Blacklist;

public interface BlacklistRepository {

	Collection<Blacklist> loadAll() throws SQLException;

}
