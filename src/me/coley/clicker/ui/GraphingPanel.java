package me.coley.clicker.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphingPanel extends JPanel {
	private final static int MAX_STAT = 1000;
	private static int SIZE_CUT = 10;
	private static int CAPACITY = MAX_STAT / SIZE_CUT;
	private ArrayList<Integer> graphData = new ArrayList<Integer>(CAPACITY);
	private ArrayList<Integer> pureData = new ArrayList<Integer>();
	private double max = 0;

	public GraphingPanel() {
		JButton btnIncreaseAccuracy = new JButton("-");
		JButton btnDecreaseAccuracy = new JButton("+");
		JButton btnReset = new JButton("Reset");
		JLabel lbl = new JLabel("Grouping: " + SIZE_CUT);
		btnIncreaseAccuracy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SIZE_CUT -= 1;
				if (SIZE_CUT <= 0) {
					SIZE_CUT = 1;
				}
				CAPACITY = MAX_STAT / SIZE_CUT;
				graphData = new ArrayList<Integer>(CAPACITY);
				lbl.setText("Grouping: " + SIZE_CUT);
				clearGraphData();
				for (int i : pureData) {
					addValue(i, false);
				}
				repaint();
			}
		});
		btnDecreaseAccuracy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SIZE_CUT += 1;
				int max = MAX_STAT / 10;
				if (SIZE_CUT >= max) {
					SIZE_CUT = max;
				}
				CAPACITY = MAX_STAT / SIZE_CUT;
				graphData = new ArrayList<Integer>(CAPACITY);
				lbl.setText("Grouping: " + SIZE_CUT);
				clearGraphData();
				for (int i : pureData) {
					addValue(i, false);
				}
				repaint();
			}
		});
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pureData.clear();
				clearGraphData();
				repaint();
			}
		});
		add(lbl);
		add(btnIncreaseAccuracy);
		add(btnDecreaseAccuracy);
		add(btnReset);
		clearGraphData();
	}

	@Override
	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D) gg;
		int w = getWidth();
		int h = getHeight();

		g.setColor(getBackground());
		g.clearRect(0, 0, w, h);
		g.setColor(Color.black);

		int minY = 0, maxY = 1;
		for (int i = 0; i < graphData.size(); i++) {
			int val = graphData.get(i);
			if (val > 0) {
				if (minY == 0) {
					minY = i;
					maxY = i + 1;
				} else {
					maxY = i;
				}
			}
		}
		for (int i = minY; i < maxY; i++) {
			int val = graphData.get(i);
			double he = val == 0 ? 0 : val / max;
			int rw = w / (maxY - minY);
			int rx = (i - minY) * rw;
			int rh = (int) Math.round((he) * h / 1.3);
			int ry = h - rh;
			g.fillRect(rx, ry, rw, rh);
		}
	}

	private void clearGraphData() {
		max = 0;
		while (graphData.size() <= CAPACITY) {
			graphData.add(0);
		}
		for (int i = 0; i < graphData.size(); i++) {
			graphData.set(i, 0);
		}
	}

	private void addValue(int i, boolean addData) {
		if (addData)
			pureData.add(i);
		if (i > MAX_STAT) {
			i = MAX_STAT;
		}

		i /= SIZE_CUT;
		//
		int val = graphData.get(i);
		if (val + 1 > max) {
			max = val + 1;
		}
		graphData.set(i, val + 1);
		repaint();
	}

	public void addValue(int i) {
		addValue(i, true);
	}
}
