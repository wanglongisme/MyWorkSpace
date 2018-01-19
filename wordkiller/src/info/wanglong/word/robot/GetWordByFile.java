package info.wanglong.word.robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import info.wanglong.word.core.ServiceBus;

public class GetWordByFile extends ServiceBus{
	public static void main(String[] args) throws Exception {
		GetWordByFile service = new GetWordByFile();
		String path = "F:\\eclipseWorkSpace\\word01.txt";
		Set<String> set = service.getWord(path);
		service.insertWrod(set);
    }
	
	/**
	 * 从文件中获取单词
	 * @throws IOException 
	 */
	public Set<String> getWord(String path) throws IOException{
		File file = new File(path);
		if(file.exists()) {
			BufferedReader br = null;
			Set<String> wordSet = null;
			try {
				br = new BufferedReader(new FileReader(file));
				wordSet = new HashSet<String>(); 
				while (br.readLine() != null) {
					String line = br.readLine();
					if(line ==null || line.trim().length()<3) {
						break;
					}
					line = line.toLowerCase();
					if(line != null) {
						String[] a = line.split("[^a-z]+");
						wordSet.addAll(Arrays.asList(a));
					}
				}
				System.out.println(path+" -- 提取单词数: "+wordSet.size());
				return wordSet;
			} catch (FileNotFoundException e) {
				//log.error("File is not found.", e);
				System.out.println("File is not found." + e);
			} catch (IOException e) {
				//log.error("readLine() exception.", e);
				System.out.println("readLine() exception." + e);
			}finally {
				br.close();
			}
		}
		return null;
	}
	
	public void insertWrod(Set<String> wordSet){
		List<String> list = new ArrayList<String>(wordSet);
		String sql = "insert into word(en, createTime) values(?, now())";
		super.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public int getBatchSize() {
				return list.size();
			}
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				String word = list.get(i);
				if(word==null || word.trim().length()<3) {
					ps.setString(1, "girl");
					return;
				}
				ps.setString(1, word.toLowerCase());
			}
		});
		System.out.println("insert batch data is over.");
	}

}
