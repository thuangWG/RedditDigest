package net.huang.parser.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

public class WriteToFiles {
	private List<URL> urls;
	private File file;
	private String[] subReddits;
	private String type;
	private FileWriter fw;
	private BufferedWriter bw;
	XmlParser parser = new XmlParser();
	
	
	public WriteToFiles(List<URL> urls, File file, String[] subReddits, String type)
	{
		this.urls = urls;
		this.file = file;
		this.subReddits = subReddits;
		this.type = type;
	}
	
	public boolean writeToFiles()
	{
		boolean success=false;
		int count=0;
		try 
		{
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		
		switch (type)
		{
			case "title-txt":
			{				
				for (URL url : urls)
				{
					List<XmlItem> content = parser.readRedditRss(url);
					System.out.println("getting " + subReddits[count] + " content...");
					try {
						bw.write("----------" + subReddits[count] + "----------\n");
						for (XmlItem item : content)
						{
							bw.write(item.getTitle() + "\n");
						}
						count++;
						bw.write("------------------------------");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				break;
			}
			case "tri-txt":
			{
				for (URL url : urls)
				{
					List<XmlItem> content = parser.readRedditRss(url);
					System.out.println("getting " + subReddits[count] + " content...");
					try {
						bw.write("----------" + subReddits[count] + "----------\n");
						for (XmlItem item : content)
						{
							bw.write(item.toString() + "\n");
						}
						count++;
						bw.write("------------------------------");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
			}
			case "html":
			{
				try {
					bw.write("<!DOCTYPE HTML>\n<html>\n<body>\n");
					for (URL url : urls)
					{
						List<XmlItem> content = parser.readRedditRss(url);
						System.out.println("getting " + subReddits[count] + " content...");
						
							bw.write("<p>\n");
							bw.write("<h3>----------" + subReddits[count] + "----------</h3>\n");
							bw.write("<ul>\n");
							for (XmlItem item : content)
							{
								bw.write("<li><a href=\"" + item.getLink() + "\">" + StringEscapeUtils.escapeHtml4(item.getTitle()) + "</a></li>\n");
							}
							
							count++;
							bw.write("</ul>\n");
							bw.write("<h3>------------------------------------------------------</h3>\n");
							bw.write("</p>\n");
					}
					bw.write("</body>\n");
					bw.write("</html>\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			default:
			{
				System.out.println("Invalid type");
			}
			
		}
		try {
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
		
		return success;
	}

}
