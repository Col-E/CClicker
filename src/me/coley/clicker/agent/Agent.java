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
		try {
			if (agentArgs.contains("dir:")) {
				String dir = agentArgs.substring(agentArgs.indexOf(":") + 1);
				MainGUI.main(new String[] { "dir", dir });
			} else {
				MainGUI.main(new String[] {});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads this agent into a given VM.
	 * 
	 * @param agentPath
	 *            The path to the agent jar
	 */
	public static void loadAgentToTarget(String target, String options) {
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
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the current path of the program file (jar).
	 * 
	 * @return
	 */
	private static String getCurrentLocation() {
		// Wrapped in File.getAbsolutePath() because it looks nicer.
		return new File(Agent.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getAbsolutePath();
	}
}
