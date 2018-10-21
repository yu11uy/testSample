package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

/*ポイント
 * @Transactional
 * 一般的には、ビジネスロジックを担当するサービスクラスに使用
 */
@Transactional
@Service
public class UserService {
	
	/**
	 * ポイント
	 * @Qualifier(対象クラス)
	 * 同じインターフェイスを実装したクラスが複数あるクラスを使用の場合に
	 * 対象クラスを使用するために記述が必要
	 */
	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;

	//登録用
	public boolean insert(User user) {
		// insert実行
		int rowNumber = dao.insertOne(user);
		
		boolean result = false;
		if(rowNumber > 0) {
			result =  true;
		}
		return result;
	}
	
	//カウント用
	public int count() {
		return dao.count();
	}
	
	//全件取得用
	public List<User> selectMany() {
		return dao.selectMany();
	}
	
	//１件取得用
	public User selectOne(String userId) {
		return dao.selectOne(userId);
	}
	
	//１件更新用
	public boolean updateOne(User user) {
		
		int rowNumber =  dao.updateOne(user);
		
		if(rowNumber > 0) {
			return true;
		}
		return false;
	}
	
	//１件削除用
	public boolean deleteOne(String userId) {
		
		int rowNumber = dao.deleteOne(userId);
		if(rowNumber > 0) {
			return true;
		}
		return false;
	}
	
	//ユーザー一覧をCSV出力する
	public void userCsvOut() throws DataAccessException{
		dao.userCsvOut();
	}
	
	//サーバー保存ファイルを取得してbyte配列に変換
	public byte[] getFile(String fileName) throws IOException {
		//ファイルシステム(デフォルト)の取得
		FileSystem fs = FileSystems.getDefault();
		//ファイル取得
		Path p = fs.getPath(fileName);
		//バイト配列に変換
		byte[] bytes = Files.readAllBytes(p);
		
		return bytes;
	}

}
