package me.nullbyte.creator.types.commands;

import me.nullbyte.creator.Schematic;
import me.nullbyte.creator.types.Type;
import me.nullbyte.creator.types.TypeInfo;
import me.nullbyte.creator.types.TypeSchematic;

public class CommandType implements Type {

	private final String cmd;
	private String perm;
	private int args;
	private String usage;
	private boolean console;

	public CommandType(String cmd) {
		this.cmd = cmd;
	}

	public void permission(String perm) {
		this.perm = perm;
	}

	public void usage(String usage) {
		this.usage = usage;
	}

	public void args(int args) {
		this.args = args;
	}

	public void console(boolean console) {
		this.console = console;
	}

	public int getArgs() {
		return args;
	}

	public String getCmd() {
		return cmd;
	}

	public String getPerm() {
		return perm;
	}

	public String getUsage() {
		return usage;
	}

	public boolean isConsole() {
		return console;
	}

	@Override
	public TypeInfo loadInfo() {
		return new CommandTypeInfo(this);
	}

	@Override
	public Schematic getSchematic() {
		return new TypeSchematic("types/command-schematic", loadInfo());
	}

	@Override
	public String getName() {
		return cmd;
	}

}
