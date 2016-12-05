package me.coley.clicker.agent;

import java.io.File;
import java.lang.instrument.Instrumentation;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import me.coley.clicker.ui.MainGUI;

/**
 * Entry point used for injecting the auto-clicker into another java process.
 * 
 * @author Matt
 */
public class Agent {
	public static void agentmain(String agentArgs, Instrumentation inst) {
		premain(agentArgs);
	}

	public static void premain(String agentArgs, Instrumentation inst) {
		premain(agentArgs);
	}

	public static void agentmain(String agentArgs) {
		premain(agentArgs);
	}

	public static void premain(String agentArgs) {
		if (agentArgs.contains("dir:")) {
			String dir = agentArgs.substring(agentArgs.indexOf(":") + 1);
			MainGUI.main(new String[] { "dir", dir });
		} else {
			MainGUI.main(new String[] {});
		}
	}

	/**
	 * Loads this agent into a given VM. 
	 * 
	 * @param agentPath
	 *            The path to the agent jar
	 */
	public static void loadAgentToTarget(String target, String options) {
		// Oddly though using this only works half of the times.
		// Tried it 10 times command line. 
		// Five times it injected into the target VM
		// Five times it ran as if no arguments were given at all
		// TODO: Figure out why this behaves oddly
		for (VirtualMachineDescriptor vm : VirtualMachine.list()) {
			if (vm.displayName().contains(target)) {
				loadAgent(getCurrentLocation(), vm.id(), options);
				break;
			}
		}
	}

	/**
	 * Loads an agent into a given VM.
	 * 
	 * @param agentPath
	 * @param vmID
	 */
	private static void loadAgent(String agentPath, String vmID, String options) {
		try {
			VirtualMachine vm = VirtualMachine.attach(vmID);
			vm.loadAgent(agentPath, options);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the current path of the program file (jar).
	 * 
	 * @return
	 */
	private static String getCurrentLocation() {
		// Wrapped in File.getAbsolutePath() because the path it returns it looks nicer.
		return new File(Agent.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getAbsolutePath();
	}
}
