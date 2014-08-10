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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class PodcastPropertiesManager {
	private static final String propertiesPath = System.getProperty("user.home")+File.separator+".radio.ini";
	private static PodcastPropertiesManager me;
    private static final Logger log = Logger.getLogger(PodcastPropertiesManager.class.getName());

	public static final String LIBRARY_FILE = "library.file";
	public static final String EPISODES_PATH = "episodes.path";
	public static final String PLAYER_TEMP = "player.temp";
	/**
	 * 
	 */
	public static final String LIBRARY_FILE_DEFAULT = System
			.getProperty("user.home") +File.separator+"Radio-Activity"+File.separator+"radioactivity.lib";
	public static final String EPISODES_PATH_DEFAULT = System
			.getProperty("user.home") +File.separator+ "Radio-Activity" +File.separator;
	public static final String PLAYER_TEMP_DEFAULT = System
			.getProperty("user.home") +File.separator+ "Radio-Activity"+File.separator+".TEMP" +File.separator;

	public Properties properties;

	private PodcastPropertiesManager(){
		File propFile = new File(propertiesPath);
		properties = new Properties();
			try {
				FileInputStream in;
				in = new FileInputStream(propFile);
				properties.loadFromXML(in);
				in.close();
			} catch (FileNotFoundException e) {
				properties = new Properties();
				this.createDefaultProperties();
                log.info("Created default property file.");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static PodcastPropertiesManager getPropertyManager() {
		if (me == null)
			me = new PodcastPropertiesManager();
		return me;
	}

	/**
	 * Adds Default Properties To new Property Object
	 */
	private void createDefaultProperties() {
		properties.put(LIBRARY_FILE, LIBRARY_FILE_DEFAULT);
		properties.put(EPISODES_PATH, EPISODES_PATH_DEFAULT);
		properties.put(PLAYER_TEMP, PLAYER_TEMP_DEFAULT);
		saveProperty();
	}

	/**
	 * Get Property from KEY
	 * 
	 * @param key
	 * @return property value
	 */
	public synchronized String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Put a Property
	 * 
	 * @param key
	 * @param value
	 */
	public synchronized void putProperty(String key, String value) {
		properties.put(key, value);
		saveProperty();
	}

	private void saveProperty() {
		FileOutputStream out;
		File f = new File(propertiesPath);
		f.delete();
		try {
			out = new FileOutputStream(f);
			properties.storeToXML(out, "666RAD");
		} catch (FileNotFoundException e) {
 		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

