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

import java.io.Serializable;
import java.net.URL;
import java.util.Date;
 

public interface Episode extends Comparable<Episode>, Serializable {
	
	public int getLength();
	public int getPlayedTime();
	public boolean isDownloaded();
	public URL getDownloadLink();
	public URL getLocalFile();
	public String getTitle();
	public String getSubtitle();
	public String getSummary();
	public Date pubDate();
	public Mediatype getMediatype();
	public boolean isExplicit();
	public boolean isPlayed();
	public boolean isFavorit();
	public boolean equals(String title);
	public Episode setLocalFile(URL file);
	public Episode setFavorit(boolean b);
	public Episode setPlayedTime(int play);
	public URL getWebsiteLink();
	
	
	enum Mediatype{MP3("mp3", false), MP4("mp4", false), AVI("avi", true), OGG("ogg" ,false);
		boolean isVideo;
		String file;
		Mediatype(String file,boolean isVideo){
			this.isVideo=isVideo;
			this.file = file;
		}
		public boolean isVideo(){ return isVideo;} ;
		public String getFile(){ return file; }
		}
}
