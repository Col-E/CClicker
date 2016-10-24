package me.coley.clicker;

import java.util.logging.Level;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import me.coley.clicker.ui.BotGUI;
import me.coley.simplejna.hook.mouse.MouseEventReceiver;
import me.coley.simplejna.hook.mouse.MouseHook;
import me.coley.simplejna.hook.mouse.struct.MOUSEHOOKSTRUCT;
import me.coley.simplejna.hook.mouse.struct.MouseButtonType;

/**
 * Handler for the recording mouse inputs.
 * 
 * @author Matt
 *
 */
public class Stats {
    private boolean status;
    private DescriptiveStatistics clickFrequency;
    private DescriptiveStatistics clicksX;
    private DescriptiveStatistics clicksY;
    private MouseEventReceiver lastMouseHook;

    private void onDisable() {
        // Finished, save stats
        BotGUI.log.log(Level.INFO, "Finished recording.");
        if (lastMouseHook != null) {
            MouseHook.unhook(lastMouseHook);
        }
        BotGUI.onRecordingFinished();
    }

    private void onEnable() {
        // Start new stats
        BotGUI.log.log(Level.INFO, "Beginning recording of mouse input.");
        clickFrequency = new DescriptiveStatistics();
        clicksX = new DescriptiveStatistics();
        clicksY = new DescriptiveStatistics();
        BotGUI.log.log(Level.INFO, "Creating keybind-listener...");
        MouseEventReceiver me = new MouseEventReceiver() {
            private long last = -1;

            @Override
            public boolean onMousePress(MouseButtonType type, MOUSEHOOKSTRUCT info) {
                long now = System.currentTimeMillis();
                if (last != -1) {
                    clickFrequency.addValue(now - last);
                }
                last = now;
                clicksX.addValue(info.pt.x);
                clicksY.addValue(info.pt.y);
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
        };
        MouseHook.hook(me);
    }

    public void toggle() {
        status = !status;
        if (status) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public DescriptiveStatistics getFrequencyData() {
        return clickFrequency;
    }

    public DescriptiveStatistics getPosXData() {
        return clicksX;
    }

    public DescriptiveStatistics getPosYData() {
        return clicksY;
    }

}
