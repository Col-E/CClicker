package me.coley.clicker.ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import me.coley.clicker.Clicker;
import me.coley.clicker.Keybinds;
import me.coley.clicker.Stats;
import me.coley.clicker.Values;
import me.coley.clicker.agent.Agent;
import me.coley.clicker.agent.AttatchListener;
import me.coley.clicker.jna.KeyHandler;
import me.coley.clicker.ui.controls.LabeledBindButton;
import me.coley.clicker.ui.controls.LabeledCheckbox;
import me.coley.clicker.ui.controls.LabeledSlider;
import me.coley.clicker.util.Lang;
import me.coley.clicker.util.NumberUtil;
import me.coley.clicker.util.SwingUtil;
import me.coley.clicker.value.BooleanValue;
import me.coley.clicker.value.NumericValue;
import me.coley.simplejna.hook.key.KeyHookManager;
import me.coley.simplejna.hook.mouse.MouseHookManager;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;

import java.awt.FlowLayout;

public class MainGUI {
	public static final Logger log = Logger.getLogger("Clicker-Init");
	public final Values settings = new Values();
	public final Keybinds keybinds = new Keybinds();
	public final Clicker clicker = new Clicker(this);
	public final Stats stats = new Stats(this);
	public final KeyHookManager keyHooks = new KeyHookManager();
	public final MouseHookManager mouseHooks = new MouseHookManager();
	private final KeyHandler handler = new KeyHandler(keyHooks, this);
	private final boolean isAttatched;
	public GraphingPanel graph;
	private JFrame frmClicker;
	private JTextArea txtStats;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Set the settings.dir property so it can be used by AbsoluteFile.
		String curDir = System.getProperty("user.dir");
		System.setProperty("settings.dir", curDir);
		// Handle args if there are any
		AtomicBoolean displayAgent = new AtomicBoolean(true);
		if (args.length >= 0) {
			for (String arg : args) {
				if (arg.startsWith("agentTarget:")) {
					// Agent that runs the program in another VM.
					String target = arg.substring("agentTarget:".length());
					Agent.loadAgentToTaretFromVMName(target, "dir:" + curDir);
					return;
				} else if (arg.contains("dir:")) {
					// Adjusts the settings.dir property.
					String dir = arg.substring("dir:".length());
					System.setProperty("settings.dir", dir);
				} else if (arg.contains("displayAgentTab:")) {
					//
					String val = arg.substring("displayAgentTab:".length());
					displayAgent.set(Boolean.parseBoolean(val));
				}
			}
		}
		try {
			log.log(Level.INFO, "Setting java visual theme to 'System'");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			log.log(Level.SEVERE, "Error setting gui theme. Excepttion thrown: " + e.toString());
		}
		log.log(Level.INFO, "Starting gui...");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI gui = new MainGUI(!displayAgent.get());
					gui.initSettings();
					gui.initGui();
					gui.loadSettings();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainGUI(boolean isAttatched) {
		this.isAttatched = isAttatched;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initGui() {
		log.log(Level.INFO, "Setting up gui...");
		frmClicker = new JFrame();
		frmClicker.setResizable(false);
		frmClicker.setTitle("CClicker");
		frmClicker.setBounds(100, 100, 422, 398);
		frmClicker.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmClicker.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				log.log(Level.INFO, "Saving settings...");
				settings.save();
				keybinds.save();
				if (!isAttatched) {
					// Not attached, just suicide the program
					System.exit(0);
				} else {
					// Attached, exiting would kill the parent program.
					// Carefully disable / dispose instead.
					if (clicker.getStatus())
						clicker.toggle();
					if (stats.getStatus())
						stats.toggle();
					keyHooks.unhook(handler);
					frmClicker.dispose();
				}
			}
		});
		JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
		frmClicker.getContentPane().add(tabs, BorderLayout.CENTER);
		JPanel tabHome = new JPanel();
		tabs.addTab(Lang.get(Lang.HOME_TITLE), null, tabHome, null);
		tabHome.setLayout(new BorderLayout(0, 0));
		{
			JSplitPane splitPane = new JSplitPane();
			tabHome.add(splitPane);
			JTree tree = new JTree(SwingUtil.createTreeMenu());
			SwingUtil.expandAllNodes(tree);
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					if (node == null) {
						return;
					}
					if (node instanceof GuiNavTreeNode) {
						splitPane.setRightComponent(((GuiNavTreeNode) node).getPanel());
					}
				}
			});
			splitPane.setLeftComponent(tree);
			splitPane.setRightComponent(SwingUtil.buildPage(Lang.get(Lang.HOME_WELCOME_TITLE), Lang.get(Lang.HOME_WELCOME_MSG)));
		}
		JPanel tabControl = new JPanel();
		tabs.addTab(Lang.get(Lang.CONTROLS_TITLE), null, tabControl, null);
		tabControl.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		{
			for (int settingID : keybinds.getKeybinds().keySet()) {
				LabeledBindButton bb = new LabeledBindButton(this, settingID);
				keybinds.register(settingID, bb);
				tabControl.add(bb);
			}
		}

		JPanel tabSettings = new JPanel();
		tabs.addTab(Lang.get(Lang.SETTINGS_TITLE), null, tabSettings, null);
		tabSettings.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		{
			// Adding numeric slider options
			for (int settingID : settings.getNumericValues().keySet()) {
				LabeledSlider slider = new LabeledSlider(this, settings.getName(settingID), settingID);
				settings.registerUser(settingID, slider);
				tabSettings.add(slider);
			}
			LabeledCheckbox ucb = new LabeledCheckbox(this, Values.SET_WINDOW_TARGET);
			settings.registerUser(Values.SET_WINDOW_TARGET, ucb);
			tabSettings.add(ucb);
			JComboBox<String> combo = new JComboBox<String>(new String[] { Lang.get(Lang.SETTINGS_WINDOW_CHOOSE) });
			combo.setFocusable(false);
			combo.setSelectedIndex(0);
			combo.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					clicker.setTargetWindow(combo.getSelectedItem().toString());
				}

			});
			combo.setModel(SwingUtil.getWindowsModel());
			combo.setMaximumSize(new Dimension(100, 100));
			combo.setPreferredSize(new Dimension(frmClicker.getWidth() - 20, 23));
			combo.setSize(100, 100);
			tabSettings.add(combo);

		}
		JPanel tabStatistics = new JPanel();
		tabs.addTab(Lang.get(Lang.STATISTICS_TITLE), null, tabStatistics, null);
		tabStatistics.setLayout(new BorderLayout());
		{
			JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			graph = new GraphingPanel();
			txtStats = new JTextArea();
			txtStats.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			txtStats.setEditable(false);
			txtStats.setFocusable(false);
			txtStats.setText(Lang.get(Lang.STATISTICS_RECORD_SOME_DATA));
			txtStats.setBackground(SystemColor.control);
			txtStats.setFont(new Font("Arial", Font.PLAIN, 12));

			sp.setTopComponent(graph);
			sp.setBottomComponent(new JScrollPane(txtStats));
			sp.setDividerLocation(frmClicker.getHeight() / 2);
			tabStatistics.add(sp, BorderLayout.CENTER);
		}
		if (!isAttatched) {
			JPanel tabAgent = new JPanel();
			tabs.addTab(Lang.get(Lang.AGENT_TITLE), null, tabAgent, null);
			tabAgent.setLayout(new BorderLayout());
			JButton btnAction = new JButton(Lang.get(Lang.AGENT_ATTATCH_VM));
			JList<String> list = new JList<String>();
			list.setModel(Agent.getJVMS());
			list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			btnAction.addActionListener(new AttatchListener(list));
			JScrollPane listScroller = new JScrollPane(list);
			tabAgent.add(listScroller, BorderLayout.CENTER);
			tabAgent.add(btnAction, BorderLayout.NORTH);
			/*
			 * list = new JList(data); //data has type Object[]
			 * list.setSelectionMode(ListSelectionModel.
			 * SINGLE_INTERVAL_SELECTION);
			 * list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			 * list.setVisibleRowCount(-1); ... JScrollPane listScroller = new
			 * JScrollPane(list); listScroller.setPreferredSize(new
			 * Dimension(250, 80));
			 */
		}
		log.log(Level.INFO, "Displaying gui");
		frmClicker.setVisible(true);
	}

	/**
	 * Creates the default values for settings.
	 */
	protected void initSettings() {
		log.log(Level.INFO, "Setting up default settings...");
		settings.addNumericValue(Values.SET_MIN_DELAY, Lang.get(Lang.SETTINGS_DELAY_MIN), new NumericValue(100, 5, 1000));
		settings.addNumericValue(Values.SET_AVG_DELAY, Lang.get(Lang.SETTINGS_DELAY_AVG), new NumericValue(150, 5, 1000));
		settings.addNumericValue(Values.SET_MAX_DELAY, Lang.get(Lang.SETTINGS_DELAY_MAX), new NumericValue(300, 5, 1000));
		settings.addNumericValue(Values.SET_DEV_DELAY, Lang.get(Lang.SETTINGS_DELAY_DEV), new NumericValue(50, 0, 200));
		settings.addBooleanValue(Values.SET_WINDOW_TARGET, Lang.get(Lang.SETTINGS_WINDOW_TARGET), new BooleanValue(false));
		keybinds.addKeyValue(Keybinds.BIND_TOGGLE_RECORDING, Lang.get(Lang.CONTROLS_TOGGLE_STATS), -1);
		keybinds.addKeyValue(Keybinds.BIND_TOGGLE_CLICKER, Lang.get(Lang.CONTROLS_TOGGLE_CLICK), -1);
		keybinds.addKeyValue(Keybinds.BIND_TOGGLE_GUI, Lang.get(Lang.CONTROLS_TOGGLE_GUI), -1);
		log.log(Level.INFO, "Creating keybind-listener...");
		keyHooks.hook(handler);
	}

	/**
	 * Load the stored values of settings.
	 */
	protected void loadSettings() {
		log.log(Level.INFO, "Loading settings...");
		settings.load();
		keybinds.load();
	}

	/**
	 * Updates the statistics panel with data from the most recent recording.
	 */
	public void onRecordingFinished() {
		DescriptiveStatistics f = stats.getFrequencyData();
		double mx = f.getMax();
		double mn = f.getMin();
		double av = f.getMean();
		double dv = f.getStandardDeviation();
		settings.updateNumeric(Values.SET_MAX_DELAY, (int) mx);
		settings.updateNumeric(Values.SET_MIN_DELAY, (int) mn);
		settings.updateNumeric(Values.SET_AVG_DELAY, (int) av);
		settings.updateNumeric(Values.SET_DEV_DELAY, (int) dv);
		Random r = new Random();
		double x1 = NumberUtil.clamp(Math.round(r.nextGaussian() * dv + av), mn, mx);
		double x2 = NumberUtil.clamp(Math.round(r.nextGaussian() * dv + av), mn, mx);
		double x3 = NumberUtil.clamp(Math.round(r.nextGaussian() * dv + av), mn, mx);
		double x4 = NumberUtil.clamp(Math.round(r.nextGaussian() * dv + av), mn, mx);
		double x5 = NumberUtil.clamp(Math.round(r.nextGaussian() * dv + av), mn, mx);
		//@formatter:off
		String s = 
		"Data in seconds:\n" +
		"Minimum Delay: " + NumberUtil.fmtNum(f.getMin()/1000,6) + "s\n"+
		"Maximum Delay: " + NumberUtil.fmtNum(f.getMax()/1000,6) + "s\n" +
		"Average Delay: " + NumberUtil.fmtNum(f.getMean()/1000,6) + "s\n" +
		"Std.Deviation: " + NumberUtil.fmtNum(f.getStandardDeviation(),6) + "s\n"
		+"\nExample output:\n" +
		"1. " + NumberUtil.fmtNum(x1/1000,6) + "s\n"+
		"2. " + NumberUtil.fmtNum(x2/1000,6) + "s\n"+
		"3. " + NumberUtil.fmtNum(x3/1000,6) + "s\n"+
		"4. " + NumberUtil.fmtNum(x4/1000,6) + "s\n"+
		"5. " + NumberUtil.fmtNum(x5/1000,6) + "s\n" ;
		//@formatter:on
		txtStats.setText(s);
	}

	/**
	 * Toggles the GUI visibility
	 */
	public void toggleVisible() {
		frmClicker.setVisible(!frmClicker.isVisible());
	}
}
