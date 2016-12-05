package me.coley.clicker.ui.controls;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.coley.clicker.ui.MainGUI;
import me.coley.clicker.util.Lang;
import me.coley.clicker.value.ValueUser;

/**
 * Checkbox with an associated label
 * 
 * @author Matt
 *
 */
@SuppressWarnings("serial")
public class LabeledCheckbox extends LabeledComponent implements ValueUser {
	private final int settingID;
	private JCheckBox chk;

	/**
	 * Creates a checkbox for a toggleable setting.
	 * 
	 * @param settingID
	 */
	public LabeledCheckbox(MainGUI gui, int settingID) {
		super(gui, gui.settings.getName(settingID));
		this.settingID = settingID;
		create();
	}

	@Override
	public void onValueUpdated() {
		boolean v = gui.settings.getBooleanSetting(settingID).getCurrent();
		if (v != chk.isSelected()) {
			chk.setSelected(v);
		}
	}

	@Override
	public void create() {
		// Normally LabeledComponent uses Box, but in this case it looks off so
		// Border is used.
		setLayout(new BorderLayout());
		chk = new JCheckBox(Lang.get(Lang.SETTINGS_ACTIVE), gui.settings.getBooleanSetting(settingID).getCurrent());
		chk.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// Update setting when the checkbox is modified
				gui.settings.updateBoolean(settingID, chk.isSelected());
			}
		});
		add(genNameLabel(), BorderLayout.NORTH);
		add(chk, BorderLayout.SOUTH);
	}

}
