package net.huang.parser.xml;

public class XmlItem {
	
	private String title;
	private String link;
	private String pubDate;
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public void setLink(String link)
	{
		this.link = link;
	}
	
	public String getpubDate()
	{
		return pubDate;
	}
	
	public void setpubDate(String pubDate)
	{
		this.pubDate = pubDate;
	}
	
	@Override
	public String toString()
	{
		return  "Item [title=" + title + ", link=" + link + ", pubDate=" + pubDate + "]";
	}

}
