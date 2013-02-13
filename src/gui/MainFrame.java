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




import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import player.PodcastPlayerException;
import player.mp3.MP3PodcastPlayer;

import com.google.common.collect.ImmutableList;

import data.Episode;
import data.Podcast;
import data.PodcastLibrary;

public class MainFrame extends JFrame implements Observer {
	private final JFrame frame;
	private static final long serialVersionUID = -6325374892958547616L;
	private final JScrollPane jsp_left;
	private final JScrollPane jsp_right;
	private final JPanel panel_left;
	private final JPanel panel_right;
	private final JLabel label_podcast;
	private final JLabel label_episode;
	private final JSeparator sep_upper;
	private final JSeparator sep_lower;
	private final JList<Podcast> list_podcast;
	private final DefaultListModel<Podcast> listmodel_podcast;
	private final JList<Episode> list_episode;
	private final DefaultListModel<Episode> listmodel_episode;
	//
	private final JButton btn_addPodcast;
	private final JButton btn_removePodcast;
	private final JButton btn_updatePodcast;

	private final JButton btn_playEpisode;
	private final JButton btn_pauseEpisode;
	private final JButton btn_toggleFav;
	private final JButton btn_togglePlayed;
	
	private PodcastLibrary library;
	private MP3PodcastPlayer player;

	public MainFrame() {
		super("Radio-Activity");
		setIconImage(new ImageIcon("icon/radio-activity.png").getImage());

		GridBagLayout gbl = new GridBagLayout();

		try {
			library = PodcastLibrary.importLibray();
		} catch (ClassNotFoundException
				| IOException e) {
			library = PodcastLibrary.getLibrary();
		}
		frame = this;
		library.addObserver(this);
		player = MP3PodcastPlayer.getInstance();
		library.updatePodcasts();

		// List
		listmodel_podcast = new DefaultListModel<Podcast>();
		listmodel_episode = new DefaultListModel<Episode>();
		list_podcast = new JList<Podcast>(listmodel_podcast);
		list_episode = new JList<Episode>(listmodel_episode);
		for(Podcast p : library.getPodcasts())
			listmodel_podcast.addElement(p);



		list_podcast.setCellRenderer(new PodcastCellRenderer());
		list_episode.setCellRenderer(new EpisodeCellRenderer());

		// Label
		label_podcast = new JLabel("Podcasts");
		label_episode = new JLabel("Episoden");
		//Seperators
		sep_upper = new JSeparator(JSeparator.VERTICAL);
		sep_lower = new JSeparator(JSeparator.VERTICAL);
		// ScrollPane
		panel_left = new JPanel();
		panel_left.setLayout(new BorderLayout());
		panel_left.add(list_podcast, BorderLayout.CENTER);
		jsp_left = new JScrollPane(panel_left);

		panel_right = new JPanel();
		panel_right.setLayout(new BorderLayout());
		panel_right.add(list_episode, BorderLayout.CENTER);
		jsp_right = new JScrollPane(panel_right);

		// Buttons Left
		btn_addPodcast = new JButton(new ImageIcon("icon/plus.png"));
		btn_addPodcast.setToolTipText("Add a Podcast");
		btn_removePodcast = new JButton(new ImageIcon("icon/minus.png"));
		btn_removePodcast.setToolTipText("Remove Selected Podcast");
		btn_updatePodcast =new JButton(new ImageIcon("icon/reload.png"));
		btn_updatePodcast.setToolTipText("Upadte Selected Podcast");
		btn_toggleFav = new JButton(new ImageIcon("icon/heart_stroke_big.png"));
		btn_toggleFav.setToolTipText("Toggle Selected Episode Fav-status");
		btn_togglePlayed = new JButton(new ImageIcon("icon/played_big.png"));
		btn_togglePlayed.setToolTipText("Toggle Selected Episode Played-status");
		// Buttons Right
		btn_playEpisode = new JButton(new ImageIcon("icon/play.png"));
		btn_pauseEpisode = new JButton(new ImageIcon("icon/pause.png"));

		this.setLayout(gbl);
		addComp(this, gbl, label_podcast, 					1, 1, 1, 1, 0, 0);
		addComp(this, gbl, jsp_left, 						1, 2, 7, 16, 1, 1);
		addComp(this , gbl, btn_updatePodcast,				1, 18, 1, 1, 0, 0);
		addComp(this , gbl, btn_addPodcast, 				6, 18, 1, 1, 0, 0);
		addComp(this , gbl, btn_removePodcast, 				5, 18, 1,1, 0, 0);
		addComp(this, gbl, sep_lower,							8, 18, 1, 1, 0, 0);
		addComp(this, gbl, sep_upper, 							8, 1, 1, 1, 0, 0);
		addComp(this, gbl, label_episode, 					9, 1, 1, 1, 0, 0);
		addComp(this, gbl, jsp_right, 						8, 2, 8, 16, 1, 1);
		addComp(this, gbl, btn_playEpisode, 				9, 18, 1, 1, 0, 0);
		addComp(this, gbl, btn_pauseEpisode, 				9, 18, 1, 1, 0, 0);
		addComp(this, gbl, btn_toggleFav, 					10, 18, 1, 1, 0, 0);
		addComp(this, gbl, btn_togglePlayed, 				11, 18, 1, 1, 0, 0);
		addListeners();


		setVisible(true);
		btn_pauseEpisode.setVisible(false);
		setMinimumSize(new Dimension(640,480));
		pack();

	}

	private void updateEpisodeList(){
		listmodel_episode.setSize(0);
		Podcast selected = list_podcast.getSelectedValue();
		if(selected != null){
			int sel = list_episode.getSelectedIndex();
			ImmutableList<Episode> episodes = selected.getEpisodes();
			for(Episode e : episodes)
				listmodel_episode.addElement(e);
			list_episode.setSelectedIndex(sel);

		}
	}

	private void updatePodcastList(){

		int sel = list_podcast.getSelectedIndex();
		listmodel_podcast.setSize(0);

		ImmutableList<Podcast> podcasts = library.getPodcasts();
		for(Podcast p : podcasts)
			listmodel_podcast.addElement(p);
		list_podcast.setSelectedIndex(sel);
		updateEpisodeList();
	}

	private void addListeners() {
		btn_playEpisode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Episode episodeToBePlayed = list_episode.getSelectedValue();
				if(episodeToBePlayed != null){
					try {
						if(episodeToBePlayed != player.getCurrentEpisode()){
							player.setEpisode(episodeToBePlayed);
						}
						player.play();
						btn_playEpisode.setVisible(false);
						btn_pauseEpisode.setVisible(true);
					} catch (PodcastPlayerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		btn_pauseEpisode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					player.pause();
				} catch (PodcastPlayerException e) {
					e.printStackTrace();
				}
				btn_playEpisode.setVisible(true);
				btn_pauseEpisode.setVisible(false);


			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosed(e);
				try {
					library.exportLibrary();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});

		btn_removePodcast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Podcast podcastToBeRemoved = list_podcast.getSelectedValue();
				if(podcastToBeRemoved != null){
					library.removePodcast(podcastToBeRemoved.getTitle());
					listmodel_podcast.removeElement(podcastToBeRemoved);
				}
			}
		});

		btn_updatePodcast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updatePodcastList();
			}
		});

		list_podcast.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				updateEpisodeList();
			}
		});

		list_episode.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(list_episode.getSelectedValue() != null){
					if(list_episode.getSelectedValue().isFavorit())
						btn_toggleFav.setIcon(new ImageIcon("icon/heart_fill_big.png"));
					else
						btn_toggleFav.setIcon(new ImageIcon("icon/heart_stroke_big.png"));
					if(list_episode.getSelectedValue().isPlayed())
						btn_togglePlayed.setIcon(new ImageIcon("icon/played_big.png"));
					else
						btn_togglePlayed.setIcon(new ImageIcon("icon/unplayed_big.png"));
				}
			}
		});

		btn_addPodcast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String res = JOptionPane.showInputDialog("Please input new podcast feed URL");
					if(res != null)
						library.putPodcast(new URL(res));
				} catch (HeadlessException | MalformedURLException e) {
					JOptionPane.showMessageDialog(frame, "Please put in a valid url!",
							"Invalid URL!", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});

		btn_toggleFav.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Episode selectedEpisode = list_episode.getSelectedValue();
				int index = list_episode.getSelectedIndex();
				if(selectedEpisode != null){
					if(selectedEpisode.isFavorit())
						selectedEpisode.setFavorit(false);
					else
						selectedEpisode.setFavorit(true);
					updateEpisodeList();
					list_episode.setSelectedIndex(index);
				}
			}
		});

		btn_togglePlayed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Episode selectedEpisode = list_episode.getSelectedValue();
				int index = list_episode.getSelectedIndex();
				if(selectedEpisode != null){
					if(selectedEpisode.isPlayed())
						selectedEpisode.setPlayedTime(0);
					else
						selectedEpisode.setPlayedTime(600000000);
					updatePodcastList();
					list_episode.setSelectedIndex(index);
				}
			}
		});
	}

	public static void main(String[] args) {
		new MainFrame();
	}
	private void addComp(final Container co, final GridBagLayout gbl,
			final Component c, final int x, final int y, final int width, 
			final int height, final double wx, final double wy) {
		GridBagConstraints gbc =  new GridBagConstraints();
		gbc.fill =GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx= x;                   // Zeile
		gbc.gridy= y;                   // Spalte
		gbc.gridwidth =width;           // Anzahl an Kasten in Spalte
		gbc.gridheight = height;        // Anzahl an Kasten in Reihe
		gbc.weightx = wx;               // Prioritat Platz verwendung x achse
		gbc.weighty = wy;               // Prioritat Platz verwendung y achse
		gbl.setConstraints(c, gbc);
		co.add(c);
	}


	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
		updatePodcastList();
		repaint();
	} 
}
