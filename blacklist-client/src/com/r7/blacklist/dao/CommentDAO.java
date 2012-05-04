package com.r7.blacklist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.r7.blacklist.model.Comment;
import com.r7.blacklist.util.ConnectionUtil;

public class CommentDAO {

	public boolean save(Comment comment) throws SQLException {
		Connection conn = ConnectionUtil.getConnection();

		PreparedStatement ps = conn.prepareStatement("INSERT INTO Comment (censured, original, valid, createdAt) VALUES (?, ?, ?, ?)");
		ps.setString(1, comment.getCensured());
		ps.setString(2, comment.getOriginal());
		ps.setBoolean(3, comment.isValid());
		ps.setTimestamp(4, new Timestamp(comment.getCreatedAt().getTime()));

		boolean isSuccess = ps.executeUpdate() == 1;

		ConnectionUtil.commit(true);
		ConnectionUtil.release(ps);

		return isSuccess;
	}

	public Collection<Comment> loadAll() throws SQLException {
		Connection conn = ConnectionUtil.getConnection();

		PreparedStatement ps = conn.prepareStatement("SELECT id, censured, original, valid, createdAt FROM Comment ORDER BY id DESC");
	    ResultSet rs = ps.executeQuery();

	    Collection<Comment> list = new ArrayList<Comment>();

	    while (rs.next()) {
	      list.add(new Comment(rs.getLong("id"), rs.getString("censured"), rs.getString("original"), rs.getBoolean("valid"), rs.getTimestamp("createdAt")));
	    }

		ConnectionUtil.release(ps, rs);

		return list;
	}

}
