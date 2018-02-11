package me.nullbyte.creator;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import me.nullbyte.creator.swing.UserInterface;
import me.nullbyte.creator.types.Type;
import me.nullbyte.creator.types.TypeManager;
import me.nullbyte.creator.types.commands.CommandType;
import me.nullbyte.creator.types.listeners.ListenerType;

public class WorkspaceCreator {

	private final Schematic compilerSchematic;
	private final Schematic mainClassSchematic;
	private final Schematic mavenPomSchematic;
	private final Schematic messagesClassSchematic;
	private final Schematic abstractCmdSchematic;
	private final Schematic pluginYamlSchematic;
	private final Schematic messagesYamlSchematic;
	private final Schematic manifestSchematic;
	private final Schematic pompropSchematic;
	private final TypeManager manager;
	private final UserInterface ui;

	public WorkspaceCreator(WorkspaceType type) {
		this.manager = new TypeManager();
		this.compilerSchematic = new Schematic("compiler-schematic");
		this.mainClassSchematic = new Schematic("main-schematic") {
			@Override
			public String getText(ProjectInfo info) {
				Set<CommandType> cmds = manager.getCommands();
				Set<ListenerType> lsnters = manager.getListeners();
				StringBuilder commands = new StringBuilder();
				StringBuilder listeners = new StringBuilder();
				StringBuilder imports = new StringBuilder();
				boolean iEmpty = true;
				boolean cEmpty = true;
				boolean lEmpty = true;
				for (CommandType type : cmds) {
					StringBuilder str = new StringBuilder("getCommand(\"" + type.getCmd().toLowerCase() + "\")");
					str.append(".setExecutor(new " + type.getCmd() + "Command());");
					if (cEmpty) {
						cEmpty = false;
						commands.append(str.toString());
					} else {
						commands.append("\n		" + str.toString());
					}
					String imp = "import #{package}.#{name-lower}.commands." + type.getCmd() + "Command;";
					if (iEmpty) {
						imports.append(imp);
						iEmpty = false;
					} else {
						imports.append("\n" + imp);
					}
				}
				for (ListenerType type : lsnters) {
					StringBuilder str = new StringBuilder("new " + type.getName() + "Listener(this)");
					if (lEmpty) {
						lEmpty = false;
						listeners.append(str.toString());
					} else {
						listeners.append("\n		" + str.toString());
					}
					String imp = "import #{package}.#{name-lower}.listeners." + type.getName() + "Listener;";
					if (iEmpty) {
						imports.append(imp);
						iEmpty = false;
					} else {
						imports.append("\n\r" + imp);
					}
				}
				String text = super.getText(info);
				text = text.replace("#{imports}", imports.toString());
				text = text.replace("#{commands}", commands.toString());
				text = text.replace("#{listeners}", listeners.toString());
				text = text.replace("#{package}", info.getPkg());
				text = text.replace("#{name}", info.getName());
				text = text.replace("#{name-lower}", info.getName().toLowerCase());
				text = text.replace("#{description}", info.getDescription());
				return text;
			}
		};
		this.mavenPomSchematic = new Schematic("maven-schematic");
		this.messagesClassSchematic = new Schematic("messages-schematic");
		this.abstractCmdSchematic = new Schematic("abstractcmd-schematic");
		this.pluginYamlSchematic = new Schematic("pluginyml-schematic") {
			@Override
			public String getText(ProjectInfo info) {
				Set<CommandType> cmds = manager.getCommands();
				StringBuilder commands = new StringBuilder();
				StringBuilder permissions = new StringBuilder();
				boolean cEmpty = true;
				boolean pEmpty = true;
				for (CommandType type : cmds) {
					StringBuilder c = new StringBuilder();
					c.append(type.getCmd().toLowerCase() + ":");
					if (type.getUsage() != null) {
						c.append("\n");
						c.append("    usage: \"" + type.getUsage() + "\"");
					}
					if (type.getPerm() != null) {
						c.append("\n");
						c.append("    permission: \"" + type.getPerm() + "\"");
						StringBuilder p = new StringBuilder(type.getPerm() + ":");
						p.append("\n    default: false");
						if (pEmpty) {
							permissions.append(p.toString());
							pEmpty = false;
						} else {
							permissions.append("\n  " + p.toString());
						}
					}
					if (cEmpty) {
						commands.append(c.toString());
						cEmpty = false;
					} else {
						commands.append("\n  " + c.toString());
					}
				}
				String text = super.getText(info);
				text = text.replace("#{commands}", commands.toString());
				text = text.replace("#{permissions}", permissions.toString());
				text = text.replace("#{package}", info.getPkg());
				text = text.replace("#{name}", info.getName());
				text = text.replace("#{name-lower}", info.getName().toLowerCase());
				text = text.replace("#{description}", info.getDescription());
				return text;
			}
		};
		this.messagesYamlSchematic = new Schematic("messagesyml-schematic");
		manifestSchematic = new Schematic("meta/manifest-schematic");
		pompropSchematic = new Schematic("meta/pomprop-schematic");
		ui = new UserInterface(this, manager);
	}

	public void generate(File output, ProjectInfo info) throws IOException {
		makeFolder(output.getParentFile(), output.getName());
		loadSchematic(output, this.mavenPomSchematic, "pom.xml", info);
		loadSchematic(output, this.compilerSchematic, "compile.cmd", info);
		File target = makeFolder(output, "target");
		String sep = File.separator;
		makeFolder(target, "classes");
		File classes = makeFolder(target, "test-classes");
		File meta = makeFolder(classes, "META-INF");
		loadSchematic(meta, this.manifestSchematic, "MANIFEST.MF", info);
		File properties = makeFolder(meta, "maven" + sep + info.getPkg() + sep + info.getName().toLowerCase());
		loadSchematic(properties, this.pompropSchematic, "pom.properties", info);
		loadSchematic(properties, this.mavenPomSchematic, "pom.xml", info);
		File source = makeFolder(output, "src");
		makeFolder(source, "test" + sep + "java");
		makeFolder(source, "test" + sep + "resources");
		File java = makeFolder(source, "main" + sep + "java");
		File resc = makeFolder(source, "main" + sep + "resources");
		loadSchematic(resc, this.pluginYamlSchematic, "plugin.yml", info);
		loadSchematic(resc, this.messagesYamlSchematic, "messages.yml", info);
		new File(resc, "config.yml").createNewFile();
		File pkg = makeFolder(java, info.getPkg().replace(".", sep) + sep + info.getName().toLowerCase());
		loadSchematic(pkg, this.mainClassSchematic, info.getName() + ".java", info);
		loadSchematic(pkg, this.messagesClassSchematic, "Messages.java", info);
		loadSchematic(pkg, this.abstractCmdSchematic, "AbstractCommand.java", info);
		Set<CommandType> commands = manager.getCommands();
		if (!commands.isEmpty()) {
			File cFolder = makeFolder(pkg, "commands");
			for (Type type : commands) {
				type.getSchematic().loadResource(cFolder, type.getName() + "Command.java", info);
			}
		}
		Set<ListenerType> listeners = manager.getListeners();
		if (!listeners.isEmpty()) {
			File lFolder = makeFolder(pkg, "listeners");
			for (Type type : listeners) {
				type.getSchematic().loadResource(lFolder, type.getName() + "Listener.java", info);
			}
		}
		JOptionPane.showMessageDialog(null, "Finished Creation");
		Desktop.getDesktop().open(output);
		ui.getFrame().dispose();
	}

	public File makeFolder(File parent, String name) {
		List<File> toCreate = new ArrayList<>();
		File latest = parent;
		while (!latest.exists()) {
			toCreate.add(0, latest);
			latest = latest.getParentFile();
		}
		for (int i = 0; i < toCreate.size(); i++) {
			toCreate.get(i).mkdirs();
		}
		File file = new File(parent + File.separator + name);
		if (!file.getParentFile().exists()) {
			makeFolder(file.getParentFile().getParentFile(), file.getParentFile().getName());
		}
		file.mkdirs();
		return file;
	}

	public void loadSchematic(File parent, Schematic schem, String name, ProjectInfo info) {
		schem.loadResource(parent, name, info);
	}

}
