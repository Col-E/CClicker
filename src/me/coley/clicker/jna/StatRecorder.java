package me.coley.clicker.jna;

import me.coley.clicker.Stats;
import me.coley.clicker.ui.GraphingPanel;
import me.coley.simplejna.hook.mouse.MouseEventReceiver;
import me.coley.simplejna.hook.mouse.MouseHookManager;
import me.coley.simplejna.hook.mouse.struct.MOUSEHOOKSTRUCT;
import me.coley.simplejna.hook.mouse.struct.MouseButtonType;

public class StatRecorder extends MouseEventReceiver {
	private long last = -1;
	private final Stats stats;
	private final GraphingPanel graph;

	public StatRecorder(MouseHookManager hookManager, Stats stats, GraphingPanel graph) {
		super(hookManager);
		this.stats = stats;
		this.graph = graph;
	}

	@Override
	public boolean onMousePress(MouseButtonType type, MOUSEHOOKSTRUCT info) {
		long now = System.currentTimeMillis();
		if (last != -1) {
			stats.getFrequencyData().addValue(now - last);
			graph.addValue((int) (now - last));
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
