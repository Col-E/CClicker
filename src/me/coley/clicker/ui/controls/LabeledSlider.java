package me.coley.clicker.ui.controls;

import java.awt.Label;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.coley.clicker.ui.MainGUI;
import me.coley.clicker.value.NumericValue;
import me.coley.clicker.value.ValueUser;

/**
 * A slider with an associated label.
 * 
 * @author Matt
 *
 */
@SuppressWarnings("serial")
public class LabeledSlider extends LabeledComponent implements ValueUser {
	private final int settingID;
	private int init, min, max;
	private JSlider slider;
	private Label label;

	public LabeledSlider(MainGUI gui, String name, int settingID) {
		super(gui, name);
		this.settingID = settingID;
		// Retrieve numeric setting
		NumericValue v = gui.settings.getNumericSetting(settingID);
		this.init = v.getCurrent();
		this.min = v.getMin();
		this.max = v.getMax();
		create();
	}

	@Override
	public void create() {
		label = genNameLabel();
		slider = new JSlider();
		slider.setFocusable(false);
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setValue(init);
		// TODO: Find a better method of creating minor/major ticks
		int dif = (max - min);
		int minor = dif / 100;
		int major = dif / 10;
		if (minor == 0) {
			minor = 1;
			major = 2;
		}
		slider.setMinorTickSpacing(minor);
		slider.setMajorTickSpacing(major);
		slider.setPaintTicks(true);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// Update numeric setting when slider is moved
				gui.settings.getNumericSetting(settingID).setValue(slider.getValue());
				updateLabel();
			}
		});
		label.setText(name + ":" + slider.getValue());
		add(label);
		add(slider);
	}

	@Override
	public void onValueUpdated() {
		// Update slider when the associated setting is changed
		slider.setValue(gui.settings.getNumericSetting(settingID).getCurrent());
		updateLabel();
	}

	private void updateLabel() {
		label.setText(name + ":" + slider.getValue());
		label.repaint();
	}
}
