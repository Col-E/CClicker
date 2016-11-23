package me.coley.clicker.jna;

import me.coley.clicker.Stats;
import me.coley.simplejna.hook.mouse.MouseEventReceiver;
import me.coley.simplejna.hook.mouse.struct.MOUSEHOOKSTRUCT;
import me.coley.simplejna.hook.mouse.struct.MouseButtonType;

public class StatRecorder extends MouseEventReceiver {
	private long last = -1;
	private final Stats stats;

	public StatRecorder(Stats stats) {
		this.stats = stats;
	}

	@Override
	public boolean onMousePress(MouseButtonType type, MOUSEHOOKSTRUCT info) {
		long now = System.currentTimeMillis();
		if (last != -1) {
			stats.getFrequencyData().addValue(now - last);
		}
		last = now;
		return false;
	}

	@Override
	public boolean onMouseRelease(MouseButtonType type, MOUSEHOOKSTRUCT info) {
		return false;
	}

	@Override
	public boolean onMouseScroll(boolean down, MOUSEHOOKSTRUCT info) {
		return false;
	}

	@Override
	public boolean onMouseMove(MOUSEHOOKSTRUCT info) {
		return false;
	}
}
