package me.nullbyte.creator.types.commands;

import me.nullbyte.creator.types.TypeInfo;

public class CommandTypeInfo implements TypeInfo {

	private final CommandType type;

	public CommandTypeInfo(CommandType type) {
		this.type = type;
	}

	@Override
	public String fix(String text) {
		StringBuilder extra = new StringBuilder("super(\"" + type.getCmd().toLowerCase() + "\");");
		if (type.getPerm() != null) {
			extra.append("\n		permission(\"" + type.getPerm() + "\");");
		}
		if (type.getUsage() != null) {
			extra.append("\n		usage(\"" + type.getUsage() + "\");");
		}
		if (type.getArgs() > 0) {
			extra.append("\n		args(" + type.getArgs() + ");");
		}
		if (!type.isConsole()) {
			extra.append("\n		console(false);");
		}
		text = text.replace("#{extras}", extra.toString());
		return text.replace("#{command-name}", type.getCmd());
	}

}
