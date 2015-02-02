package com.scheidt.slots.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;


import com.badlogic.gdx.utils.Json;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class SerializeUtil {

	private static Logger LOG = Logger.getLogger("com.hwslots");

	public static void saveObjectToDisk(Object obj, String filename) throws Exception {
		LOG.info("Saving serialized data to disk...");
		FileOutputStream f_out;

		// Write to disk with FileOutputStream
		f_out = new FileOutputStream(filename);

		// Write object with ObjectOutputStream
		ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

		// Write object out to disk
		obj_out.writeObject(obj);

		obj_out.close();
		
		
		/* json test */
		LOG.info("Writing json for debug purposes also - json_" + filename);
		Json json = new Json();
		String jsonStr = json.prettyPrint(obj);
		//FileUtils.writeStringToFile(new File("json_" + filename), jsonStr);
		Files.write(jsonStr, new File("json_" + filename), Charsets.UTF_8);
		
		LOG.info("Saved.");
	}

	public static Object loadObjectFromDisk(String file) throws Exception {
		File f = new File(file);
		if (f.isFile()) {
			LOG.info("Loading serialized data from disk...");
			// Read from disk using FileInputStream
			FileInputStream f_in = new FileInputStream(file);

			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream(f_in);

			// Read an object
			Object obj = obj_in.readObject();
			obj_in.close();
			LOG.info("Loaded.");
			
			return obj;
		} else {
			LOG.info("> No data file found, " + file);
			return null;
		}
	}
	
	

}
