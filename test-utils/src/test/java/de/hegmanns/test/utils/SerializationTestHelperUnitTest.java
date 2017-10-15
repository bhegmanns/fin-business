package de.hegmanns.test.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static de.hegmanns.test.utils.SerializationTestHelper.SerialationTestResult.*;
import static de.hegmanns.test.utils.SerializationTestHelper.serializeIntoStream;
import static org.junit.Assert.assertEquals;

public class SerializationTestHelperUnitTest {

	private ObjectOutputStream objectOutputStream = null;
	private File file = null;

	@Before
	public void setup() throws Exception {
		file = File.createTempFile("test", ".tmp");
		file.deleteOnExit();

		objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
	}

	@After
	public void cleanup() throws Exception {
		if (objectOutputStream != null) {
			objectOutputStream.close();
		}
	}

	@Test
	public void testSerializeNotSerializable() {
		assertEquals(serializeIntoStream(new Object(), objectOutputStream), ERROR_NOT_SERIALIZABLE);
	}

	@Test
	public void testSerializeToStream() throws Exception {
		SomeSerializableObject serializable = new SomeSerializableObject();

		assertEquals(serializeIntoStream(serializable, objectOutputStream), OK);

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		SomeSerializableObject deserialized = (SomeSerializableObject) ois.readObject();

		assertEquals(serializable, deserialized);
	}
}

class SomeSerializableObject implements Serializable {
	private String stringField;
	private int intField;

	SomeSerializableObject() {
		stringField = "someValue";
		intField = 123;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		SomeSerializableObject that = (SomeSerializableObject) o;

		if (intField != that.intField)
			return false;
		return stringField != null ? stringField.equals(that.stringField) : that.stringField == null;
	}

	@Override
	public int hashCode() {
		int result = stringField != null ? stringField.hashCode() : 0;
		result = 31 * result + intField;
		return result;
	}
}
