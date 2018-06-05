package me.coley.clicker.jna;

import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.POINT;

import me.coley.clicker.Stats;
import me.coley.clicker.ui.GraphingPanel;
import me.coley.simplejna.hook.mouse.MouseEventReceiver;
import me.coley.simplejna.hook.mouse.MouseHookManager;
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
	public boolean onMousePress(MouseButtonType type, HWND hwnd, POINT point) {
		if (type == MouseButtonType.LEFT_DOWN) {
			long now = System.currentTimeMillis();
			if (last != -1) {
				stats.getFrequencyData().addValue(now - last);
				graph.addValue((int) (now - last));
			}
			last = now;
		}
		return false;
	}

	@Override
	public boolean onMouseRelease(MouseButtonType type, HWND hwnd, POINT pt) {
		return false;
	}

	@Override
	public boolean onMouseScroll(boolean down, HWND hwnd, POINT pt) {
		return false;
	}
	
	@Override
	public boolean onMouseMove(HWND hwnd, POINT pt) {
		return false;
	}
}
