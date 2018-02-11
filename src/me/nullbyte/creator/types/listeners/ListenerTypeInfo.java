package me.nullbyte.creator.types.listeners;

import me.nullbyte.creator.types.TypeInfo;

public class ListenerTypeInfo implements TypeInfo {

	private final String name;

	public ListenerTypeInfo(String name) {
		this.name = name;
	}

	@Override
	public String fix(String text) {
		return text.replace("#{listener-name}", name);
	}

}
