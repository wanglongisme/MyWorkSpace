package info.wanglong.word.robot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class DownloadRobot {
	static HttpClient httpClient = HttpClientBuilder.create().build();
	/**
	 * @param url 下载文件路径
	 * @param path 保存文件路径(包含文件名)
	 * code author: wanglong
	 * 2018-01-26 14:44:58
	 */
	public static void download(String url, String path) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
			HttpEntity entity = response.getEntity();
			System.out.println(entity.toString());
			if (entity != null) {
				//可以判断是否是文件数据流
				//System.out.println(entity.isStreaming());
				long size = entity.getContentLength();
				if(size > 40*1024) {
					System.out.println("文件超过40K  --> \n"+url);
				}
				File storeFile = new File(path);
				FileOutputStream output = new FileOutputStream(storeFile);
				//得到网络资源并写入文件
				InputStream input = entity.getContent();
				byte b[] = new byte[4096];
				int j = 0;
				while( (j = input.read(b))!=-1){
					output.write(b,0,j);
				}
				output.flush();
				output.close(); 
				input.close();
				System.out.println("download over.");
			}
			if (entity != null) {
				EntityUtils.consume(entity);
			}
		}
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
