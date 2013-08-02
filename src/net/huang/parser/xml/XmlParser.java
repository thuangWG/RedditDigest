package net.huang.parser.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;

public class XmlParser {
	
	static final String TITLE = "title";
	static final String LINK = "link";
	static final String PUBDATE = "pubDate";
	static final String ITEM = "item";

	public List<XmlItem> readRedditRss(URL xmlUrl)
	{
		List<XmlItem> items = new ArrayList<XmlItem>();
		InputStream in;
		XMLEvent event;
		XmlItem item = null;
		
		try {
			in = xmlUrl.openStream();
			XMLInputFactory factory = XMLInputFactory.newInstance();
			factory.setProperty("javax.xml.stream.isCoalescing", true);
			XMLEventReader eventReader = factory.createXMLEventReader(in);
			
			
			while (eventReader.hasNext())
			{
				//System.out.println("start....");
				event = eventReader.nextEvent();
				
				//System.out.println(event);
				
				if (event.isStartElement()) 
				{
					/* create new element only if starting element has <item> tag */
					if (event.asStartElement().getName().getLocalPart() == (ITEM)) {
						//System.out.println("item start tag detected");
						item = new XmlItem();
						continue;
					}
					/* start capturing relevant data only after item start tag has been detected */
					if (item != null)
					{
						if (event.asStartElement().getName().getLocalPart() == (TITLE))
						{
							//System.out.println("title start tag detected");
							event = eventReader.nextEvent();
							//System.out.println(event.asCharacters());
							item.setTitle(event.asCharacters().getData());
							continue;
						}

						if (event.asStartElement().getName().getLocalPart() == (LINK))
						{
							//System.out.println("link start tag detected");
							event = eventReader.nextEvent();
							//System.out.println(event);
							item.setLink(event.asCharacters().getData());
							continue;
						}

						if (event.asStartElement().getName().getLocalPart() == (PUBDATE))
						{
							//System.out.println("pubDate start tag detected");
							event = eventReader.nextEvent();
							//System.out.println(event);
							item.setpubDate(event.asCharacters().getData());
							continue;
						}	
					}
				}
				if (event.isEndElement())
				{
					EndElement endElement = event.asEndElement();
					/* add item to the list only if detecting </item> tag */
					if (endElement.getName().getLocalPart() == (ITEM))
					{
						items.add(item);
						item = null; 
					}
				}
				//System.out.println("End....");
			}
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		catch(XMLStreamException e1)
		{
			e1.printStackTrace();
		}
		return items;
	}
}
