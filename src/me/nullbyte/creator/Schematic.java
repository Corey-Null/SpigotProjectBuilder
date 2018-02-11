package me.nullbyte.creator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class Schematic {

	private final String resourcePath;

	public Schematic(String resourcePath) {
		this.resourcePath = "schems/" + resourcePath.replace('\\', '/');
	}

	public void loadResource(File outFolder, String fileName, ProjectInfo info) {
		String text = getText(info);
		if (text == null) {
			return;
		}
		File outFile = new File(outFolder, fileName);
		try {
			OutputStream out = new FileOutputStream(outFile);
			out.write(text.getBytes());
			out.close();
		} catch (IOException ex) {
		}
	}

	public String getText(ProjectInfo info) {
		if (resourcePath == null || resourcePath.equals("")) {
			throw new IllegalArgumentException("ResourcePath cannot be null or empty");
		}
		InputStream in = getResource(resourcePath);
		if (in == null) {
			throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found");
		}
		String text;
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
			text = buffer.lines().collect(Collectors.joining("\n"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		text = text.replace("#{package}", info.getPkg());
		text = text.replace("#{name}", info.getName());
		text = text.replace("#{name-lower}", info.getName().toLowerCase());
		text = text.replace("#{description}", info.getDescription());
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	public InputStream getResource(String filename) {
		if (filename == null) {
			throw new IllegalArgumentException("Filename cannot be null");
		}
		try {
			URL url = getClass().getClassLoader().getResource(filename);
			if (url == null) {
				return null;
			}
			URLConnection connection = url.openConnection();
			connection.setUseCaches(false);
			return connection.getInputStream();
		} catch (IOException ex) {
			return null;
		}
	}

}
