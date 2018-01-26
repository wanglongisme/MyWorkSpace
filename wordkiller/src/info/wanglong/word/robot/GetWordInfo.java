package info.wanglong.word.robot;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import info.wanglong.word.bean.Word;
import info.wanglong.word.core.ServiceBus;

public class GetWordInfo extends ServiceBus {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetWordInfo me = new GetWordInfo();
		//获取单词集合
		List<Word> list = me.getWordList(1803, 100);
		//解析
		me.getWordInfo(list);
		for(Word w : list) {
			System.out.println(w.toString());
		}
		//入库
		me.updateWordList(list);
		System.out.println("执行完成");
		
	}
	
	
	/**
	 * 从数据库中获取单词集合
	 * @param startId 从表的某id开始查询
	 * @param count 一共获取多少行记录
	 * 使用id排序
	 * @return
	 */
	protected List<Word> getWordList(int startId, int count){
		String sql = "select * from word where id>"+startId+" order by id limit 0,"+count;
		RowMapper<Word> rowMapper = new BeanPropertyRowMapper<>(Word.class);
		List<Word> wordList = super.jdbcTemplate.query(sql, rowMapper);
		System.out.println("查询数据库: "+sql+"\r\n结果集大小: "+ wordList.size());
		return wordList;
	}
	
	/**
	 * 更新单词信息
	 * @return
	 */
	private List<Word> updateWordList(List<Word> wordList){
		String sql = "update word set cn=?, type=?, voiceLabel=?, star=?, uk_sound=?, en_sound=? where id=?";
		super.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Word w = wordList.get(i);
                ps.setString(1, w.getCn());
                ps.setString(2, w.getType());
                ps.setString(3, w.getVoiceLabel());
                ps.setInt(4, w.getStar());
                ps.setString(5, w.getUk_sound());
                ps.setString(6, w.getEn_sound());
                ps.setInt(7, w.getId());
                
			}
			
			@Override
			public int getBatchSize() {
				return wordList.size();
			}
		});
		
		return wordList;
	}
	
	
	
	/**
	 * 解析单词
	 * @param list
	 */
	public void getWordInfo(List<Word> list) {
		String url = "http://www.iciba.com/";
		for(Word word : list) {
			String en = word.getEn();
			String html = httpGet(url+en);
			getInfo(html, word);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从html抓取出单词数据
	 * @param html 
	 * @return
	 */
	public static Word getInfo(final String html, Word word) {
		System.out.println("分析 "+word.getEn());
		//音标
		String regex = "<span>[英|美][\\s\\S].*?</span>"; //音标
		final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		final Matcher ma = pa.matcher(html);
		StringBuilder vLabel = new StringBuilder(); 
		while (ma.find()) {
			vLabel.append(outTag(ma.group())+" ");
		}
		word.setVoiceLabel(vLabel.toString());
		//star
		String regex2 = "star(\\d)?"; //音标
		final Pattern pa2 = Pattern.compile(regex2, Pattern.CANON_EQ);
		final Matcher ma2 = pa2.matcher(html);
		if (ma2.find()) {
			String star = ma2.group();
			if(star.indexOf("star") > -1) {
				if(star.length()>4) {
					star = star.substring(4, 5);
				}else {
					star = "0";
				}
					
			}
			word.setStar(Integer.valueOf(star));
		}
		//声音
		String uk_sound = "http://dict.youdao.com/dictvoice?audio="+word.getEn()+"&type=1";
		word.setUk_sound(uk_sound);
		String en_sound = "http://dict.youdao.com/dictvoice?audio="+word.getEn()+"&type=2";
		word.setUk_sound(en_sound);
		//汉译 
		String regex3 = "<ul class=\"base-list switch_part\" [\\s\\S]*?</ul>";
		final Pattern pa3 = Pattern.compile(regex3, Pattern.CANON_EQ);
		final Matcher ma3 = pa3.matcher(html);
		if (ma3.find()) {
			String title = ma3.group();
			String re = "<li class=\"clearfix\">[\\s\\S]*?</li>";
			final Pattern pa33 = Pattern.compile(re, Pattern.CANON_EQ);
			final Matcher ma33 = pa33.matcher(title);
			if(ma33.find()) {
				//word.setCn(ma33.group(0));
				//第一个汉译
				String firstCn = ma33.group(0);
				String re4 = "<span[\\s]*[class=\"prop\"]*>[\\s\\S]*?</span>";
				final Pattern pa4 = Pattern.compile(re4, Pattern.CANON_EQ);
				final Matcher ma4 = pa4.matcher(firstCn);
				StringBuilder en = new StringBuilder();
				while(ma4.find()) {
					en.append(outTag(ma4.group()));
				}
				word.setCn(en.toString());
			}
		}
		//词性
		String wordCn = word.getCn();
		if(wordCn != null) {
			String wordType = "";
			wordType = wordCn.substring(0, wordCn.indexOf(".")+1);
			word.setType(wordType);
		}
		
		return word;
	}
	
	/**
	 * 发送http GET请求
	 * @param url
	 * @return
	 */
	public static String httpGet(String url){
        //get请求返回结果
        try {
        	HttpClient client = HttpClientBuilder.create().build();
            //DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
 
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                return strResult;
            } else {
                System.out.println("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            System.out.println("get请求提交失败:" + url);
        }
        return null;
    }
	
	/**
	 * 
	 * @param s
	 * @return 去掉标记
	 */
	public static String outTag(final String s) {
		return s.replaceAll("<.*?>", "");
	}
}
