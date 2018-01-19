package info.wanglong.word.robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRobot {

	public static void main(String[] args) throws IOException {
		// test1();
		test2();
	}

	public static void test2() {
		String url = "http://www.iciba.com/doctor";
		String html = GetWordInfo.httpGet(url);
		String title = getTitle(html);

		System.out.println(title);
	}

	public void test1() throws IOException {
		// TODO Auto-generated method stub
		String path = "F:\\eclipseWorkSpace\\word01.txt";
		Set<String> set = getWord(path);
		for (String s : set) {
			System.out.println(s);
		}
	}

	/**
	 * 
	 * @param s
	 * @return 获得网页标题
	 */
	public static String getTitle(final String s) {
		System.out.println(s);
		String title = "";
		//String regex = "<h1 class=\"keyword\">[\\s\\S].*?</h1>"; //单词
		String regex = "<li class=\"clearfix\">[\\s\\S].*?</li>"; //音标
		// regex = "<span class=\"prop\">[\\s\\S].*?</span>";
		
		final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		final Matcher ma = pa.matcher(s);
		if (ma.find()) {
			title = ma.group();
		}

		return outTag(title);
	}

	/**
	 * 
	 * @param s
	 * @return 去掉标记
	 */
	public static String outTag(final String s) {
		return s.replaceAll("<.*?>", "");
	}

	/**
	 * 从文件中获取单词
	 * 
	 * @throws IOException
	 */
	public static Set<String> getWord(String path) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			BufferedReader br = null;
			Set<String> wordSet = null;
			try {
				br = new BufferedReader(new FileReader(file));
				wordSet = new HashSet<String>();
				while (br.readLine() != null) {
					String line = br.readLine();
					if (line != null) {
						String[] a = line.split("[^a-zA-Z]+");
						wordSet.addAll(Arrays.asList(a));
					}
				}
				System.out.println(wordSet.size());
				return wordSet;
			} catch (FileNotFoundException e) {
				// log.error("File is not found.", e);
				System.out.println("File is not found." + e);
			} catch (IOException e) {
				// log.error("readLine() exception.", e);
				System.out.println("readLine() exception." + e);
			} finally {
				br.close();
			}
		}
		return null;
	}
}
