package com.moura.metadataeditor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Model responsible for creating exiftool processes to change or read metadata.
 * 
 * @author de Moura
 */
public class MetadataEditor {

	private static List<String> commonKeys = Arrays.asList(
			"ExifTool Version Number", "File Inode Change Date/Time", "File Type",
			"File Modification Date/Time", "MIME Type", "File Size", "File Permissions",
			"Directory", "File Name", "File Access Date/Time");

	private static Logger logger = Logger.getLogger(MetadataEditor.class.getName());
	private static Map<File, Map<String, String>> memoisationTable = new HashMap<>();

	/**
	 * Retrieve the metadata from a given file.
	 * 
	 * @return A map containing the metadata of the file. The result is null if
	 *         the file doesn't exist.
	 */
	public static Map<String, String> getMetadata(File file) {
		if (file.exists()) {
			// Start the process of getting metadata.
			Map<String, String> metadata = memoisationTable.get(file);
			if (metadata != null) {
				return metadata;
			} else {
				// File isn't in the memoisation table. Query the metadata.
				metadata = new HashMap<>();
				String output = getOutput(file);
				if (output == null) {
					return metadata;
				}
				String[] fieldsAndValues = output.split("\n");
				for (String fieldAndValue : fieldsAndValues) {
					String[] current = fieldAndValue.split(":");
					current[0] = current[0].strip();
					current[1] = current[1].strip();
					metadata.put(current[0], current[1]);
				}
				memoisationTable.put(file, metadata);
				return metadata;
			}
		} else {
			// The file doesn't exist, so just return null.
			return null;
		}
	}

	public static Map<String, String> getNeededMetadata(File file) {
		Map<String, String> fileMetadata = getMetadata(file);
		for (String commonKey : commonKeys) {
			fileMetadata.remove(commonKey);
		}

		return fileMetadata;
	}

	/**
	 * Sets a new metadata to the file.
	 * 
	 * @param newMetadata A map containing all the metadata fields that needs to
	 *                    be changed.
	 * @return True when the metadata is successfully set, otherwise false.
	 */
	public static boolean setMetadata(File file, Map<String, String> newMetadata) {
		if (!file.exists()) {
			return false;
		}

		List<String> command = new ArrayList<>();
		command.add("exiftool");
		Set<String> keys = newMetadata.keySet();
		for (String key : keys) {
			command.add(createTag(key.replaceAll(" ", ""), newMetadata.get(key)));
		}
		command.add(file.getAbsolutePath());
		logger.info(command.toString());

		try {
			new ProcessBuilder(command).start();
		} catch (IOException err) {
			logger.warning(err.getMessage());
			return false;
		}
		return true;
	}

	private static String createTag(String tagName, String tagValue) {
		return String.format("-%s=%s", tagName, tagValue);
	}

	private static String getOutput(File file) {
		try {
			Process p = new ProcessBuilder("exiftool", file.getAbsolutePath()).start();
			String output = new String(p.getInputStream().readAllBytes());
			return output;
		} catch (IOException err) {
			return null;
		}
	}
}