package me.coley.clicker.ui.controls;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JButton;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;

import me.coley.clicker.Keybinds;
import me.coley.clicker.ui.BotGUI;
import me.coley.clicker.value.Updatable;
import me.coley.jnathread.hook.key.KeyEventReceiver;
import me.coley.jnathread.hook.key.KeyHook;

@SuppressWarnings("serial")
/**
 * Button with an associated label
 * 
 * @author Matt
 */
public class LabeledBindButton extends LabeledComponent implements Updatable {
    private final int settingID;
    private JButton btnBind;

    /**
     * Creates a button for a keybind.
     * 
     * @param settingID
     *            Keybind ID.
     */
    public LabeledBindButton(int settingID) {
        super(BotGUI.keybinds.getName(settingID));
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
        BotGUI.log.log(Level.INFO, "Keybind updated: " + vkCode);
        // Update the keybind
        BotGUI.keybinds.update(settingID, vkCode);
        // Update button
        update();
        // Stop hook
        KeyHook.unhook(notifier);
    }

    @Override
    public void update() {
        btnBind.setText("Key ID:" + Keybinds.getKeyName(BotGUI.keybinds.getKey(settingID)));
    }

    @Override
    public void create() {
        setLayout(null);
        setPreferredSize(new Dimension(130, 48));
        btnBind = new JButton("Key ID:" + BotGUI.keybinds.getKey(settingID));
        btnBind.setFocusable(false);
        btnBind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BotGUI.log.log(Level.INFO, "Creating keybind-listener...");
                btnBind.setEnabled(false);
                // Create a new keyboard hook that waits for a key to be
                // pressed.
                // That key will become the new bind.
                KeyEventReceiver notifier = new KeyEventReceiver() {
                    @Override
                    public boolean onKeyRelease(boolean sys, KBDLLHOOKSTRUCT info) {
                        keyChanged(info.vkCode, this);
                        return false;
                    }

                    @Override
                    public boolean onKeyPress(boolean sys, KBDLLHOOKSTRUCT info) {
                        return false;
                    }
                };
                KeyHook.hook(notifier);
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
