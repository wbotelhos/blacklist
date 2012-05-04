package com.r7.blacklist.server.business;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.r7.blacklist.server.exception.CommonException;
import com.r7.blacklist.server.model.Blacklist;
import com.r7.blacklist.server.model.Comment;
import com.r7.blacklist.server.repository.BlacklistRepository;
import com.r7.blacklist.server.repository.CommentRepository;

public class CommentBusiness implements CommentRepository {

	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("replacements");
	private static final String[] LETRAS = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

	private final BlacklistRepository blacklistRepository = new BlacklistBusiness();

	public Comment censure(String comment) throws CommonException {
		try {
			Collection<Blacklist> blacklist = blacklistRepository.loadAll();
			Map<String, String> replacements = getReplacements();
	
			String censured = comment;
	
			if (blacklist != null) {
				for (Blacklist item : blacklist) {
					String word = item.getWord();
					String regex = "(?i)";
		
					for (int i = 0; i < word.length(); i++) {
						String letter = Character.toString(word.charAt(i));
						String replacement = replacements.get(letter.toLowerCase()).replace(',', '|');
		
						regex += "(" + replacement + ")+";
					}
		
					regex += "";
		
					censured = censured.replaceAll(regex, "xxxx").replaceAll("(?i)[\\wÀ-ÿ]*(xxxx)[\\wÀ-ÿ]*", "xxxx");
				}
			}
	
			Comment entity = new Comment();
			entity.setOriginal(comment);
			entity.setCensured(censured);
			entity.setValid(censured.equals(entity.getOriginal()));

			return entity;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CommonException("Não foi possível validar o comentário!<br/>Detalhe: " + e.getMessage());
		}
	}

	private Map<String, String> getReplacements() {
		Map<String,String> map = new HashMap<String, String>();

		for (int i = 0; i < LETRAS.length; i++) {
			map.put(LETRAS[i], BUNDLE.getString(LETRAS[i]));
		}

		return map;
	}

	public String serialize(Comment comment) {
		return "{ \"original\": \"" + comment.getOriginal()  + "\", \"censured\": \"" + comment.getCensured()  + "\", \"valid\": " + comment.isValid() + " }";
	}

}
