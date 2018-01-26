
package info.wanglong.word.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

// 爬取百度图片
public class JsoupBaidu {
	
	public static void main(String[] args) throws Exception{
		String downloadPath = "F:\\sound_img";
		List<String> list = nameList("pen");
		getPictures("red",1,downloadPath); //1 down one page
	}
	
    public static List<String> getPictures(String keyword, int max,String downloadPath) throws Exception{
    	List<String> imgUrlList = new ArrayList<String>();
        String gsm=Integer.toHexString(max)+"";
        String finalURL = "";
        String tempPath = "";
       //for(String keyword : keywordList){
//    	   tempPath = downloadPath;
//           if(!tempPath.endsWith("\\")){
//           	tempPath = downloadPath+"\\";
//           }
//           tempPath = tempPath+keyword+"\\";
//    	   File f = new File(tempPath);
//    	   if(!f.exists()){
//    		   f.mkdirs();
//    	   }
    	   int picCount = 1;
	    	   sop("正在下载第"+1+"页面");
	            Document document = null;
	            try {
	            	String url ="http://image.baidu.com/search/avatarjson?tn=resultjsonavatarnew&ie=utf-8&word="+keyword+"&cg=star&pn="+1*10+"&rn=30&itg=0&z=0&fr=&width=&height=&lm=-1&ic=0&s=0&st=-1&gsm="+Integer.toHexString(1*10);
	            	sop(url);
	            	document = Jsoup.connect(url).data("query", "Java")//请求参数  
							 .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")//设置urer-agent  get();
							 .timeout(5000)
							 .get();
	                String xmlSource = document.toString();
	                xmlSource = StringEscapeUtils.unescapeHtml3(xmlSource);
	                //sop(xmlSource);
	                String reg = "objURL\":\"http://.+?\\.jpg";
	        		Pattern pattern = Pattern.compile(reg, Pattern.CANON_EQ);
	                Matcher m = pattern.matcher(xmlSource);
	                //图片数量
	                for(int i=0; i< 16; i++) {
	                	if(m.find()) {
		                	finalURL = m.group().substring(9);
		                	if(finalURL.indexOf("fromURL")>-1) {
		                		continue;
		                	}
		                	//sop(keyword+picCount+++":"+finalURL);
		                	imgUrlList.add(finalURL);
		                	//download(finalURL,tempPath);
		                	//sop("download success.");
	                	}
	                }
	                return imgUrlList;
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
       //}
       sop("下载完毕");
       delMultyFile(downloadPath);
       sop("已经删除所有空图");
       return null;
    }
    public static void delMultyFile(String path){
		File file = new File(path);
		if(!file.exists())
			throw new RuntimeException("File \""+path+"\" NotFound when excute the method of delMultyFile()....");
		File[] fileList = file.listFiles();
		File tempFile=null;
		for(File f : fileList){
			if(f.isDirectory()){
				delMultyFile(f.getAbsolutePath());
			}else{
				if(f.length()==0)
					sop(f.delete()+"---"+f.getName());
			}
		}
	}
    public static List<String> nameList(String nameList){
    	List<String> arr = new ArrayList<>();
    	String[] list;
    	if(nameList.contains(","))
    		list= nameList.split(",");
    	else if(nameList.contains("、"))
    		list= nameList.split("、");
    	else if(nameList.contains(" "))
    		list= nameList.split(" ");
    	else{
    		arr.add(nameList);
    		return arr;
    	}
    	for(String s : list){
    		arr.add(s);
    	}
    	return arr;
    }
    public static void sop(Object obj){
    	System.out.println(obj);
    }
  //根据图片网络地址下载图片
  	public static void download(String url,String path){
  		//path = path.substring(0,path.length()-2);
  		File file= null;
  		File dirFile=null;
  		FileOutputStream fos=null;
  		HttpURLConnection httpCon = null;
  		URLConnection  con = null;
  		URL urlObj=null;
  		InputStream in =null;
  		byte[] size = new byte[2048];
  		int num=0;
  		try {
  			String downloadName= url.substring(url.lastIndexOf("/")+1);
  			dirFile = new File(path);
  			if(!dirFile.exists() && path.length()>0){
  				if(dirFile.mkdir()){
  					sop("creat document file \""+path.substring(0,path.length()-1)+"\" success...\n");
  				}
  			}else{
  				file = new File(path+downloadName);
  				fos = new FileOutputStream(file);
  				if(url.startsWith("http")){
  					urlObj = new URL(url);
  					con = urlObj.openConnection();
  					httpCon =(HttpURLConnection) con;
  					in = httpCon.getInputStream();
  					while((num=in.read(size)) != -1){
  						for(int i=0;i<num;i++)
  						   fos.write(size[i]);
  					}
  				}
  			}
  		}catch (FileNotFoundException notFoundE) {
  			sop("找不到该网络图片....");
  			file.delete();
  		}catch(NullPointerException nullPointerE){
  			sop("找不到该网络图片....");
  			file.delete();
  		}catch(IOException ioE){
  			sop("产生IO异常.....");
  			file.delete();
  		}catch (Exception e) {
  			e.printStackTrace();
  			file.delete();
  		}finally{
  			try {
  				if(fos != null) {
  					fos.close();
  				}
  			} catch (Exception e) {
  				e.printStackTrace();
  			}
  		}
  	}
}