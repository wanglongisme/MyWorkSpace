package info.wanglong.word.robot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import info.wanglong.word.bean.Word;
import info.wanglong.word.core.ServiceBus;

public class GetWordInfo extends ServiceBus {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetWordInfo me = new GetWordInfo();
		List<String> list = me.getWordList();
		me.getWordInfo(list);
	}
	
	public void getWordInfo(List<String> list) {
		String url = "http://www.iciba.com/";
		for(String en : list) {
			String html = httpGet(url+en);
			Word word = new Word();
			word.setEn(en);
			Word w = getInfo(html, word);
		}
	}
	
	private List<String> getWordList(){
		String sql = "select en from word order by id limit 0,50";
		RowMapper<String> rowMapper = new BeanPropertyRowMapper<>(String.class);
		List<String> diaryList = super.jdbcTemplate.query(sql, rowMapper);
		return diaryList;
	}
	
	/**
	 * 发送http GET请求
	 * @param url
	 * @return
	 */
	public static String httpGet(String url){
        //get请求返回结果
        try {
            DefaultHttpClient client = new DefaultHttpClient();
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
	 * 从html抓取出数据
	 * @param html 
	 * @return
	 */
	public static Word getInfo(final String html, Word word) {
		//音标
		String regex = "<span>美 [\\s\\S].*?</span>"; //音标
		final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		final Matcher ma = pa.matcher(html);
		if (ma.find()) {
			word.setVoiceLabel(ma.group());
		}
		//汉译
		String regex2 = "<span>美 [\\s\\S].*?</span>"; //音标
		final Pattern pa2 = Pattern.compile(regex2, Pattern.CANON_EQ);
		final Matcher ma2 = pa2.matcher(html);
		if (ma2.find()) {
			word.setVoiceLabel(ma2.group());
		}
		
		
		return outTag(title);
	}
}
