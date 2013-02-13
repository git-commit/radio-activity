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

import java.net.URL;
import java.util.Date;

public class EpidsodeImpl implements Episode{

	private static final long serialVersionUID = 3189186153918899150L;
	private int length;
	private int playedTime;
	private URL downloadLink;
	private URL localFile;
	private String title;
	private String subtitle;
	private String summary;
	private Date pubDate;
	private boolean explicit;
	private boolean favorit;
	private Mediatype media; 
	private URL link;
	
	public EpidsodeImpl( String title, String subtitle, String summary, Date puDate, URL downloadLink, URL link, int length, boolean expl ) {
		this.title 		= title;
		this.subtitle 	= subtitle;
		this.summary 	= splitSum(summary);
		this.pubDate	= puDate;
		this.downloadLink=downloadLink;
		this.length		= length;
		this.explicit 	= expl;
		this.localFile	= null;
		this.favorit	= false;
		this.playedTime = 0;
		this.media		= getMediaType(this.downloadLink);
		this.link 		= link;
	}

	private String splitSum(String summary2) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>");
		int oldi=0;
		for(int i =50 ;i < summary2.length(); i++){
			if(i%50 == 0){
			sb.append(summary2.substring(oldi, i));
			sb.append("\n");
			oldi=i;
			}
		}
		sb.append(summary2.substring(oldi, summary2.length()));
		sb.append("<\\body><\\html>");
		return sb.toString();
	}

	@Override
	public int compareTo(Episode o) {
		return pubDate.compareTo(o.pubDate());
	}

	@Override
	public int getLength() {
		return this.length;
	}

	@Override
	public int getPlayedTime() {
		return this.playedTime;
	}

	@Override
	public URL getDownloadLink() {
		return downloadLink;
	}

	@Override
	public URL getLocalFile() {
		return localFile;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getSubtitle() {
		return subtitle;
	}

	@Override
	public String getSummary() {
		return summary;
	}

	@Override
	public Date pubDate() {
		return pubDate;
	}

	@Override
	public Mediatype getMediatype() {
		return media;
	}

	@Override
	public boolean isExplicit() {
		return explicit;
	}

	@Override
	public boolean isPlayed() {
		
		return playedTime > 0; 
	}

	@Override
	public boolean isFavorit() {
		return favorit;
	}

	@Override
	public boolean equals(String title) {
		return this.title.equals(title);
	}

	@Override
	public Episode setLocalFile(URL file) {
		localFile = file;
		return this;
	}

	@Override
	public Episode setPlayedTime(int play) {
		playedTime = play;
		return this;
	}

	@Override
	public Episode setFavorit(boolean b) {
		favorit = b;
		return this;
	}
	
	private Mediatype getMediaType(URL l){
		//TODO Insert method
		return Mediatype.MP3;
	}

	@Override
	public URL getWebsiteLink() {
		return link;
	}

	@Override
	public boolean isDownloaded() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int hashCode() {
		return title.hashCode();//% 7349 * summary.hashCode();  
	}
	public String toString(){
		return title;
	}
		
}



