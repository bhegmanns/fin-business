package de.hegmanns.test.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;

/**
 * Simple helper class to serialize an object into a stream and returns if serialization succeeded.
 * If not, there are some couple of reasons.
 * 
 * @author B. Hegmanns
 */
public class SerializationTestHelper {
	
	/**
	 * 
	 * @author B. Hegmanns
	 */
	public static enum SerialationTestResult{OK, ERROR_NOT_SERIALIZABLE, ERROR_IO, ERROR_INVALID_CLASS, ERROR_TECHNICAL};

	public static SerialationTestResult serializeIntoStream(Object instance, ObjectOutputStream outputStream){
		SerialationTestResult result = SerialationTestResult.OK;
		try {
			outputStream.writeObject(instance);
		} 
		catch(InvalidClassException e){
			result = SerialationTestResult.ERROR_INVALID_CLASS;
		}
		catch(NotSerializableException e){
			result = SerialationTestResult.ERROR_NOT_SERIALIZABLE;
		}
		catch (IOException e) {
			result = SerialationTestResult.ERROR_IO;
		} 
		
		return result;
	}
	
	public static SerialationTestResult checkSerialize(Object instance){
		try(ObjectOutputStream outputStream = new ObjectOutputStream(new ByteArrayOutputStream())){
			return serializeIntoStream(instance, outputStream);
		}
		catch(IOException e){
			return SerialationTestResult.ERROR_TECHNICAL;
		}
	}
}
