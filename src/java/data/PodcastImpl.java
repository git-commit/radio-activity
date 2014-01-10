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

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale.Category;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class PodcastImpl implements Podcast {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8694762731204924262L;
	private String title;
	private String subtitle;
	private String author;
	private String copyright;
	private String summary;
	private URL feed;
	private URL link;
	private URL imageUrl;
	private Set<String> keywords;
	private Category category;
	private TreeSet<Episode> episodes;
	private ImageIcon image;

	public PodcastImpl(String title, String subtitle, String autor,
			String copyright, String summary, URL feed, URL link, URL imageUrl,
			Category category, Set<String> keywords)
			throws MalformedURLException {
		this.title = title;
		this.subtitle = subtitle;
		this.copyright = copyright;
		this.summary = summary;
		this.feed = feed;
		this.imageUrl = imageUrl; // get Image!
		this.category = category;
		this.author = autor;
		this.keywords = keywords;
		this.link = link;
		this.episodes = new TreeSet<Episode>();
		this.image = downloadImage(this.imageUrl);

	}

	private ImageIcon downloadImage(URL imageUrl2) {
		ImageIcon image = null;
		InputStream in = null;
		try {
			in = new BufferedInputStream(imageUrl2.openStream());
			Image i = ImageIO.read(in);
			i = i.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
			image = new ImageIcon(i);
		} catch (IOException e) {
			image = new ImageIcon("icon/err.png");
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return image;
	}

	@Override
	public URL getLink() {
		return link;
	}

	@Override
	public int compareTo(Podcast o) {
		return title.compareTo(o.getTitle());
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
	public String getAuthor() {
		return author;
	}

	@Override
	public String copyright() {
		return copyright;
	}

	@Override
	public String getSummary() {
		return summary;
	}

	@Override
	public URL getFeed() {
		return feed;
	}

	@Override
	public URL getWebImage() {
		return imageUrl;
	}

	@Override
	public Set<String> getKeywords() {
		return ImmutableSet.copyOf(keywords);
	}

	@Override
	public ImmutableList<Episode> getEpisodes() {
		return ImmutableList.copyOf(episodes);
		// return Collections.unmodifiableSet(episodes);
	}

	@Override
	public Episode getEpisode(String title) {
		for (Episode i : episodes)
			if (title.equalsIgnoreCase(i.getTitle()))
				return i;
		return null;
	}

	@Override
	public boolean equals(String name) {
		return title.equals(name);
	}

	@Override
	public int getNumberOfUnplayedEpisodes() {
		int i = 0;
		for (Episode epi : episodes)
			if (!epi.isPlayed())
				i++;
		return i;
	}

	@Override
	public int getNumberOfFavEpisodersNum() {
		int i = 0;
		for (Episode epi : episodes)
			if (epi.isFavorit())
				i++;
		return i;
	}

	@Override
	public ImmutableList<Episode> getUnplayedEpisodes() {
		Set<Episode> unplayed = new TreeSet<Episode>();
		for (Episode epi : episodes)
			if (!epi.isPlayed())
				unplayed.add(epi);
		return ImmutableList.copyOf(unplayed);
	}

	@Override
	public ImmutableList<Episode> getFavoriteEpisodes() {
		Set<Episode> favs = new TreeSet<Episode>();
		for (Episode epi : episodes)
			if (epi.isFavorit())
				favs.add(epi);
		return ImmutableList.copyOf(favs);
	}

	@Override
	public Podcast addEpisode(Episode p) {
		episodes.add(p);
		return this;
	}

	@Override
	public Category categorys() {
		return category;
	}

	@Override
	public String toString() {
		return title;
	}

	@Override
	public int hashCode() {
		return title.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return o.hashCode() == hashCode();
	}

	@Override
	public ImageIcon getImage() {
		return image;
	}

}
