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

import java.net.URL;

import javazoom.jl.decoder.JavaLayerException;
import player.mp3.JLayerPlayerPausable.PlaybackListener;

public class MP3Player implements Runnable {

	private URL filePath;
	private JLayerPlayerPausable player;
	private Thread playerThread;
	private String namePlayerThread = "MP3PlayerThread";

	public MP3Player(URL filePath) {
		this.filePath = filePath;
	}

	public MP3Player(URL filePath, String namePlayerThread) {
		this.filePath = filePath;
		this.namePlayerThread = namePlayerThread;
	}

	public void play() {
		if (player == null) {
			this.playerInitialize();
		} else if (!player.isPaused() || player.isComplete() || player.isStopped()) {
			this.stop();
			this.playerInitialize();
		}
		playerThread = new Thread(this, namePlayerThread);
		//playerThread.setDaemon(true);
		playerThread.start();
	}
	
	private void playerInitialize() {
		try {
			player = new JLayerPlayerPausable(filePath);
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

	public void pause() {
		if (player != null) {
			player.pause();
			if (playerThread != null) 
				playerThread = null;
			
		}
	}

	public void pauseToggle() {
		if (player != null) {
			if (player.isPaused() && !player.isStopped()) {
				play();
			} else
				pause();
		}
	}

	public void stop() {
		if (player != null) {
			player.stop();

			if (playerThread != null)
				playerThread = null;
			
		}
	}

	public void run() {
		try {
			player.resume();
		} catch (javazoom.jl.decoder.JavaLayerException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void setPlaybackListener(PlaybackListener newPlaybackListener){
		player.setPlaybackListener(newPlaybackListener);
	}
	
	public boolean isPaused(){
		return player.isPaused();
	}
	
	public boolean isStopped(){
		return player.isStopped();
	}
}
