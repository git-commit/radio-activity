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
package player;
import data.Episode;
import util.Time;


public interface PodcastPlayer{
	
	void setEpisode(Episode episode) throws PodcastPlayerException;
	
	//Player Functions
	void setPlayTime(Time t) throws PodcastPlayerException;
	void skipSeconds(int seconds) throws PodcastPlayerException;
	void rewindSeconds(int seconds) throws PodcastPlayerException;
	void skip30seconds() throws PodcastPlayerException; //in case of optimization
	void rewind30seconds() throws PodcastPlayerException; //in case of optimization
	void pause() throws PodcastPlayerException;
	void pauseToggle() throws PodcastPlayerException;
	void play() throws PodcastPlayerException;
	void resume() throws PodcastPlayerException;
	void stop() throws PodcastPlayerException;
	void fastforward() throws PodcastPlayerException;
	void rewind() throws PodcastPlayerException;
	
	Time getRemainingTime();
	Time getPlayedTime();
	Time getPlayTime();

	Episode getCurrentEpisode();
	boolean isPaused();
	boolean isStopped();
}
