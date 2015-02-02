package com.scheidt.slots.api;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileUtil {
	
	@SuppressWarnings("unchecked")
	public static List<String> getListFromFile(File file) throws Exception {
		return FileUtils.readLines(file, "UTF-8");
	}

}
