package me.nullbyte.creator.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileOpenButton extends JButton {

	private static final long serialVersionUID = -5392519303540929654L;
	private SFunction function;
	private JFileChooser chooser;

	public FileOpenButton(JFrame frame) {
		super("...");
		this.function = null;
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("C://"));
		chooser.setDialogTitle("Choose a folder.");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		super.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					if (function != null) {
						function.apply(chooser.getSelectedFile().getAbsolutePath());
					}
				}
			}
		});
	}

	public void setResponder(SFunction function) {
		this.function = function;
	}

}
