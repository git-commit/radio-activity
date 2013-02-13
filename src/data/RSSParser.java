/*******************************************************************************
 * Copyright (c) 2013 Yannic Remmet, Maximilian Berger.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yannic Remmet - initial API and implementation
 *     Maximilian Berger - initial API and implementation
 ******************************************************************************/
package data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;
import java.util.Locale.Category;


import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;



public class RSSParser extends Thread {
	/**
	 * TAGS!
	 */
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String LINK = "link";
	static final String OWNER = "owner";
	static final String OWNER1 = "author";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String PUB_DATE2 = "lastBuildDate";
	static final String IMAGE = "image";
	static final String URL = "url";
	static final String FILE = "origEnclosureLink"; //feedburner:origEnclosureLink
	static final String FILE2= "enclosure";
	
	private String title;
	private String subtitle;
	private String author;
	private String copyright;
	private String summary;
	private URL feed;
	private URL link;
	private String image;
	private TreeSet<String> keywords;
	private Category category;
	private int length;
	private URL downloadLink;
	private Date pubDate;
	private boolean explicit; 
	
	private boolean isNew;
	private Podcast pod;
	//private Date lastPubDate;
	private boolean ready;
	private PodcastLibrary library;
	
	/**
	 * Priavte Construtor only used to initialize Variables.
	 * @param lib podcastlibraby
	 */
	private RSSParser(PodcastLibrary lib){
		library		= lib;
		ready 		= false;
		title		= "";
		subtitle	= "";
		author		= "";
		copyright	= "";
		summary		= "";
		image 		= "";
		downloadLink= null;
		keywords 	= new TreeSet<String>();
		category 	= null;
		length 		= 0;
		pubDate		= null;
		explicit	= false;
		pod 		= null;
		//lastPubDate	= new Date(0);
		link = null;
	}

	/**
	 * Constructor used to Update an Podcast Object. 
	 * The Constructor starts the Parser automatically.
	 * @param p 	Podcast Object to Update
	 * @param lib 	PodcastLibrary
	 */
	public RSSParser(Podcast p , PodcastLibrary lib)  {
		this(lib);
		this.feed = p.getFeed();
		this.isNew = false;
		this.pod = p;
		this.start();
	}
	

	/**
	 * Constructor used to add an Podcast to the Library
	 * The Constructor starts the Parser automatically.
	 * @param u		Podcast RSS Feed URL to add To library
	 * @param lib 	PodcastLibrary
	 */
	public RSSParser(URL u , PodcastLibrary lib) {
		this(lib);
		this.feed = u;
		this.isNew = true;
		this.start();
	}
	
	/**
	 * run Method of the Thread.
	 * Parses the RSS Feed.
	 */
	public void run() {
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		try {
			XMLEventReader eventReader = inputFactory.createXMLEventReader(feed
					.openStream());
			String now = "";
			boolean header = true;
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				

					switch (event.getEventType()) {
					case XMLStreamConstants.START_ELEMENT:
						now = event.asStartElement().getName().getLocalPart();
						switch(now){
						case ITEM:
							if(header){
								if(isNew){
									pod = new PodcastImpl(title, subtitle, author, copyright, summary, feed, link, new URL(image), category, keywords);
									isNew=false;
									if(library != null)
										library.addPodcast(pod);
								}
							header = false;
							}else{
									pod.addEpisode(new EpidsodeImpl(
											title, subtitle, summary, pubDate, downloadLink, link,  length, explicit));
									
							}
							break;
						case FILE2:
							String s=event.asStartElement().getAttributeByName(new QName("url")).toString();
							String le = event.asStartElement().getAttributeByName(new QName("length")).toString();
							length = Integer.parseInt( le.substring(8, le.length()-1));
							downloadLink =new URL( s.substring(5, s.length()-1));
							break;
						case IMAGE:
							Attribute f=event.asStartElement().getAttributeByName(new QName("href"));
							if(f != null){
								String t = f.toString();
								image = t.substring(6, t.length()-1);
							}
						}
						
						break;
					case XMLStreamConstants.CHARACTERS:
						if (!event.asCharacters().isWhiteSpace())
							switch (now) {
							case TITLE:
								title = event.asCharacters().getData();
								break;
							case LINK:
								link = new URL( event.asCharacters().getData());
								break;
							case DESCRIPTION:
								summary = event.asCharacters().getData();
								break;
							case OWNER:
							case OWNER1:
								author = event.asCharacters().getData();
								break;
							case PUB_DATE:
							case PUB_DATE2:
								pubDate = stringToDate( event.asCharacters().getData());
								break;
							case URL:
								image =event.asCharacters().getData();
							case FILE:
								downloadLink = new URL( event.asCharacters().getData());
								break;
							}
						break;
					}
			}
			pod.addEpisode(new EpidsodeImpl(
					title, subtitle, summary, pubDate, downloadLink, link,  length, explicit));
			ready = true;
			if(library !=  null)
				library.notifyObservers();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Method to Parse the Time-String to the java.util.Date Object
	 * @param pubdate Datesting
	 * @return	Date Object
	 */
	private Date stringToDate(String pubdate){
		SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.US);
		try {
			return df.parse(pubdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return new Date();
	}
	/**
	 * @return null if word is in progress or Podcast-Object if work is done
	 */
	public Podcast getNewPodcast(){
		if(ready)
			return pod;
		else
			return null;
	}
	public static void main(String[] args) {
		try {
			new RSSParser(new URL("http://fanboys.fm/episodes.all.mp3.rss"), null);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	};
}
