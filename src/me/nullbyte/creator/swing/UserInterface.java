package me.nullbyte.creator.swing;

import java.awt.Font;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import me.nullbyte.creator.ProjectInfo;
import me.nullbyte.creator.WorkspaceCreator;
import me.nullbyte.creator.swing.type.TypeFrame;
import me.nullbyte.creator.types.TypeManager;

public class UserInterface {

	private final WorkspaceCreator creator;
	private final JFrame frame;
	private final TypeManager manager;
	private JTextField nameText;
	private JTextField packageText;
	private JTextField descText;
	private JTextField outputText;

	public UserInterface(WorkspaceCreator creator, TypeManager manager) {
		this.creator = creator;
		this.manager = manager;
		this.frame = new JFrame("+~ Project Creator ~+");
		createFrame();
		createLabels();
		createTextFields();
		createButtons();
		frame.setVisible(true);
	}

	public void createFrame() {
		frame.setBounds(100, 100, 450, 340);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
	}

	public void createLabels() {
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		nameLabel.setBounds(10, 11, 46, 14);
		frame.getContentPane().add(nameLabel);

		JLabel packageLabel = new JLabel("Package:");
		packageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		packageLabel.setBounds(10, 67, 138, 14);
		frame.getContentPane().add(packageLabel);

		JLabel descLabel = new JLabel("Description: ");
		descLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		descLabel.setBounds(10, 123, 138, 14);
		frame.getContentPane().add(descLabel);

		JLabel outputLabel = new JLabel("Output: ");
		outputLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		outputLabel.setBounds(10, 179, 138, 14);
		frame.getContentPane().add(outputLabel);
	}

	public void createTextFields() {
		nameText = new JTextField();
		nameText.setBounds(10, 36, 414, 20);
		frame.getContentPane().add(nameText);
		nameText.setColumns(10);

		packageText = new JTextField();
		packageText.setBounds(10, 92, 414, 20);
		frame.getContentPane().add(packageText);
		packageText.setColumns(10);

		descText = new JTextField();
		descText.setBounds(10, 148, 414, 20);
		frame.getContentPane().add(descText);
		descText.setColumns(10);

		outputText = new JTextField();
		outputText.setBounds(10, 204, 414, 20);
		frame.getContentPane().add(outputText);
		outputText.setColumns(10);
	}

	public void createButtons() {
		JButton extrasButton = new JButton("~~~Extras~~~");
		extrasButton.setBounds(10, 235, 414, 23);
		extrasButton.addActionListener((event) -> {
			new TypeFrame(manager).setVisible(true);
		});
		frame.getContentPane().add(extrasButton);

		JButton createButton = new JButton("Create");
		createButton.setBounds(10, 269, 414, 23);
		createButton.addActionListener((event) -> {
			String name = nameText.getText();
			String pkg = packageText.getText();
			String desc = descText.getText();
			String output = outputText.getText();
			if (name.isEmpty() || pkg.isEmpty() || desc.isEmpty() || output.isEmpty()) {
				JOptionPane.showMessageDialog(null, "One or more field is empty");
				return;
			}
			File outputFolder;
			try {
				outputFolder = new File(output + File.separator + name);
				ProjectInfo info = new ProjectInfo();
				info.name(name);
				info.pkg(pkg);
				info.description(desc);
				creator.generate(outputFolder, info);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error creating schematic...");
			}
		});
		frame.getContentPane().add(createButton);

		FileOpenButton fileButton = new FileOpenButton(frame);
		fileButton.setBounds(335, 176, 89, 23);
		fileButton.setResponder((str) -> outputText.setText(str));
		frame.getContentPane().add(fileButton);
	}

}
