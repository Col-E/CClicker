package me.coley.clicker;

import java.util.Random;
import java.util.logging.Level;

import me.coley.clicker.ui.BotGUI;
import me.coley.simplejna.Mouse;
import me.coley.simplejna.Windows;

/**
 * Handler for the clicking process.
 * 
 * @author Matt
 *
 */
public class Clicker {
    private boolean status;
    private static String target;

    public void toggle() {
        status = !status;
        if (status) {
            onEnable();
        } else {
            BotGUI.log.log(Level.INFO, "Stopping clicker thread...");
        }
    }

    private void onEnable() {
        BotGUI.log.log(Level.INFO, "Launching clicker thread...");
        new Thread() {
            private final Random r = new Random();

            @Override
            public void run() {
                while (status) {
                    if (canClick()) {
                        Mouse.mouseLeftClick(-1, -1);
                    }
                    try {
                        double dev = BotGUI.settings.getNumericSetting(Values.SET_DEV_DELAY).getCurrent();
                        double mean = BotGUI.settings.getNumericSetting(Values.SET_AVG_DELAY).getCurrent();
                        int min = BotGUI.settings.getNumericSetting(Values.SET_MIN_DELAY).getCurrent();
                        int max = BotGUI.settings.getNumericSetting(Values.SET_MAX_DELAY).getCurrent();
                        // Gaussian random sleep. Tends to sleep with times
                        // around the mean. Times near the bounds (min/max) are
                        // less common.
                        long sleep = (long) clamp(Math.round(r.nextGaussian() * dev + mean), min, max);
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            private long clamp(double val, int min, int max) {
                // Java needs to implement a clamp function in java.util.math...
                return (long) Math.max(min, Math.min(max, val));
            }
        }.start();
    }

    /**
     * Checks if a target window needs to be active.
     * 
     * @return
     */
    private static boolean canClick() {
        if (BotGUI.settings.getBooleanSetting(Values.SET_WINDOW_TARGET).getCurrent() && target != null) {
            if (Windows.getCurrentWindowTitle().equals(target)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the clicker's target window.
     * 
     * @param target
     */
    public void setTargetWindow(String target) {
        Clicker.target = target;
    }
}
