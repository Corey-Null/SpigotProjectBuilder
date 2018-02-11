package me.nullbyte.creator;

public class ProjectInfo {

	private String pkg;
	private String name;
	private String description;

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getPkg() {
		return pkg;
	}

	public void description(String description) {
		this.description = description;
	}

	public void name(String name) {
		this.name = name;
	}

	public void pkg(String pkg) {
		this.pkg = pkg;
	}

}
