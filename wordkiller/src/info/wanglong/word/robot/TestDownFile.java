package info.wanglong.word.robot;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class TestDownFile {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		//设置需要下载的文件
		HttpGet httpGet = new HttpGet("http://imgs.inkfrog.com/pix/akibashipping/Sailor_11-0700-233-1.jpg");
		HttpResponse response = httpClient.execute(httpGet);
		if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
			HttpEntity entity = response.getEntity();
			System.out.println(entity.toString());
			if (entity != null) {
				//这里可以得到文件的类型 如image/jpg /zip /tiff 等等 但是发现并不是十分有效，有时明明后缀是.rar但是取到的是null，这点特别说明
				//System.out.println(entity.getContentType());
				//可以判断是否是文件数据流
				//System.out.println(entity.isStreaming());
				String size = String.valueOf(entity.getContentLength());
				File storeFile = new File("F:\\sound_file\\aa.jpg");
				FileOutputStream output = new FileOutputStream(storeFile);
				//得到网络资源并写入文件
				InputStream input = entity.getContent();
				byte b[] = new byte[1024];
				int j = 0;
				while( (j = input.read(b))!=-1){
					output.write(b,0,j);
				}
				output.flush();
				output.close(); 
				input.close();
				System.out.println("done.");
			}
			if (entity != null) {
				EntityUtils.consume(entity);
			}
		}
	}
}

