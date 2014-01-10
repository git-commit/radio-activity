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

public class EpisodeAlphabeticalComperator implements Comparator<Episode>{

	@Override
	public int compare(Episode o1, Episode o2) {
		return o1.getTitle().compareTo(o2.getTitle());
	}

}
