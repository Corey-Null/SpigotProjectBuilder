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
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import me.nullbyte.creator.types.TypeManager;
import me.nullbyte.creator.types.commands.CommandType;

public class TypeViewer extends JFrame {

	private static final long serialVersionUID = 8847840636948449856L;
	private final TypeManager manager;
	private JComboBox<Integer> indexBox;
	private JTextArea text;
	private JPanel contentPane;

	public TypeViewer(TypeManager manager) {
		super("+~ Project Types ~+");
		this.manager = manager;
		setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel indexLabel = new JLabel("Index:");
		indexLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		indexLabel.setBounds(10, 11, 57, 14);
		contentPane.add(indexLabel);

		indexBox = new JComboBox<Integer>();
		indexBox.setBounds(77, 9, 57, 20);
		contentPane.add(indexBox);
		createText();
		createButtons();
	}

	public void createText() {
		text = new JTextArea();
		text.setEditable(false);
		text.setBounds(10, 36, 414, 214);
		contentPane.add(text);
	}

	public void update(int reason) {
		switch (reason) {
		case 0x1:
			indexBox.addItem(indexBox.getItemCount() + 1);
			break;
		case 0x2:
			indexBox.removeItem(indexBox.getItemCount());
			break;
		}
		StringBuilder builder = new StringBuilder();
		boolean empty = true;
		int index = 1;
		for (me.nullbyte.creator.types.Type type : manager.getTypeList()) {
			if (empty) {
				empty = false;
			} else {
				builder.append("\n\r");
			}
			String name = type instanceof CommandType ? "Command: " : "Listener: ";
			builder.append(index + ": " + name + type.getName());
			index++;
		}
		text.setText(builder.toString());
	}

	public void createButtons() {
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer chosen = (Integer) indexBox.getSelectedItem();
				if (chosen == null) {
					JOptionPane.showMessageDialog(null, "There are currently no extras added.");
					return;
				}
				StringBuilder loaded = new StringBuilder();
				me.nullbyte.creator.types.Type type = manager.getIndex(chosen - 1);
				if (type == null) {
					return;
				}
				if (type instanceof CommandType) {
					loaded.append("Type: Command\n\r");
					loaded.append("Name: " + type.getName());
					loaded.append("\n\r");
					CommandType cmd = (CommandType) type;
					loaded.append("Command: /" + type.getName().toLowerCase());
					loaded.append("\n\r");
					loaded.append("Permission: " + String.valueOf(cmd.getPerm()));
					loaded.append("\n\r");
					loaded.append("Usage: " + String.valueOf(cmd.getUsage()));
					loaded.append("\n\r");
					loaded.append("Args: " + String.valueOf(cmd.getArgs()));
					loaded.append("\n\r");
					loaded.append("Console Use: " + String.valueOf(cmd.isConsole()));
				} else {
					loaded.append("Type: Listener\n\r");
					loaded.append("Name: " + type.getName());
				}
				JOptionPane.showMessageDialog(null, loaded.toString(), "Type Viewer", JOptionPane.DEFAULT_OPTION);
			}
		});
		btnView.setBounds(236, 8, 89, 23);
		contentPane.add(btnView);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer chosen = (Integer) indexBox.getSelectedItem();
				if (chosen == null) {
					JOptionPane.showMessageDialog(null, "There are currently no extras added.");
					return;
				}
				manager.removeType(chosen - 1);
				update(0x2);
			}
		});
		btnRemove.setBounds(335, 8, 89, 23);
		contentPane.add(btnRemove);
	}
}
