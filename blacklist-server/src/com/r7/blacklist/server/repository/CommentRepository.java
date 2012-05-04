package com.r7.blacklist.server.repository;

import com.r7.blacklist.server.exception.CommonException;
import com.r7.blacklist.server.model.Comment;

public interface CommentRepository {

	Comment censure(String comment) throws CommonException;

	String serialize(Comment comment);

}
