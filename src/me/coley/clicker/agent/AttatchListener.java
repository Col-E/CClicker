package me.coley.clicker.agent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

public class AttatchListener implements ActionListener {
	private final JList<String> list;

	public AttatchListener(JList<String> list) {
		this.list = list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int i = list.getSelectedIndex();
		if (i != -1) {
			String id = list.getModel().getElementAt(i);
			try {
				Agent.loadAgentToTarget(id, "dir:" + System.getProperty("settings.dir"));
				System.exit(0);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
