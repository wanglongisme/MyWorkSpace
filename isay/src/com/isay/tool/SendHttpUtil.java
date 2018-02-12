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
     * ��ָ�� URL ����POST����������
     * 
     * @param url
     *            ��������� URL
     * @param param
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return ������Զ����Դ����Ӧ���
     */
    public static Map sendPost(String url, String param,String charset) {
        PrintWriter out = null;
        BufferedReader in = null;
        Map<String,String> result = new HashMap<String,String>();
        BufferedWriter bw = null;  
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            HttpURLConnection httpUrlCon = (HttpURLConnection)conn;  
            // ����ͨ�õ���������
            httpUrlCon.setRequestProperty("accept", "*/*");
            httpUrlCon.setRequestProperty("connection", "Keep-Alive");
            //httpUrlCon.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpUrlCon.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4549.400 QQBrowser/9.7.12900.400");
            
            //httpUrlCon.setRequestProperty("Charset", charset);
            httpUrlCon.connect();  //����tcp����
            
            in = new BufferedReader(new InputStreamReader(httpUrlCon.getInputStream(),charset));  //httpUrlCon.getInputStream()����http����
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
            System.out.println("���� POST ��������쳣��"+e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
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
