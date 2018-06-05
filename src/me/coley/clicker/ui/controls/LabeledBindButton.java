package me.coley.clicker.ui.controls;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JButton;
import me.coley.clicker.Keybinds;
import me.coley.clicker.ui.MainGUI;
import me.coley.clicker.value.ValueUser;
import me.coley.simplejna.hook.key.KeyEventReceiver;

@SuppressWarnings("serial")
/**
 * Button with an associated label
 * 
 * @author Matt
 */
public class LabeledBindButton extends LabeledComponent implements ValueUser {
	private final int settingID;
	private JButton btnBind;

	/**
	 * Creates a button for a keybind.
	 * 
	 * @param settingID
	 *            Keybind ID.
	 */
	public LabeledBindButton(MainGUI gui, int settingID) {
		super(gui, gui.keybinds.getName(settingID));
		this.settingID = settingID;
		create();
	}

	/**
	 * Called when the the bind is modified to another character.
	 * 
	 * @param vkCode
	 * @param notifier
	 */
	public void keyChanged(int vkCode, KeyEventReceiver notifier) {
		MainGUI.log.log(Level.INFO, "Keybind updated: " + vkCode);
		// Update the keybind
		gui.keybinds.updateKeybind(settingID, vkCode);
		// Update button
		onValueUpdated();
		// Stop hook
		gui.keyHooks.unhook(notifier);
	}

	@Override
	public void onValueUpdated() {
		btnBind.setText("Key ID:" + Keybinds.getKeyName(gui.keybinds.getKey(settingID)));
	}

	@Override
	public void create() {
		setLayout(null);
		setPreferredSize(new Dimension(130, 48));
		btnBind = new JButton("Key ID:" + gui.keybinds.getKey(settingID));
		btnBind.setFocusable(false);
		btnBind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainGUI.log.log(Level.INFO, "Creating keybind-listener...");
				btnBind.setEnabled(false);
				// Create a new keyboard hook that waits for a key to be
				// pressed.
				// That key will become the new bind.
				KeyEventReceiver notifier = new KeyEventReceiver(gui.keyHooks) {
					@Override
					public boolean onKeyUpdate(SystemState sys, PressState press, int time, int vkCode) {
						if (press == PressState.UP) {
							// on release
							keyChanged(vkCode, this);
						}
						return false;
					}
				};
				gui.keyHooks.hook(notifier);
				btnBind.setEnabled(true);
				btnBind.setText("PRESS A KEY");
			}
		});
		Label label = genNameLabel();
		label.setBounds(0, 3, 120, 13);
		btnBind.setBounds(0, 22, 120, 25);
		add(btnBind);
		add(label);
	}
}
