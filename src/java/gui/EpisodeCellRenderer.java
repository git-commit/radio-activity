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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import data.Episode;

public class EpisodeCellRenderer  implements ListCellRenderer<Episode>  {

	@Override
	public Component getListCellRendererComponent(
			JList<? extends Episode> list, Episode value, int index,
			boolean isSelected, boolean cellHasFocus) {
		return new EpisodePanel(value, isSelected);
	}

	public class EpisodePanel extends JPanel {
	
	/**
		 * 
		 */
		private static final long serialVersionUID = 6685752087872968949L;
	public EpisodePanel(Episode value, boolean isSelected) {
		
			GridBagLayout gbl = new GridBagLayout();
			this.setLayout(gbl);
			JLabel title = new JLabel(value.getTitle());
			title.setFont(new Font("Arial", Font.PLAIN, 14));
			JLabel subtitle = new JLabel(value.getSubtitle());
			subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
			JLabel pubDate = new JLabel(value.pubDate().toString());
			pubDate.setFont(new Font("Arial", Font.PLAIN, 10));
			JLabel isPlayed;
			JLabel isFav;
			if(value.isPlayed())
				isPlayed = new JLabel(new ImageIcon("icon/played.png"));
			else
				isPlayed = new JLabel(new ImageIcon("icon/unplayed.png"));
			if(value.isFavorit())
				isFav = new JLabel(new ImageIcon("icon/heart_fill.png"));
			else
                isFav = new JLabel(new ImageIcon("icon/heart_stroke.png"));

			if(isSelected){
				this.setBackground(Color.BLUE);
				title.setForeground(Color.WHITE);
				subtitle.setForeground(Color.WHITE);
				pubDate.setForeground(Color.LIGHT_GRAY);
			} else {
				this.setBackground(Color.WHITE);
				title.setForeground(Color.BLACK);
				subtitle.setForeground(Color.BLACK);
				pubDate.setForeground(Color.DARK_GRAY);
			}
			addComp(this, gbl, title , 0, 1, 3, 1, 1, 0);
			
			addComp(this, gbl, subtitle , 0, 2, 3, 1, 0, 0);
			addComp(this, gbl, isPlayed , 0, 3, 1, 1, 0, 0);
			addComp(this, gbl, isFav, 1, 3, 1, 1, 0, 0);
			addComp(this, gbl, pubDate , 2, 3, 1, 1, 0, 0);
			
	}
	private void addComp(final Container co, final GridBagLayout gbl,
		    final Component c, final int x, final int y, final int width, 
		    final int height, final double wx, final double wy) {
		        GridBagConstraints gbc =  new GridBagConstraints();
		        gbc.fill =GridBagConstraints.BOTH;
		        gbc.anchor = GridBagConstraints.WEST;
		        //gbc.insets = new Insets(0, 0, 0, 2);
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
