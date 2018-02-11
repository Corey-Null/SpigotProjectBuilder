package me.nullbyte.creator.types.listeners;

import me.nullbyte.creator.Schematic;
import me.nullbyte.creator.types.Type;
import me.nullbyte.creator.types.TypeInfo;
import me.nullbyte.creator.types.TypeSchematic;

public class ListenerType implements Type {

	private final String name;

	public ListenerType(String name) {
		this.name = name;
	}

	@Override
	public TypeInfo loadInfo() {
		return new ListenerTypeInfo(name);
	}

	@Override
	public Schematic getSchematic() {
		return new TypeSchematic("types/listener-schematic", loadInfo());
	}

	@Override
	public String getName() {
		return name;
	}

}
