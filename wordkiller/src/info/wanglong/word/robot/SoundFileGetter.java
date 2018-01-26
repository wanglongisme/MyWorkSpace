package info.wanglong.word.robot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import info.wanglong.word.bean.Sound;
import info.wanglong.word.bean.Word;
import info.wanglong.word.core.ServiceBus;

public class SoundFileGetter extends ServiceBus{

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		SoundFileGetter getter = new SoundFileGetter();
		//查库
		GetWordInfo me = new GetWordInfo();
		//获取单词集合
		List<Word> list = me.getWordList(1770, 100);
		//下载文件
		getter.downLoadList(list);
		System.out.println("Everything is done.");
	}
	
	/**
	 * 下载MP3文件,并入库
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<Sound> downLoadList(List<Word> list) throws Exception{
		if(list == null) {
			return null;
		}
		String insertSql = "insert into sound_en(id, fileName, size, lan, spd) values(?,?,?,?,?)";
		for(Word word : list) {
			String w = word.getEn();
			System.out.println(w);
			int id = word.getId();
			int star = word.getStar();
			//因为fanyi.baidu.com提供一个单词8种音频文件,不同语速或不同压缩比.
			for(int i=1; i<8; i++) {
				CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				String uk = "http://fanyi.baidu.com/gettts?lan=en&text="+w+"&spd="+i+"&source=web";
				System.out.println(uk);
				String ukFileName = w+"_en_"+i+".mp3";
				HttpGet httpGet = new HttpGet(uk);
				//Thread.sleep(200); //sleep
				CloseableHttpResponse response = null;
				try {
					response = httpClient.execute(httpGet);
				} catch (Exception e) {
					httpGet.releaseConnection();
					httpClient.close();
					System.out.println(ukFileName+" is exception ->"+e);
					continue;
					//e.printStackTrace();
				}
				//System.out.println("code="+response.getStatusLine().getStatusCode()+" ->"+uk);
				if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						//可以判断是否是文件数据流
						if(!entity.isStreaming() || entity.getContentLength()<500) {
							continue;
						}
						String size = String.valueOf(entity.getContentLength());
						//根据单词star分文件夹
						String fileFullName = "F:\\sound_file\\en\\" + star + "\\" + ukFileName;
						File storeFile = new File(fileFullName);
						FileOutputStream output = new FileOutputStream(storeFile);
						//得到网络资源并写入文件
						InputStream input = entity.getContent();
						byte b[] = new byte[1024];
						int j = 0;
						while( (j = input.read(b))!=-1){
							output.write(b,0,j);
						}
						output.flush();
						EntityUtils.consume(entity);
						if(output != null) {
							output.close();	
						}
						if(input != null) {
							input.close();	
						}
						System.out.println(ukFileName + " is download.");
						//入库
						this.jdbcTemplate.update(insertSql, new Object[] {id, ukFileName, size, "en", i});
						System.out.println("insert done.");
					}
				}
				response.close();
				httpGet.releaseConnection();
				httpClient.close();
			}
			System.out.println("ooooo");
		}
		System.out.println("is over.");
		return null;
	}
	

}
