package de.hegmanns.core.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;

import de.hegmanns.core.utils.helper.NotSerializableClazz;
import de.hegmanns.core.utils.helper.SerializableClazz;
import de.hegmanns.test.utils.SerializationTestHelper;
import de.hegmanns.test.utils.SerializationTestHelper.SerialationTestResult;

public class ReferenceUnitTest {

	@Test
	public void dealsWithNotSerializableInstance(){
		NotSerializableClazz notSerializableInstance = new NotSerializableClazz();
		notSerializableInstance.setValue(100);
		
		Reference<NotSerializableClazz> ref = new Reference<NotSerializableClazz>(notSerializableInstance);
		
		assertThat(ref.get(), sameInstance(notSerializableInstance));
	}
	
	@Test
	public void dealsWithSerializableInstance(){
		SerializableClazz serializableInstance = new SerializableClazz();
		serializableInstance.setValue(100);
		
		Reference<SerializableClazz> ref = new Reference<SerializableClazz>(serializableInstance);
		
		assertThat(ref.get(), sameInstance(serializableInstance));
	}
	
	@Test
	public void notSerializableInstanceCannotSerialize(){
		NotSerializableClazz notSerializableInstance = new NotSerializableClazz();
		notSerializableInstance.setValue(100);
		
		Reference<NotSerializableClazz> ref = new Reference<NotSerializableClazz>(notSerializableInstance);
		
		assertThat(SerializationTestHelper.checkSerialize(ref), is(SerialationTestResult.ERROR_NOT_SERIALIZABLE));
	}
	
	@Test
	public void serializableInstanceCanSerialize(){
		SerializableClazz serializableInstance = new SerializableClazz();
		serializableInstance.setValue(100);
		
		Reference<SerializableClazz> ref = new Reference<>(serializableInstance);
		
		assertThat(SerializationTestHelper.checkSerialize(ref), is(SerialationTestResult.OK));
	}
	
	@Test
	public void createGivesReference(){
		Reference<SerializableClazz> instance = Reference.create(new SerializableClazz());
		instance.get().setValue(100);
		
		assertThat(instance.get().getValue(), is(100));
	}
}
