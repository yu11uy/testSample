package com.example.demo.login.domain.repository.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

/*
 * RowCallbackHandlerの実装
 * 時間のかかるような処理で処理が終わったら結果を受け取る処理用
 */

public class UserRowCallbackHandler implements RowCallbackHandler{

	@Override
	public void processRow(ResultSet rs) throws SQLException{
		
		try {
			File file = new File("sample.csv");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			//取得件数分Loop
			do {
				String str = rs.getString("user_id") + ","
						+ rs.getString("password") + ","
						+ rs.getString("user_name") + ","
						+ rs.getDate("birthday") + ","
						+ rs.getInt("Age") + ","
						+ rs.getBoolean("marriage") + ","
						+ rs.getString("role");
				// ファイルに書き込み＆改行
				bw.write(str);
				bw.newLine();
				
			} while(rs.next());
			
			//強制的に書き込み＆クローズ
			bw.flush();
			bw.close();
			
		}catch(IOException e){
			e.printStackTrace();
			throw new SQLException(e);
		}
	}
	
	private static void writeTextsToFile(File file, List<String> texts) throws IOException {
		Files.write(file.toPath(), texts, StandardCharsets.UTF_8);
	}

}
