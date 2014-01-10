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
import java.util.Locale.Category;
import java.util.Set;

import javax.swing.ImageIcon;


import com.google.common.collect.ImmutableList;

public interface Podcast extends Comparable<Podcast>, Serializable {
	public String getTitle();
	public String getSubtitle();
	public String getAuthor();
	public String copyright();
	public String getSummary();
	public URL getFeed();
	public URL getWebImage();
	public ImageIcon getImage();
	public Set<String> getKeywords();
	public Category categorys();
	public ImmutableList<Episode> getEpisodes();
	public Episode getEpisode(String title);
	public boolean equals(String name);
	public int getNumberOfUnplayedEpisodes();
	public int getNumberOfFavEpisodersNum();	
	public ImmutableList<Episode> getUnplayedEpisodes();
	public ImmutableList<Episode> getFavoriteEpisodes();
	public Podcast addEpisode(Episode p);
	public URL getLink();
	
}
