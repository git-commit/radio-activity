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

public class PodcastPlayerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8515514423241089672L;
	
	public PodcastPlayerException(String s) {
		super(s);
	}
	
	public PodcastPlayerException() {
		super();
	}
	
}
