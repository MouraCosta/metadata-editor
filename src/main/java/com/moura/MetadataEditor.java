package com.moura;

import java.io.File;
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

	private final List<String> commonKeys = Arrays.asList(
			"ExifTool Version Number", "File Inode Change Date/Time", "File Type",
			"File Modification Date/Time", "MIME Type", "File Size", "File Permissions", "Directory",
			"File Name", "File Access Date/Time");

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Map<File, Map<String, String>> memoisationTable = new HashMap<>();

	/**
	 * Retrieve the metadata from a given file.
	 * 
	 * @return A map containing the metadata of the file. The result is null if
	 *         the file doesn't exist.
	 */
	// FIXME: Exception is too general.
	public Map<String, String> getMetadata(File file) throws Exception {
		if (file.exists()) {
			// Start the process of getting metadata.
			Map<String, String> metadata = memoisationTable.get(file);
			if (metadata != null) {
				return metadata;
			} else {
				// File isn't in the memoisation table. Query the metadata.
				String output = getOutput(file);
				String[] fieldsAndValues = output.split("\n");
				metadata = new HashMap<>();
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

	public Map<String, String> getNeededMetadata(File file) throws Exception {
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
	 * @return True when the metadata
	 */
	// FIXME: Exception is too general.
	public boolean setMetadata(File file, Map<String, String> newMetadata) throws Exception {
		// TODO: Test this code and reinforce it, too much atomic
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
		} catch (Exception err) {
			err.printStackTrace();
		}
		return true;
	}

	private String createTag(String tagName, String tagValue) {
		return String.format("-%s=%s", tagName, tagValue);
	}

	private String getOutput(File file) {
		try {
			Process p = new ProcessBuilder("exiftool", file.getAbsolutePath()).start();
			String output = new String(p.getInputStream().readAllBytes());
			return output;
		} catch (Exception err) {
			return null;
		}
	}
}