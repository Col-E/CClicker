package me.coley.clicker.ui;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import me.coley.clicker.util.Lang;

/**
 * Builds JPanels that follow a similar format.
 * 
 * @author Matt
 *
 */
public class PageBuilder {
    public static JPanel build(String title, String texxt) {
        JPanel panel = new JPanel();
        JLabel version = new JLabel(title);
        JTextArea text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        panel.setLayout(null);
        text.setText(texxt);
        text.setFont(new Font("Arial", Font.PLAIN, 15));
        text.setBackground(SystemColor.control);
        text.setBounds(10, 40, 280, 300);
        version.setFont(new Font("Arial", Font.BOLD, 20));
        version.setBounds(10, 10, 200, 25);
        panel.add(text);
        panel.add(version);
        return panel;
    }

    public static JPanel getVersionPage(int i) {
        String title = "", texxt = "";
        switch (i) {
        case 1:
            title = "1.0";
            texxt = Lang.get(Lang.CHANGELOG_1_0);
            break;
        }
        return build(title, texxt);
    }
}
