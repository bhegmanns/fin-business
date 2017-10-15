package de.hegmanns.test.utils;

import org.junit.Test;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SerializationTestHelperTest {

    private IOExceptionStub ioExceptionStub = new IOExceptionStub();
    private InvalidClassExceptionStub invalidClassExceptionStub = new InvalidClassExceptionStub();
    private NotSerializableExceptionStub notSerializableExceptionStub = new NotSerializableExceptionStub();

    @Test
    public void checkSerialize_OK() throws Exception {

        Object instance = "Simple instance";

        SerializationTestHelper.SerializationTestResult result = SerializationTestHelper.checkSerialize(instance);

        assertThat(result, is(SerializationTestHelper.SerializationTestResult.OK));
    }

    @Test
    public void checkSerialize_ERROR_IO() throws Exception {

        Object instance = ioExceptionStub;

        SerializationTestHelper.SerializationTestResult result = SerializationTestHelper.checkSerialize(instance);

        assertThat(result, is(SerializationTestHelper.SerializationTestResult.ERROR_IO));
    }

    @Test
    public void checkSerialize_ERROR_INVALID_CLASS() throws Exception {

        Object instance = invalidClassExceptionStub;

        SerializationTestHelper.SerializationTestResult result = SerializationTestHelper.checkSerialize(instance);

        assertThat(result, is(SerializationTestHelper.SerializationTestResult.ERROR_INVALID_CLASS));
    }

    @Test
    public void checkSerialize_ERROR_NOT_SERIALIZABLE() throws Exception {

        Object instance = notSerializableExceptionStub;

        SerializationTestHelper.SerializationTestResult result = SerializationTestHelper.checkSerialize(instance);

        assertThat(result, is(SerializationTestHelper.SerializationTestResult.ERROR_NOT_SERIALIZABLE));
    }
}

// **
// ** Stub classes
// **
class InvalidClassExceptionStub implements Serializable {
    private void writeObject(ObjectOutputStream oos) throws IOException {
        throw new InvalidClassException("");
    }
}

class NotSerializableExceptionStub { }

class IOExceptionStub implements Serializable {
    private void writeObject(ObjectOutputStream oos) throws IOException {
        throw new IOException("");
    }
}





