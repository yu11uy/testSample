package com.example.demo.login.domain.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;

/**
 * BeanPropertyRowMapperを使用版
 * カラム名と同一フィールド名がクラスにあれば、自動でマッピング
 * ■マッピング仕様条件
 * １．カラム名は単語をアンダースコアで区切る	例：user_id
 * ２．フィールド名は２つ目の単語から大文字にする	例：String userId;
 */

@Repository("UserDaoJdbcImpl3")
public class UserDaoJdbcImpl3 extends UserDaoJdbcImpl{

	@Autowired
	private JdbcTemplate jdbc;
	
	//Userテーブルのデータを１件取得
	@Override
	public User selectOne(String userId) {
		
		String sql = "SELECT * FROM m_user WHERE user_id = ?";
		//RowMapper(BeanPropertyRowMapper)	の生成
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		//SQL実行
		return jdbc.queryForObject(sql, rowMapper, userId);
	}

	//Userテーブルの全データを取得
	@Override
	public List<User> selectMany() {
		
		String sql = "SELECT * FROM m_user";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		//SQL実行
		return jdbc.query(sql, rowMapper);
	}

}
