package me.coley.clicker.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GraphingPanel extends JPanel {
	private final static int MAX_STAT = 1000;
	private final static int SIZE_CUT = 10;
	private final static int CAPACITY = MAX_STAT / SIZE_CUT;
	private ArrayList<Integer> data = new ArrayList<Integer>(CAPACITY);
	private double max = 0;

	public GraphingPanel() {
		clear();
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
		for (int i = 0; i < data.size(); i++) {
			int val = data.get(i);
			if (val > 0) {
				if (minY == 0) {
					minY = i;
					maxY = i + 1;
				} 
				else {
					maxY = i;
				}
			}
		}
		for (int i = minY; i < maxY; i++) {
			int val = data.get(i);
			double he = val == 0 ? 0 : val / max;
			int rw = w / (maxY - minY);
			int rx = (i-minY) * rw;
			int rh = (int) Math.round((he) * h / 1.3);
			int ry = h - rh;
			g.fillRect(rx, ry, rw, rh);
		}
	}

	public void clear() {
		max = 0;
		while (data.size() <= CAPACITY) {
			data.add(0);
		}
		for (int i = 0; i < data.size(); i++) {
			data.set(i, 0);
		}
	}

	public void addValue(int i) {
		if (i > MAX_STAT) {
			i = MAX_STAT;
		}
		i /= SIZE_CUT;
		//
		int val = data.get(i);
		if (val + 1 > max) {
			max = val + 1;
		}
		data.set(i, val + 1);
		repaint();
	}
}
