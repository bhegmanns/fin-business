package de.hegmanns.test.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Simple helper class to serialize an object into a stream and returns if serialization succeeded.
 * If not, there are some couple of reasons.
 * 
 * @author B. Hegmanns
 */
public class SerializationTestHelper {
	
	/**
	 * Result for serialization.
	 * 
	 * @author B. Hegmanns
	 */
	public static enum SerialationTestResult{
		/** successful serialization */
		OK, 
		
		/** object isn't serializable (interface isn't implemented) */
		ERROR_NOT_SERIALIZABLE, 
		
		/** IO error, object cannot write for example*/
		ERROR_IO, 
		
		/** all error from {@link InvalidClassException} */
		ERROR_INVALID_CLASS, 
		
		/** other technical error */
		ERROR_TECHNICAL};

	/**
	 * Serializes an instance to the currently open {@link ObjectOutputStream}.
	 * Note, that the instance/class have to implement {@link Serializable}-interface.
	 * 
	 * @param instance the (serializable) instance
	 * @param outputStream the output stream where the object would be written
	 * @return {@link SerialationTestResult#OK} is it works other see {@link SerialationTestResult}
	 */
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
	
	/**
	 * Checks if the instance should be serialized.
	 * 
	 * @param instance the instance
	 * @return {@link SerialationTestResult#OK} if works, other see {#link {@link SerialationTestResult}
	 */
	public static SerialationTestResult checkSerialize(Object instance){
		try(ObjectOutputStream outputStream = new ObjectOutputStream(new ByteArrayOutputStream())){
			return serializeIntoStream(instance, outputStream);
		}
		catch(IOException e){
			return SerialationTestResult.ERROR_TECHNICAL;
		}
	}
}
