package me.nullbyte.creator.types;

import me.nullbyte.creator.Schematic;

public interface Type {

	TypeInfo loadInfo();

	Schematic getSchematic();

	String getName();

}
