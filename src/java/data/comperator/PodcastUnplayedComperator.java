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
package data.comperator;

import java.util.Comparator;

import data.Podcast;

public class PodcastUnplayedComperator implements Comparator<Podcast> {

	@Override
	public int compare(Podcast o1, Podcast o2) {
		if(o1.getNumberOfUnplayedEpisodes() > o2.getNumberOfUnplayedEpisodes())
			return 1;
		if(o1.getNumberOfUnplayedEpisodes() < o2.getNumberOfUnplayedEpisodes())
			return -1;
		if(o1.getNumberOfUnplayedEpisodes() == o2.getNumberOfUnplayedEpisodes())
			return new PodcastFavouritesComperator().compare(o1,o2);
		return 0;
	}
	
}
