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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.HashSet;
import java.util.Observable;

import com.google.common.collect.ImmutableList;
/**
 * 
 * @author yannic
 *	
 */
public class PodcastLibrary extends Observable implements Serializable {

	private static PodcastLibrary lib = null;
	private static final long serialVersionUID = -7883861152152068899L;
	private HashSet<Podcast> podcasts;
	/**
	 * Private Default Constructor 
	 */
	private PodcastLibrary(){
		podcasts = new HashSet<Podcast>();
	}
	
	/**
	 * 	@return the library instance
	 */
	public static PodcastLibrary getLibrary(){
		if(lib == null)
			lib = new PodcastLibrary();
		return lib;	
	}
	/**
	 * @return ImmutableList of all Podcasts
	 */
	public ImmutableList<Podcast> getPodcasts(){
		return ImmutableList.copyOf(podcasts);
	}

	/**
	 * 
	 * @param u URL of Podcastfeed
	 * @return this
	 */
	public PodcastLibrary putPodcast(URL u){ /// Haesslich ich weiss wird noch gefixt
		new RSSParser(u, this);
		return this;
	}
	/**
	 * 
	 * @param p podcast 
	 * @return this
	 */
	public PodcastLibrary updatePodcast(Podcast p){
		new RSSParser(p, this);
		return this;
	}
	/**
	 * @param p podcast added to the Library
	 */
	protected void addPodcast(Podcast p){
		podcasts.add(p);
		super.setChanged();
		super.notifyObservers(p);
		super.clearChanged();
	}
	
	/**
	 * Writes Library Data do Disk into 
	 * Given Directory
	 * @throws FileNotFoundException	
	 * @throws IOException
	 */
	public void exportLibrary() throws FileNotFoundException, IOException{
		String saveDir =PodcastPropertiesManager.getPropertyManager().getProperty(PodcastPropertiesManager.LIBRARY_FILE);
		File f = new File(saveDir);

		if (!f.exists()) {
			new File(f.getParent()).mkdirs();
			
			f.createNewFile();
		}
		ObjectOutputStream out; 
		out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
		out.writeObject(this);
		out.close();
	}

	/**
	 * Imports Library from given save Directory
	 * @param saveDir Directory
	 * @return imported Library
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static PodcastLibrary importLibray() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in;
		in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(
				PodcastPropertiesManager.getPropertyManager().getProperty(PodcastPropertiesManager.LIBRARY_FILE))));

		lib = (PodcastLibrary) in.readObject();
		in.close();
		return lib;
		
	}
	
	/**
	 * Remove Podcast from Libray
	 * @param title	Title of the Podcast
	 * @return	true/ false
	 */
	public boolean removePodcast(String title){
		for(Podcast p : podcasts)
			if(p.getTitle().equals(title))
				return podcasts.remove(p);
		return false;
		
	}

	public void updatePodcasts() {
		for(Podcast p : podcasts)
			new RSSParser(p, this);
	}
	
}
