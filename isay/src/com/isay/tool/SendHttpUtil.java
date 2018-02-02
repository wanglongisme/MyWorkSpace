package com.isay.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class SendHttpUtil {
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static Map sendPost(String url, String param,String charset) {
        PrintWriter out = null;
        BufferedReader in = null;
        Map<String,String> result = new HashMap<String,String>();
        BufferedWriter bw = null;  
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            HttpURLConnection httpUrlCon = (HttpURLConnection)conn;  
            // 设置通用的请求属性
            httpUrlCon.setRequestProperty("accept", "*/*");
            httpUrlCon.setRequestProperty("connection", "Keep-Alive");
            httpUrlCon.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //httpUrlCon.setRequestProperty("Charset", charset);
            httpUrlCon.connect();  //建立tcp链接
            
            in = new BufferedReader(
                    new InputStreamReader(httpUrlCon.getInputStream(),charset));  //httpUrlCon.getInputStream()发送http请求
            String line;
            
            StringBuffer lineStr  = new StringBuffer();
            while ((line = in.readLine()) != null) {
            	lineStr.append(line);
            }
            result.put("html", lineStr.toString());
            String responseCode = httpUrlCon.getResponseCode()+"";
            String head = httpUrlCon.getHeaderFields().toString().substring(0, 128);
            result.put("code", responseCode);
            result.put("head", head);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }  
}
