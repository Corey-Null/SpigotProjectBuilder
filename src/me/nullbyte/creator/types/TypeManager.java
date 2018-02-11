package me.nullbyte.creator.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import me.nullbyte.creator.types.commands.CommandType;
import me.nullbyte.creator.types.listeners.ListenerType;

public class TypeManager {

	private static Comparator<Type> ALPHABETICAL_ORDER = new Comparator<Type>() {
		public int compare(Type o1, Type o2) {
			String str1 = o1.getName();
			String str2 = o2.getName();
			int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
			if (res == 0) {
				res = str1.compareTo(str2);
			}
			return res;
		}
	};
	private final List<Type> typeList;

	public TypeManager() {
		this.typeList = new ArrayList<>();
	}

	public List<Type> getTypeList() {
		return Collections.unmodifiableList(typeList);
	}
	
	public Set<CommandType> getCommands() {
		Set<CommandType> commands = new TreeSet<>(ALPHABETICAL_ORDER);
		for (Type type : typeList) {
			if (type instanceof CommandType) {
				commands.add((CommandType) type);
			}
		}
		return commands;
	}

	public Set<ListenerType> getListeners() {
		Set<ListenerType> listeners = new TreeSet<>(ALPHABETICAL_ORDER);
		for (Type type : typeList) {
			if (type instanceof ListenerType) {
				listeners.add((ListenerType) type);
			}
		}
		return listeners;
	}

	public void addType(Type type) {
		for (Type t : typeList) {
			if (t.getClass().isInstance(type) && type.getName().equalsIgnoreCase(t.getName())) {
				return;
			}
		}
		typeList.add(type);
	}

	public Type getIndex(int index) {
		if (index >= typeList.size() || index < 0) {
			return null;
		}
		return typeList.get(index);
	}

	public void removeType(int index) {
		if (index >= typeList.size() || index < 0) {
			return;
		}
		typeList.remove(index);
	}

}
