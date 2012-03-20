package com.xebialabs.deployit.service.importer;

import com.google.common.io.Files;
import com.xebialabs.deployit.server.api.importer.ImportSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FileSource implements ImportSource {

	private final String location;
	private final boolean tempFile;

	public FileSource(String location, boolean tempFile) {
		this.location = location;
		this.tempFile = tempFile;
	}

	public FileSource(File archive, boolean tempFile) {
		this.location = archive.getPath();
		this.tempFile = tempFile;
	}

	public String getLocation() {
		return location;
	}

	public boolean isTempFile() {
		return tempFile;
	}

	@Override
	public File getFile() {
		return new File(location);
	}

	@SuppressWarnings("deprecation")
    @Override
	public void cleanUp() {
		if (isTempFile()) {
			try {
				Files.deleteRecursively(getFile());
			} catch (IOException e) {
				logger.error("Could not clean up temporary file {}", location);
			}
		}
	}

	@Override
	public String toString() {
		return "FileSource[" + location + "," + tempFile + "]";
	}

	private static final Logger logger = LoggerFactory.getLogger(FileSource.class);
}