package info.wanglong.word.robot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import info.wanglong.word.bean.Sound;
import info.wanglong.word.bean.Time;
import info.wanglong.word.bean.Word;
import info.wanglong.word.core.ServiceBus;

public class ImgFileGetter extends ServiceBus{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis(); //开始时间
		ImgFileGetter getter = new ImgFileGetter();
		//查库
		GetWordInfo me = new GetWordInfo();
		//获取单词集合
		List<Word> list = me.getWordList(1416, 50);
		getter.downLoadList(list);
		long endTime = System.currentTimeMillis(); //结束时间
		long doTime = (endTime - startTime)/1000; //秒
		if(doTime%60 >0) {
			double mins = doTime / 60;
			double min = Math.floor(mins);
			System.out.println("执行结束. 用时: " + min+"分" + (doTime%60) + " s");
			return;
		}
		System.out.println("执行结束. 用时: " + doTime + " s");
		
	}
	
	
	public List<Sound> downLoadList(List<Word> list) throws Exception {
		if(list == null) {
			System.out.println("数据集为空.");
			return null;
		}
		String insertSql = "insert into image(wordId, word, fileName, url, size, star) values(?,?,?,?,?,?)";
		for(Word word : list) {
			String w = word.getEn();
			System.out.println(w);
			int id = word.getId();
			int star = word.getStar();
			List<String> imgList = JsoupBaidu.getPictures(w, 0, "");
			for(int i=0; i<imgList.size(); i++) {
				RequestConfig defaultRequestConfig = RequestConfig.custom()
						  .setSocketTimeout(10000)
						  .setConnectTimeout(10000)
						  .setConnectionRequestTimeout(10000)
						  .setStaleConnectionCheckEnabled(true)
						  .build();
				CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(defaultRequestConfig).build();
				//CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
				String url = imgList.get(i);
				HttpGet httpGet = new HttpGet(url);
				CloseableHttpResponse response = null;
				try {
					response = httpClient.execute(httpGet);
				} catch (Exception e) {
					httpGet.releaseConnection();
					httpClient.close();
					System.out.println(w+"_"+i+" img is exception ->"+e);
					continue;
				}
				if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
					String fileName = w+"_"+i+".jpg";
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						//可以判断是否是文件数据流
						if(!entity.isStreaming() || entity.getContentLength()<500) {
							System.out.println(fileName+" 不是文件...");
							continue;
						}
						long size = entity.getContentLength();
						//过滤大图片
						if(size > 131072) {
							System.out.println(fileName+" 超过128K...");
							continue;
						}
						//根据单词star分文件夹
						String fileFullName = "F:\\sound_img\\" + star + "\\" + fileName;
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
						System.out.println(fileName + " is download - "+ Time.now());
						//入库
						try {
							this.jdbcTemplate.update(insertSql, new Object[] {id, w, fileName, url, size+"", star});	
						}catch(Exception e) {
							System.out.println("update exception.");
							response.close();
							httpGet.releaseConnection();
							httpClient.close();
							continue;
						}
						
						//System.out.println("insert done...");
					}
				}
				response.close();
				httpGet.releaseConnection();
				httpClient.close();
			}
		}
		System.out.println("is over.");
		return null;
	}
	
}
