package me.coley.clicker.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.google.common.collect.Lists;
import com.sun.jna.platform.win32.WinDef.HWND;

import me.coley.clicker.ui.GuiNavTreeNode;
import me.coley.simplejna.Windows;

/**
 * Assorted utilities dealing with swing.
 * 
 * @author Matt
 */
public class SwingUtil {

	/**
	 * Expands all nodes in a tree
	 * 
	 * @param tree
	 */
	public static void expandAllNodes(JTree tree) {
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

	/**
	 * Creates the tree node on the main page.
	 * 
	 * @return
	 */
	public static DefaultMutableTreeNode createTreeMenu() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(Lang.get(Lang.HOME_TREE_HOME));
		{
			DefaultMutableTreeNode versions = new DefaultMutableTreeNode(Lang.get(Lang.HOME_TREE_VERSIONHIST));
			{
				versions.add(new GuiNavTreeNode("1.0", SwingUtil.getVersionPage("1.0")));
				versions.add(new GuiNavTreeNode("1.1", SwingUtil.getVersionPage("1.1")));
				versions.add(new GuiNavTreeNode("1.2", SwingUtil.getVersionPage("1.2")));
				versions.add(new GuiNavTreeNode("1.3", SwingUtil.getVersionPage("1.3")));
			}
			DefaultMutableTreeNode usage = new GuiNavTreeNode(Lang.get(Lang.HOME_TREE_USAGEGUIDE), SwingUtil.buildPage(Lang.get(Lang.HOME_TREE_USAGEGUIDE), Lang.get(Lang.HOME_TREE_USAGEGUIDE_MSG)));
			top.add(versions);
			top.add(usage);
		}
		return top;
	}

	/**
	 * Returns a ComboBoxModel containing an array of window titles.
	 * 
	 * @return
	 */
	public static ComboBoxModel<String> getWindowsModel() {
		String[] a = null;
		List<String> l = Lists.newArrayList();
		for (Entry<HWND, String> e : Windows.getWindows().entrySet()) {
			String s = e.getValue();
			if (s.length() > 0 && !l.contains(s)) {
				l.add(s);
			}
		}
		Collections.sort(l);
		l.add(0, "Choose a Window");
		a = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			a[i] = l.get(i);
		}
		return new DefaultComboBoxModel<String>(a);
	}

	/**
	 * Build's a titled page.
	 * 
	 * @param title
	 * @param text
	 * @return
	 */
	public static JPanel buildPage(String title, String text) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
		panel.setLayout( new BorderLayout());
		JLabel lblTitle = new JLabel(title);
		JTextArea textArea = new JTextArea();		
		textArea.setMaximumSize(new Dimension(280,9999));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setText(text);
		textArea.setFont(new Font("Arial", Font.PLAIN, 15));
		textArea.setBackground(SystemColor.control);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBorder(BorderFactory.createEmptyBorder(6,0,0,0));
		lblTitle.setBackground(SystemColor.red);
		panel.add(lblTitle, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * Build's a titled verion page.
	 * 
	 * @param i
	 * @return
	 */
	public static JPanel getVersionPage(String version) {
		String text = "";
		switch (version) {
		case  "1.0":
			text = Lang.get(Lang.CHANGELOG_1_0);
			break;
		case "1.1":
			text = Lang.get(Lang.CHANGELOG_1_1);
			break;
		case "1.2":
			text = Lang.get(Lang.CHANGELOG_1_2);
			break;
		case "1.3":
			text = Lang.get(Lang.CHANGELOG_1_3);
			break;
		}
		return buildPage(version, text);
	}
}
