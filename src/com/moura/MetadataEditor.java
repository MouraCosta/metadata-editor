package com.moura;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

public class MetadataEditor {

	private File file;
	private ProcessBuilder pb;
	private Map<File, Map<String, String>> memoisationTable = new HashMap<>();

	public MetadataEditor(File file) throws Exception {
		if (! file.exists()) {
			throw new Exception("Put only files that exists");
		} else {
			this.file = file;
		}
	}

	/**
	 * Sets a new file to editor.
	 * @return True whether the file given exists, otherwise false.
	 */
	public boolean setFile(File file) {
		if (file.exists()) {
			this.file = file;
			return true;
		}
		return false;
	}

	/**
	 * Retrieve the metadata from a given file.
	 * @return A map containing the metadata of the file.
	 */
	public Map<String, String> getMetadata() {
		Map<String, String> metadata = memoisationTable.get(file);
		if (metadata != null) {
			return metadata;
		} else {
			pb = new ProcessBuilder("exiftool", file.getAbsolutePath());
			Process p;
			byte[] outputBytes;
			try {
				p = pb.start();
				outputBytes = p.getInputStream().readAllBytes();
			} catch (Exception err) {
				return null;
			}
			String output = new String(outputBytes);

			// Parse all the stuff
			String[] keyValues = output.split("\n");
			metadata = new HashMap<>();
			for (String keyValue: keyValues) {
				String[] current = keyValue.split(":");
				// Strip the keys and values
				current[0] = current[0].strip();
				current[1] = current[1].strip();
				metadata.put(current[0], current[1]);
			}
			memoisationTable.put(file, metadata);
			return metadata;
		}
	}
}