package me.nullbyte.creator.types;

import me.nullbyte.creator.ProjectInfo;
import me.nullbyte.creator.Schematic;

public class TypeSchematic extends Schematic {

	private final TypeInfo info;

	public TypeSchematic(String resource, TypeInfo info) {
		super(resource);
		this.info = info;
	}

	@Override
	public String getText(ProjectInfo info) {
		return this.info.fix(super.getText(info));
	}

}
