package de.hegmanns.core.utils.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Matchers.any;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;
import org.mockito.Mockito;

import de.hegmanns.core.utils.RemotableOptional;
import de.hegmanns.core.utils.helper.NotSerializableClazz;
import de.hegmanns.core.utils.helper.SerializableClazz;
import de.hegmanns.test.utils.SerializationTestHelper;
import de.hegmanns.test.utils.SerializationTestHelper.SerialationTestResult;

public class RemotableOptionalUnitTest {
	

	@Test
	public void coreOptionalNotSerializable() throws IOException{
		Optional<BigDecimal> bigDecimalOptional = Optional.of(BigDecimal.TEN);
		SerialationTestResult result = SerializationTestHelper.checkSerialize(bigDecimalOptional);
		
		assertThat(result, is(SerialationTestResult.ERROR_NOT_SERIALIZABLE));
	}
	
	@Test
	public void remotableOptionalIsSerializable() throws IOException{
		RemotableOptional<BigDecimal> bigDecimalOptional = RemotableOptional.of(BigDecimal.TEN);
		SerialationTestResult result = SerializationTestHelper.checkSerialize(bigDecimalOptional);
		
		assertThat(result, is(SerialationTestResult.OK));
	}
	
	@Test
	public void isPresentForEmpty(){
		assertThat(RemotableOptional.empty().isPresent(), is(false));
	}
	
	@Test
	public void isPresentForNotEmpty(){
		assertThat(RemotableOptional.of(BigDecimal.TEN).isPresent(), is(true));
	}
	
	@Test
	public void ofNullableWithNull(){
		assertThat(RemotableOptional.ofNullable(null).isPresent(), is(false));
	}
	
	@Test
	public void ofNullableWithNotNull(){
		BigDecimal bigDecimal = BigDecimal.TEN;
		RemotableOptional<BigDecimal> remotableOptional = RemotableOptional.ofNullable(bigDecimal);
		
		assertThat(remotableOptional.isPresent(), is(true));
		assertThat(remotableOptional.get(), sameInstance(bigDecimal));
	}
	
	@Test
	public void remotableOptionalWithNotSerializable() throws IOException{
		NotSerializableClazz notSerializableClazz = new NotSerializableClazz();
		notSerializableClazz.setValue(100);
		RemotableOptional<NotSerializableClazz> remotableOptional = RemotableOptional.of(notSerializableClazz);
		SerialationTestResult result = SerializationTestHelper.checkSerialize(remotableOptional);
		
		assertThat(result, is(SerialationTestResult.ERROR_NOT_SERIALIZABLE));
	}
	
	@Test
	public void remotableOptionalWithSerializable() throws IOException{
		SerializableClazz serializableClazz = new SerializableClazz();
		serializableClazz.setValue(100);
		RemotableOptional<SerializableClazz> remotableOptional = RemotableOptional.of(serializableClazz);		
		SerialationTestResult result = SerializationTestHelper.checkSerialize(remotableOptional);
		
		assertThat(result, is(SerialationTestResult.OK));
	}
	
	@Test
	public void hashCodeForEmptyReturns0(){
		RemotableOptional<Object> empty = RemotableOptional.empty();
		assertThat(empty.hashCode(), is(0));
	}
	
	@Test
	public void hashCodeReturnsHashCodeFromInstance(){
		BigDecimal anyInstance = new BigDecimal(1000);
		BigDecimal spyAnyInstance = Mockito.spy(anyInstance);
		
		RemotableOptional<BigDecimal> remotableOptional = RemotableOptional.of(spyAnyInstance);
		
		remotableOptional.hashCode();
		Mockito.verify(spyAnyInstance, Mockito.times(1)).hashCode();
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getForEmpty(){
		RemotableOptional.empty().get();
	}
	
	@Test
	public void orElseNotEmpty(){
		BigDecimal instance = BigDecimal.TEN;
		
		RemotableOptional<BigDecimal> remotableOptional = RemotableOptional.of(instance);
		assertThat(remotableOptional.orElse(BigDecimal.ONE), sameInstance(instance));
	}
	
	@Test
	public void orElseEmpty(){
		BigDecimal otherInstance = BigDecimal.ONE;
		assertThat(RemotableOptional.empty().orElse(otherInstance), sameInstance(otherInstance));
	}
	
	@Test
	public void ifPresentWithEmpty(){
		@SuppressWarnings("unchecked")
		Consumer<Object> consumer = Mockito.mock(Consumer.class);
		
		RemotableOptional<Object> remotableOptional = RemotableOptional.empty();
		remotableOptional.ifPresent(consumer);
		
		Mockito.verify(consumer, Mockito.never()).accept(Mockito.any());
	}
	
	@Test
	public void ifPresentWithNotEmpty(){
		BigDecimal instance = BigDecimal.TEN;
		@SuppressWarnings("unchecked")
		Consumer<BigDecimal> consumer = Mockito.mock(Consumer.class);
		
		RemotableOptional<BigDecimal> remotableOptional = RemotableOptional.of(instance);
		remotableOptional.ifPresent(consumer);
		Mockito.verify(consumer, Mockito.times(1)).accept(instance);
	}
	
	@Test
	public void orElseGetWithEmpty(){
		@SuppressWarnings("unchecked")
		Supplier<Object> supplier = Mockito.mock(Supplier.class);
		
		RemotableOptional<Object> remotableOptional = RemotableOptional.empty();
		remotableOptional.orElseGet(supplier);
		Mockito.verify(supplier, Mockito.times(1)).get();
	}
	
	@Test
	public void orElseGetWithNotEmpty(){
		BigDecimal instance = BigDecimal.TEN;
		@SuppressWarnings("unchecked")
		Supplier<BigDecimal> supplier = Mockito.mock(Supplier.class);
		
		RemotableOptional<BigDecimal> remotableOptional = RemotableOptional.of(instance);
		remotableOptional.orElseGet(supplier);
		Mockito.verify(supplier, Mockito.never()).get();
	}
	
	@Test(expected = Throwable.class)
	public void orElseThrowWithEmpty() throws Throwable{
		Supplier<Throwable> supplier = () -> new Throwable();
		
		RemotableOptional<Object> remotableOptional = RemotableOptional.empty();
		remotableOptional.orElseThrow(supplier);
	}
	
	@Test
	public void orElseThrowWithNotEmpty() throws Throwable{
		BigDecimal instance = BigDecimal.TEN;
		@SuppressWarnings("unchecked")
		Supplier<Throwable> supplier = Mockito.mock(Supplier.class);
		
		RemotableOptional<BigDecimal> remotableOptional = RemotableOptional.of(instance);
		remotableOptional.orElseThrow(supplier);
		Mockito.verify(supplier, Mockito.never()).get();
	}
	
	@Test(expected = NullPointerException.class)
	public void mapWithNullMapper(){
		RemotableOptional.of(BigDecimal.TEN).map(null);
	}
	
	@Test
	public void mapWithNotPresent(){
		@SuppressWarnings("unchecked")
		Function<BigDecimal, Object> mapper = Mockito.mock(Function.class);
		
		RemotableOptional<BigDecimal> remotableOptional = RemotableOptional.empty();
		remotableOptional.map(mapper);
		
		Mockito.verify(mapper, Mockito.never()).apply(any());
	}
	
	@Test
	public void mapWithPresent(){
		@SuppressWarnings("unchecked")
		Function<BigDecimal, Object> mapper = Mockito.mock(Function.class);
		
		RemotableOptional<BigDecimal> remotableOptional = RemotableOptional.of(BigDecimal.TEN);
		remotableOptional.map(mapper);
		Mockito.verify(mapper, Mockito.times(1)).apply(Mockito.eq(BigDecimal.TEN));
	}
	
	@Test
	public void toStringWithEmpty(){
		RemotableOptional<Object> remotableOptional = RemotableOptional.empty();
		assertThat(remotableOptional.toString(), comparesEqualTo("RemoteableOptional.empty"));
	}
	
	@Test
	public void toStringWithPresent(){
		RemotableOptional<BigDecimal> remotableOptional = RemotableOptional.of(BigDecimal.TEN);
		String toStringResult = remotableOptional.toString();
		
		assertThat(toStringResult, comparesEqualTo("RemoteableOptional[10]"));
	}
	
	@Test(expected = NullPointerException.class)
	public void flatMapWithNullMapper(){
		RemotableOptional.of(BigDecimal.TEN).flatMap(null);
	}
	
	@Test
	public void flatMapWithEmpty(){
		@SuppressWarnings("unchecked")
		Function<BigDecimal, RemotableOptional<Object>> mapper = Mockito.mock(Function.class);
		RemotableOptional.<BigDecimal>empty().flatMap(mapper);
		Mockito.verify(mapper, Mockito.never()).apply(any());
	}
	
	@Test
	public void flatMapWithPresent(){
		@SuppressWarnings("unchecked")
		Function<BigDecimal, RemotableOptional<Object>> mapper = Mockito.mock(Function.class);
		Mockito.when(mapper.apply(any())).thenReturn(RemotableOptional.of(new Object()));
		RemotableOptional.of(BigDecimal.TEN).flatMap(mapper);
		Mockito.verify(mapper, Mockito.times(1)).apply(any());
	}
	
	@Test(expected = NullPointerException.class)
	public void filterWithNullPredicate(){
		RemotableOptional.of(BigDecimal.TEN).filter(null);
	}
	
	@Test
	public void filterWithEmpty(){
		@SuppressWarnings("unchecked")
		Predicate<Object> predicate = Mockito.mock(Predicate.class);
		RemotableOptional.<Object>empty().filter(predicate);
		
		Mockito.verify(predicate, Mockito.never()).test(Mockito.any());
	}
	
	@Test
	public void filterWithPresentTrue(){
		Object value = new Object();
		@SuppressWarnings("unchecked")
		Predicate<Object> predicate = Mockito.mock(Predicate.class);
		Mockito.when(predicate.test(Mockito.any())).thenReturn(true);
		RemotableOptional<Object> result = RemotableOptional.<Object>of(value).filter(predicate);
		
		assertThat(result.get(), sameInstance(value));
	}
	
	@Test
	public void filterWithPresentFalse(){
		Object value = new Object();
		@SuppressWarnings("unchecked")
		Predicate<Object> predicate = Mockito.mock(Predicate.class);
		Mockito.when(predicate.test(Mockito.any())).thenReturn(false);
		RemotableOptional<Object> result = RemotableOptional.<Object>of(value).filter(predicate);
		
		assertThat(result.isPresent(), is(false));
	}
	
	@Test
	public void equalsSameInstances(){
		RemotableOptional<BigDecimal> remotableOptional = RemotableOptional.of(BigDecimal.TEN);
		
		assertThat(remotableOptional.equals(remotableOptional), is(true));
	}
	
	@Test
	public void equalsNotSameInstanceOf(){
		RemotableOptional<BigDecimal> remotableOptional = RemotableOptional.of(BigDecimal.TEN);
		BigDecimal bigDecimal = BigDecimal.TEN;
		
		assertThat(remotableOptional.equals(bigDecimal), is(false));
	}
	
	@Test
	public void equalsSameValues(){
		RemotableOptional<BigDecimal> remotableOptionalFirst = RemotableOptional.of(BigDecimal.TEN);
		RemotableOptional<BigDecimal> remotableOptionalSecond = RemotableOptional.of(BigDecimal.TEN);
		
		assertThat(remotableOptionalFirst.equals(remotableOptionalSecond), is(true));
	}
	
	@Test
	public void equalsNotSameValues(){
		RemotableOptional<BigDecimal> remotableOptionalFirst = RemotableOptional.of(BigDecimal.TEN);
		RemotableOptional<BigDecimal> remotableOptionalSecond = RemotableOptional.of(BigDecimal.ONE);
		
		assertThat(remotableOptionalFirst.equals(remotableOptionalSecond), is(false));
	}
}
