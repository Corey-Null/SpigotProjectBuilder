package me.nullbyte.creator;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		// TODO add other types of schematics
		EventQueue.invokeLater(() -> {
			new WorkspaceCreator(WorkspaceType.MAVEN);
		});
	}

}
