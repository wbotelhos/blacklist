package com.r7.blacklist.business;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import com.r7.blacklist.dao.CommentDAO;
import com.r7.blacklist.exception.CommonException;
import com.r7.blacklist.model.Comment;
import com.r7.blacklist.repository.CommentRepository;

public class CommentBusiness implements CommentRepository {

	private static final DateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

	private final CommentDAO dao = new CommentDAO();

	public boolean save(Comment entity) throws CommonException {
		try {
			if (entity.getId() == null) {
				entity.setCreatedAt(new Date());
			}

			return dao.save(entity);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CommonException("Não foi possível salvar o comentário!<br/>Detalhes: " + e.getMessage());
		}
	}

	public Collection<Comment> loadAll() throws CommonException {
		try {
			return dao.loadAll();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CommonException("Não foi possível listar os comentários!<br/>Detalhes: " + e.getMessage());
		}
	}

	public String serialize(Collection<Comment> commentList) {
		if (commentList != null) {
			String json = "{ entityList: [ ";

			for (Comment comment : commentList) {
				json += "{ \"censured\": \"" + comment.getCensured() + "\", \"original\": \"" + comment.getOriginal() + "\", \"valid\": " + comment.isValid() + ", \"createdAt\": \"" + format.format(comment.getCreatedAt()) + "\" }";
			}
	
			json += " ] }";
	
			return json.replace("}{", "}, {");
		}

		return "{}";
	}

}
