package me.coley.clicker.ui;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * TreeNode that stores an associated panel for display.
 * 
 * @author Matt
 */
@SuppressWarnings("serial")
public class GuiNavTreeNode extends DefaultMutableTreeNode {
    private JPanel panel;

    public GuiNavTreeNode(String name, JPanel panel) {
        super(name);
        this.panel = panel;

    }

    public JPanel getPanel() {
        return panel;
    }
}
