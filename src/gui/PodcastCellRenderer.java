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
package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;


import data.Podcast;

public class PodcastCellRenderer implements ListCellRenderer<Podcast> {

	

	@Override
	public Component getListCellRendererComponent(
			JList<? extends Podcast> list, Podcast value, int index,
			boolean isSelected, boolean cellHasFocus) {
		return new PodcastPanel(value, isSelected);
	}
	
	public class PodcastPanel extends JPanel{
		private static final long serialVersionUID = -4347013464647107266L;
		public PodcastPanel(Podcast value, boolean isSelected){
			JLabel title = new JLabel(value.getTitle());
			JLabel unp = new JLabel("\t"+value.getNumberOfUnplayedEpisodes());
			JLabel fav = new JLabel(new ImageIcon("\t"+value.getNumberOfFavEpisodersNum()));
			
			JLabel image = new JLabel (value.getImage());
			JLabel author = new JLabel(value.getAuthor());
			JLabel unplayed = new JLabel(new ImageIcon("icon/unplayed.png"));
			unplayed.setToolTipText("Number of Favourie Episode of this Podcast : " +value.getNumberOfFavEpisodersNum());
			JLabel faves = new JLabel(new ImageIcon("icon/heart_fill.png"));
			faves.setToolTipText("Number of Unplayed Episode of this Podcast : " +value.getNumberOfUnplayedEpisodes());
			title.setFont(new Font("Arial", Font.PLAIN, 14));
			unplayed.setFont(new Font("Arial", Font.PLAIN, 14));
			fav.setFont(new Font("Arial", Font.PLAIN, 14));
			author.setFont(new Font("Arial", Font.PLAIN, 10));
			author.setForeground(Color.GRAY);
			GridBagLayout gbl = new GridBagLayout();
			this.setLayout(gbl);
			if(!isSelected){
				this.setBackground(Color.WHITE);
			}
			else{
				this.setBackground(Color.BLUE);
				title.setForeground(Color.WHITE);
				unp.setForeground(Color.WHITE);
				author.setForeground(Color.LIGHT_GRAY);
			}
			
			addComp(this, gbl, image , 1, 1, 2, 2, 0, 0);
			addComp(this, gbl, title , 3, 1, 1, 1, 3, 3);
			addComp(this, gbl, unplayed,6, 1, 1, 1, 0, 0);
			addComp(this, gbl, unp	 , 5, 1, 1, 1, 0, 0);
			
			addComp(this, gbl, author, 3, 2, 2, 1, 0, 0);
			addComp(this, gbl, faves ,6, 2, 1, 1, 0, 0);
			addComp(this, gbl, fav	 ,5, 2, 1, 1, 0, 0);
		}
		
		private void addComp(final Container co, final GridBagLayout gbl,
			    final Component c, final int x, final int y, final int width, 
			    final int height, final double wx, final double wy) {
			        GridBagConstraints gbc =  new GridBagConstraints();
			        gbc.fill =GridBagConstraints.BOTH;
			        gbc.anchor = GridBagConstraints.WEST;
			        gbc.insets = new Insets(0, 0, 0, 2);
			        gbc.gridx= x;                   // Zeile
			        gbc.gridy= y;                   // Spalte
			        gbc.gridwidth =width;           // Anzahl an Kasten in Spalte
			        gbc.gridheight = height;        // Anzahl an Kasten in Reihe
			        gbc.weightx = wx;               // Prioritat Platz verwendung x achse
			        gbc.weighty = wy;               // Prioritat Platz verwendung y achse
			        gbl.setConstraints(c, gbc);
			        co.add(c);
			}
	}
	
}
