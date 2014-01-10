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
package player.mp3;

import player.PodcastPlayer;
import player.PodcastPlayerException;
import util.Time;
import data.Episode;


public class MP3PodcastPlayer implements PodcastPlayer {
	public static long framelength = 26; //all times in ms
	
	private MP3Player player;
	private static MP3PodcastPlayer instance;
	private Episode currentEpisode;
	
	private MP3PodcastPlayer(){
		player = null;
		currentEpisode = null;
	}
	
	public static MP3PodcastPlayer getInstance(){
		if (instance == null)
			instance = new MP3PodcastPlayer();
		return instance;
	}
	
	@Override
	public Episode getCurrentEpisode() {
		return currentEpisode;
	}
	
	@Override
	public void setEpisode(Episode episode) throws PodcastPlayerException {
		if(player != null){
			player.stop();
			player = null;
		}
		currentEpisode = episode;
		if(player == null){
			player = new MP3Player(currentEpisode.isDownloaded() ? currentEpisode.getLocalFile() : currentEpisode.getDownloadLink(), "MP3PodcastThread");
		}
	}

	@Override
	public void pause() throws PodcastPlayerException {
		if(player == null)
			throw new PodcastPlayerException("Player nonexistent");
		player.pause();
	}
	

	@Override
	public void pauseToggle() throws PodcastPlayerException {
		if(player == null)
			throw new PodcastPlayerException("Player nonexistent");
		player.pauseToggle();
	}

	@Override
	public void play() throws PodcastPlayerException {
		if(currentEpisode == null)
			throw new PodcastPlayerException("Episode not set");

		player.play();
	}

	@Override
	public void resume() throws PodcastPlayerException {
		if(player == null)
			throw new PodcastPlayerException("Player nonexistent");		
	}

	@Override
	public void stop() throws PodcastPlayerException {
		if(player == null)
			throw new PodcastPlayerException("Player nonexistent");
		player.stop();
		player = null;
	}
	
	@Override
	public Time getRemainingTime() {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public Time getPlayedTime() {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public Time getPlayTime() {
		throw new UnsupportedOperationException("NYI");
	}	
	
	@Override
	public void fastforward() throws PodcastPlayerException {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public void rewind() throws PodcastPlayerException {
		throw new UnsupportedOperationException("NYI");		
	}
	
	@Override
	public void setPlayTime(Time t) throws PodcastPlayerException {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public void skipSeconds(int seconds) throws PodcastPlayerException {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public void rewindSeconds(int seconds) throws PodcastPlayerException {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public void skip30seconds() throws PodcastPlayerException {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public void rewind30seconds() throws PodcastPlayerException {
		throw new UnsupportedOperationException("NYI");		
	}

	@Override
	public boolean isPaused() {
		return player.isPaused();
	}



	@Override
	public boolean isStopped() {
		return player.isStopped();
	}





}
