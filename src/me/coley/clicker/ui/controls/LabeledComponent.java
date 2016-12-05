package me.coley.clicker.ui.controls;

import java.awt.Font;
import java.awt.Label;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import me.coley.clicker.ui.MainGUI;

/**
 * Custom component (JPanel) containing X (Being another component) with an
 * associated label.
 * 
 * @author Matt
 *
 */
@SuppressWarnings("serial")
public abstract class LabeledComponent extends JPanel {
	protected final String name;
	protected final MainGUI gui;

	public LabeledComponent(MainGUI gui, String name) {
		this.gui = gui;
		this.name = name;
		// Vertical align components
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	/**
	 * Creates a Label with the stored name.
	 * 
	 * @return
	 */
	protected final Label genNameLabel() {
		Label l = new Label(name);
		l.setFont(new Font("Arial", Font.BOLD, 10));
		return l;
	}

	/**
	 * Initiate the component.
	 */
	public abstract void create();
}
