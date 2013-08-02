package net.huang.parser.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ParseReddit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<URL> urls = new ArrayList<URL>();
		Properties prop = new Properties();
		
		String[] splitSub;
		/* get a unique file name using today's date */
		Date date = new Date();
		String dateStr = new SimpleDateFormat("_yyyy_MM_dd").format(date);
		//File dailyFile = new File("/Users/thuang/getReddit/README/reddit_content" + dateStr + ".txt");
		//File dailyFile_titleonly = new File("/Users/thuang/getReddit/README/reddit_title" + dateStr + ".txt");
		//File test = new File("/Users/thuang/getReddit/README/test" + dateStr + ".txt");
		File dailyFile_html = new File("/Users/thuang/getReddit/README/reddit" + dateStr + ".html");
		
		
		try {
			prop.load(new FileInputStream("/Users/thuang/getReddit/resources/config.properties"));
			splitSub = prop.getProperty("subReddits").split(",");
			for (String temp : splitSub)
			{
				urls.add(new URL(prop.getProperty("baseUrl") + temp + prop.getProperty("urlSuffix")));
			}
			/*
			if (!dailyFile.exists())
			{
				dailyFile.createNewFile();
			}
			if (!dailyFile_titleonly.exists())
			{
				dailyFile_titleonly.createNewFile();
			}
			if (!test.exists())
			{
				test.createNewFile();
			}
			*/
			if (!dailyFile_html.exists())
			{
				dailyFile_html.createNewFile();
			}
			System.out.println(date.toString());
			WriteToFiles wtf = new WriteToFiles(urls, dailyFile_html, splitSub, "html");
			wtf.writeToFiles();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
