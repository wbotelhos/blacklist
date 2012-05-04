package com.r7.blacklist.repository;

import java.util.Collection;

import com.r7.blacklist.exception.CommonException;
import com.r7.blacklist.model.Comment;

public interface CommentRepository {

	boolean save(Comment entity) throws CommonException;

	Collection<Comment> loadAll() throws CommonException;

	String serialize(Collection<Comment> commentList);

}
