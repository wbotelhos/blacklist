package com.r7.blacklist.helper;

import java.io.IOException;
import java.sql.SQLException;

import com.jintegrity.core.JIntegrity;
import com.jintegrity.helper.SQLHelper;

public class PopulateHelper {

	public static void main(String[] args) throws IOException, SQLException {
		new SQLHelper().run("sql/create-schema", "sql/create-blacklist", "sql/create-comment");
		new JIntegrity().cleanAndInsert();
	}

}
