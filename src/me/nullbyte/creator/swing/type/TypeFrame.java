package me.nullbyte.creator.swing.type;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import me.nullbyte.creator.types.TypeManager;
import me.nullbyte.creator.types.listeners.ListenerType;

public class TypeFrame extends JFrame {

	private static final long serialVersionUID = -5080476834127936062L;
	private final TypeViewer viewer;
	private final TypeManager manager;
	private JPanel contentPane;

	public TypeFrame(TypeManager manager) {
		super("+~ Project Extras ~+");
		this.manager = manager;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		viewer = new TypeViewer(manager);

		JButton btnViewTypes = new JButton("View Types");
		btnViewTypes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				viewer.setVisible(!viewer.isVisible());
			}
		});
		contentPane.add(btnViewTypes, BorderLayout.CENTER);

		JButton btnCreateCommand = new JButton("Create Command");
		btnCreateCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new CommandCreator(TypeFrame.this);
				frame.setVisible(true);
			}
		});
		contentPane.add(btnCreateCommand, BorderLayout.WEST);

		JButton btnCreateListener = new JButton("Create Listener");
		btnCreateListener.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String text = JOptionPane.showInputDialog(null, "Please type in a name for the listener.");
				if (text != null && !text.isEmpty()) {
					manager.addType(new ListenerType(text.replace(" ", "")));
					viewer.update(0x1);
				}
			}
		});
		contentPane.add(btnCreateListener, BorderLayout.EAST);
	}

	public TypeViewer getViewer() {
		return viewer;
	}

	public TypeManager getManager() {
		return manager;
	}

}
