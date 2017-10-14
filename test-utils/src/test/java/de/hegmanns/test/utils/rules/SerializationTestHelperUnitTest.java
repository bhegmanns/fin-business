package de.hegmanns.test.utils.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectOutput;

import static de.hegmanns.test.utils.SerializationTestHelper.SerialationTestResult.ERROR_INVALID_CLASS;
import static de.hegmanns.test.utils.SerializationTestHelper.SerialationTestResult.ERROR_IO;
import static de.hegmanns.test.utils.SerializationTestHelper.SerialationTestResult.ERROR_NOT_SERIALIZABLE;
import static de.hegmanns.test.utils.SerializationTestHelper.SerialationTestResult.OK;
import static de.hegmanns.test.utils.SerializationTestHelper.serializeIntoStream;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

/**
 * Created by cwancowicz on 10/13/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SerializationTestHelperUnitTest {

    @Mock
    private ObjectOutput objectOutput;

    @Test
    public void shouldWriteObjectWhenSerializeIntoStream() throws IOException {
        Object object = new Object();
        serializeIntoStream(object, objectOutput);

        verify(objectOutput).writeObject(object);
    }

    @Test
    public void shouldReturnOkWhenSerializeIntoStreamSuccessfully() {
        assertEquals(OK, serializeIntoStream(new Object(), objectOutput));
    }

    @Test
    public void shouldReturnInvalidClassWhenSerializingIntoStream() throws IOException {
        doThrow(new InvalidClassException("testing")).when(objectOutput).writeObject(any());

        assertEquals(ERROR_INVALID_CLASS, serializeIntoStream(new Object(), objectOutput));
    }

    @Test
    public void shouldReturnNotSerializableWhenSerializingIntoStream() throws IOException {
        doThrow(new NotSerializableException("testing")).when(objectOutput).writeObject(any());

        assertEquals(ERROR_NOT_SERIALIZABLE, serializeIntoStream(new Object(), objectOutput));
    }

    @Test
    public void shouldReturnIOErrorWhenSerializingIntoStream() throws IOException {
        doThrow(new IOException("testing")).when(objectOutput).writeObject(any());

        assertEquals(ERROR_IO, serializeIntoStream(new Object(), objectOutput));
    }
}
