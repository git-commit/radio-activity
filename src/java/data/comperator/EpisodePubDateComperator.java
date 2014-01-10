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

import data.Episode;

public class EpisodePubDateComperator implements Comparator<Episode>{

	@Override
	public int compare(Episode o1, Episode o2) {
		int ret = o1.pubDate().compareTo(o2.pubDate());
		if(ret == 0)
			return new EpisodeAlphabeticalComperator().compare(o1, o2);
		return ret;
	}

}
