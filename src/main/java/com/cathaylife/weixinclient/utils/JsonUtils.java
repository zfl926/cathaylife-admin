package com.cathaylife.weixinclient.utils;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;

public class JsonUtils {
	
	/**
	 *  jaskson mapper model
	 */
	private static final  ObjectMapper mm = new ObjectMapper();
	
	
	/**
	 * @param json
	 * @return
	 */
	public static <OBJ> OBJ parseToObj(String json, Class<OBJ> clazz){
		ObjectReader reader = mm.reader(clazz);
		try {
			return reader.readValue(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * @param obj
	 * @return
	 */
	public static <OBJ> String parseToString(OBJ obj, Class<OBJ> clazz){
		ObjectWriter writer = mm.writerWithView(clazz);
		try {
			return writer.writeValueAsString(obj);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
