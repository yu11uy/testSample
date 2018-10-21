package com.example.demo.login.domain.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;

/**
 * RowMapperを使用版
 */

@Repository("UserDaoJdbcImpl2")
public class UserDaoJdbcImpl2 extends UserDaoJdbcImpl{

	@Autowired
	private JdbcTemplate jdbc;
	
	//Userテーブルのデータを１件取得
	@Override
	public User selectOne(String userId) {
		
		String sql = "SELECT * FROM m_user WHERE user_id = ?";
		//RowMapperの生成
		UserRowMapper rowMapper = new UserRowMapper();
		//SQL実行
		return jdbc.queryForObject(sql, rowMapper, userId);
	}

	//Userテーブルの全データを取得
	@Override
	public List<User> selectMany() {
		
		String sql = "SELECT * FROM m_user";
		UserRowMapper rowMapper = new UserRowMapper();
		//SQL実行
		return jdbc.query(sql, rowMapper);
	}
}
