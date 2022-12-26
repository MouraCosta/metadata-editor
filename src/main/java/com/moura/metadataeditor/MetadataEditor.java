package com.moura.metadataeditor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class that works as model for reading and editing files' metadata.
 * 
 * @author de Moura
 */
public class MetadataEditor {

	private static List<String> commonKeys = Arrays.asList(
			"ExifTool Version Number", "File Inode Change Date/Time", "File Type",
			"File Modification Date/Time", "MIME Type", "File Size", "File Permissions",
			"Directory", "File Name", "File Access Date/Time");

	private static Logger logger = Logger.getLogger(MetadataEditor.class.getName());

	/**
	 * It retrieves the file metadata using the system installed ExifTool.
	 * 
	 * @return A map containing the metadata of the file. The result is null if
	 *         the file doesn't exist. Also, it may return an empty map if
	 *         ExifTool isn't present in the host's system.
	 * @throws IOException
	 */
	public static Map<String, String> getMetadata(File file) {
		if (file.exists()) {
			// Start the process of getting metadata.
			Map<String, String> metadata = new HashMap<>();
			String output = getOutput(file);
			if (output == null) {
				// Returns an empty hash map if exiftool isn't installed.
				return metadata;
			}
			String[] fieldsAndValues = output.split("\n");
			for (String fieldAndValue : fieldsAndValues) {
				String[] current = fieldAndValue.split(":");
				current[0] = current[0].strip();
				current[1] = current[1].strip();
				metadata.put(current[0], current[1]);
			}
			return metadata;
		} else {
			// The file doesn't exist, so just return null.
			return null;
		}
	}

	/**
	 * It fetchs a collection of metadata fields that can be safely edited by
	 * the user.
	 * 
	 * @param file File to be read
	 * @return Map of metadata fields
	 */
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
	 * @param newMetadata Map containing all the metadata fields that needs to
	 *                    be changed
	 * @return True when the metadata is successfully set, otherwise false
	 */
	public static boolean setMetadata(File file, Map<String, String> newMetadata) {
		if (!file.exists()) {
			return false;
		}

		List<String> command = new ArrayList<>();
		command.add("exiftool");
		newMetadata.forEach((key, value) -> {
			if (value != "" && !value.equals(getMetadata(file).get(key))) {
				command.add(createTag(key.replaceAll(" ", ""), value));
			}
		});
		command.add(file.getAbsolutePath());
		logger.info(command.toString());

		try {
			Process proc = new ProcessBuilder(command).start();
			proc.waitFor();
			if (proc.exitValue() == 1) {
				return false;
			}
		} catch (IOException | InterruptedException err) {
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