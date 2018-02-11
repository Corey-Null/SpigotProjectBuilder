package me.nullbyte.creator.swing.type;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import me.nullbyte.creator.types.commands.CommandType;

public class CommandCreator extends JFrame {

	private static final long serialVersionUID = 7991308960505296743L;
	private JPanel contentPane;
	private JTextField commandText;
	private JTextField permText;
	private JTextField usageText;
	private JTextField argText;

	public CommandCreator(TypeFrame parent) {
		super("+~ Project Command Creator ~+");
		setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 411);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		createLabels();
		createTextFields();

		JComboBox<String> consoleUse = new JComboBox<String>();
		consoleUse.setBounds(10, 260, 86, 20);
		consoleUse.addItem("true");
		consoleUse.addItem("false");
		contentPane.add(consoleUse);

		JButton createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String cmd = commandText.getText();
				String perm = permText.getText();
				String usage = permText.getText();
				String arg = argText.getText();
				boolean console = Boolean.parseBoolean(consoleUse.getSelectedItem().toString());
				if (cmd.isEmpty()) {
					JOptionPane.showMessageDialog(null, "You need to specify this with a command name.");
					return;
				}
				int args;
				if (arg.isEmpty()) {
					try {
						args = Integer.parseInt(arg);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "You need to specify a real number as args.");
						return;
					}
				} else {
					args = 0;
				}
				CommandType type = new CommandType(cmd);
				if (!perm.isEmpty()) {
					type.permission(perm);
				}
				if (!usage.isEmpty()) {
					type.usage(usage);
				}
				type.console(console);
				if (args > 0) {
					type.args(args);
				}
				parent.getManager().addType(type);
				parent.getViewer().update(0x1);
				dispose();
			}
		});
		createButton.setBounds(10, 338, 414, 23);
		contentPane.add(createButton);
	}

	public void createLabels() {
		JLabel commandLabel = new JLabel("Command Name:");
		commandLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		commandLabel.setBounds(10, 11, 131, 14);
		contentPane.add(commandLabel);

		JLabel permLabel = new JLabel("Permission:");
		permLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		permLabel.setBounds(10, 67, 109, 14);
		contentPane.add(permLabel);

		JLabel usageLabel = new JLabel("Usage:");
		usageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		usageLabel.setBounds(10, 123, 109, 14);
		contentPane.add(usageLabel);

		JLabel argLabel = new JLabel("Arguments:");
		argLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		argLabel.setBounds(10, 179, 109, 14);
		contentPane.add(argLabel);

		JLabel consoleLabel = new JLabel("Console Use:");
		consoleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		consoleLabel.setBounds(10, 235, 109, 14);
		contentPane.add(consoleLabel);
	}

	public void createTextFields() {
		commandText = new JTextField();
		commandText.setBounds(10, 36, 414, 20);
		contentPane.add(commandText);
		commandText.setColumns(10);

		permText = new JTextField();
		permText.setColumns(10);
		permText.setBounds(10, 92, 414, 20);
		contentPane.add(permText);

		usageText = new JTextField();
		usageText.setColumns(10);
		usageText.setBounds(10, 148, 414, 20);
		contentPane.add(usageText);

		argText = new JTextField();
		argText.setBounds(10, 204, 86, 20);
		contentPane.add(argText);
		argText.setColumns(10);
	}

}
