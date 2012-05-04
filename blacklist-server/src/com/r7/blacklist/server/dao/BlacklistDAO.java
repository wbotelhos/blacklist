package com.r7.blacklist.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.r7.blacklist.server.model.Blacklist;
import com.r7.blacklist.server.util.ConnectionUtil;

public class BlacklistDAO {

	public Collection<Blacklist> getBlacklist() throws SQLException {
		Connection conn = ConnectionUtil.getConnection();

		PreparedStatement ps = conn.prepareStatement("SELECT id, word FROM Blacklist");
	    ResultSet rs = ps.executeQuery();

	    Collection<Blacklist> list = new ArrayList<Blacklist>();

	    while (rs.next()) {
	      list.add(new Blacklist(rs.getLong("id"), rs.getString("word")));
	    }

		ConnectionUtil.release(ps, rs);

		return list;
	}

}
