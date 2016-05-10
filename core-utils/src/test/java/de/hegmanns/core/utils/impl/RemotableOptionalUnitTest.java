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

import de.hegmanns.core.utils.RemoteableOptional;
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
		RemoteableOptional<BigDecimal> bigDecimalOptional = RemoteableOptional.of(BigDecimal.TEN);
		SerialationTestResult result = SerializationTestHelper.checkSerialize(bigDecimalOptional);
		
		assertThat(result, is(SerialationTestResult.OK));
	}
	
	@Test
	public void isPresentForEmpty(){
		assertThat(RemoteableOptional.empty().isPresent(), is(false));
	}
	
	@Test
	public void isPresentForNotEmpty(){
		assertThat(RemoteableOptional.of(BigDecimal.TEN).isPresent(), is(true));
	}
	
	@Test
	public void ofNullableWithNull(){
		assertThat(RemoteableOptional.ofNullable(null).isPresent(), is(false));
	}
	
	@Test
	public void ofNullableWithNotNull(){
		BigDecimal bigDecimal = BigDecimal.TEN;
		RemoteableOptional<BigDecimal> remoteableOptional = RemoteableOptional.ofNullable(bigDecimal);
		
		assertThat(remoteableOptional.isPresent(), is(true));
		assertThat(remoteableOptional.get(), sameInstance(bigDecimal));
	}
	
	@Test
	public void remotableOptionalWithNotSerializable() throws IOException{
		NotSerializableClazz notSerializableClazz = new NotSerializableClazz();
		notSerializableClazz.setValue(100);
		RemoteableOptional<NotSerializableClazz> remoteableOptional = RemoteableOptional.of(notSerializableClazz);
		SerialationTestResult result = SerializationTestHelper.checkSerialize(remoteableOptional);
		
		assertThat(result, is(SerialationTestResult.ERROR_NOT_SERIALIZABLE));
	}
	
	@Test
	public void remotableOptionalWithSerializable() throws IOException{
		SerializableClazz serializableClazz = new SerializableClazz();
		serializableClazz.setValue(100);
		RemoteableOptional<SerializableClazz> remoteableOptional = RemoteableOptional.of(serializableClazz);
		SerialationTestResult result = SerializationTestHelper.checkSerialize(remoteableOptional);
		
		assertThat(result, is(SerialationTestResult.OK));
	}
	
	@Test
	public void hashCodeForEmptyReturns0(){
		RemoteableOptional<Object> empty = RemoteableOptional.empty();
		assertThat(empty.hashCode(), is(0));
	}
	
	@Test
	public void hashCodeReturnsHashCodeFromInstance(){
		BigDecimal anyInstance = new BigDecimal(1000);
		BigDecimal spyAnyInstance = Mockito.spy(anyInstance);
		
		RemoteableOptional<BigDecimal> remoteableOptional = RemoteableOptional.of(spyAnyInstance);
		
		remoteableOptional.hashCode();
		Mockito.verify(spyAnyInstance, Mockito.times(1)).hashCode();
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getForEmpty(){
		RemoteableOptional.empty().get();
	}
	
	@Test
	public void orElseNotEmpty(){
		BigDecimal instance = BigDecimal.TEN;
		
		RemoteableOptional<BigDecimal> remoteableOptional = RemoteableOptional.of(instance);
		assertThat(remoteableOptional.orElse(BigDecimal.ONE), sameInstance(instance));
	}
	
	@Test
	public void orElseEmpty(){
		BigDecimal otherInstance = BigDecimal.ONE;
		assertThat(RemoteableOptional.empty().orElse(otherInstance), sameInstance(otherInstance));
	}
	
	@Test
	public void ifPresentWithEmpty(){
		@SuppressWarnings("unchecked")
		Consumer<Object> consumer = Mockito.mock(Consumer.class);
		
		RemoteableOptional<Object> remoteableOptional = RemoteableOptional.empty();
		remoteableOptional.ifPresent(consumer);
		
		Mockito.verify(consumer, Mockito.never()).accept(Mockito.any());
	}
	
	@Test
	public void ifPresentWithNotEmpty(){
		BigDecimal instance = BigDecimal.TEN;
		@SuppressWarnings("unchecked")
		Consumer<BigDecimal> consumer = Mockito.mock(Consumer.class);
		
		RemoteableOptional<BigDecimal> remoteableOptional = RemoteableOptional.of(instance);
		remoteableOptional.ifPresent(consumer);
		Mockito.verify(consumer, Mockito.times(1)).accept(instance);
	}
	
	@Test
	public void orElseGetWithEmpty(){
		@SuppressWarnings("unchecked")
		Supplier<Object> supplier = Mockito.mock(Supplier.class);
		
		RemoteableOptional<Object> remoteableOptional = RemoteableOptional.empty();
		remoteableOptional.orElseGet(supplier);
		Mockito.verify(supplier, Mockito.times(1)).get();
	}
	
	@Test
	public void orElseGetWithNotEmpty(){
		BigDecimal instance = BigDecimal.TEN;
		@SuppressWarnings("unchecked")
		Supplier<BigDecimal> supplier = Mockito.mock(Supplier.class);
		
		RemoteableOptional<BigDecimal> remoteableOptional = RemoteableOptional.of(instance);
		remoteableOptional.orElseGet(supplier);
		Mockito.verify(supplier, Mockito.never()).get();
	}
	
	@Test(expected = Throwable.class)
	public void orElseThrowWithEmpty() throws Throwable{
		Supplier<Throwable> supplier = Throwable::new;//() -> new Throwable();
		
		RemoteableOptional<Object> remoteableOptional = RemoteableOptional.empty();
		remoteableOptional.orElseThrow(supplier);
	}
	
	@Test
	public void orElseThrowWithNotEmpty() throws Throwable{
		BigDecimal instance = BigDecimal.TEN;
		@SuppressWarnings("unchecked")
		Supplier<Throwable> supplier = Mockito.mock(Supplier.class);
		
		RemoteableOptional<BigDecimal> remoteableOptional = RemoteableOptional.of(instance);
		remoteableOptional.orElseThrow(supplier);
		Mockito.verify(supplier, Mockito.never()).get();
	}
	
	@Test(expected = NullPointerException.class)
	public void mapWithNullMapper(){
		RemoteableOptional.of(BigDecimal.TEN).map(null);
	}
	
	@Test
	public void mapWithNotPresent(){
		@SuppressWarnings("unchecked")
		Function<BigDecimal, Object> mapper = Mockito.mock(Function.class);
		
		RemoteableOptional<BigDecimal> remoteableOptional = RemoteableOptional.empty();
		remoteableOptional.map(mapper);
		
		Mockito.verify(mapper, Mockito.never()).apply(any());
	}
	
	@Test
	public void mapWithPresent(){
		@SuppressWarnings("unchecked")
		Function<BigDecimal, Object> mapper = Mockito.mock(Function.class);
		
		RemoteableOptional<BigDecimal> remoteableOptional = RemoteableOptional.of(BigDecimal.TEN);
		remoteableOptional.map(mapper);
		Mockito.verify(mapper, Mockito.times(1)).apply(Mockito.eq(BigDecimal.TEN));
	}
	
	@Test
	public void toStringWithEmpty(){
		RemoteableOptional<Object> remoteableOptional = RemoteableOptional.empty();
		assertThat(remoteableOptional.toString(), comparesEqualTo("RemoteableOptional.empty"));
	}
	
	@Test
	public void toStringWithPresent(){
		RemoteableOptional<BigDecimal> remoteableOptional = RemoteableOptional.of(BigDecimal.TEN);
		String toStringResult = remoteableOptional.toString();
		
		assertThat(toStringResult, comparesEqualTo("RemoteableOptional[10]"));
	}
	
	@Test(expected = NullPointerException.class)
	public void flatMapWithNullMapper(){
		RemoteableOptional.of(BigDecimal.TEN).flatMap(null);
	}
	
	@Test
	public void flatMapWithEmpty(){
		@SuppressWarnings("unchecked")
		Function<BigDecimal, RemoteableOptional<Object>> mapper = Mockito.mock(Function.class);
		RemoteableOptional.<BigDecimal>empty().flatMap(mapper);
		Mockito.verify(mapper, Mockito.never()).apply(any());
	}
	
	@Test
	public void flatMapWithPresent(){
		@SuppressWarnings("unchecked")
		Function<BigDecimal, RemoteableOptional<Object>> mapper = Mockito.mock(Function.class);
		Mockito.when(mapper.apply(any())).thenReturn(RemoteableOptional.of(new Object()));
		RemoteableOptional.of(BigDecimal.TEN).flatMap(mapper);
		Mockito.verify(mapper, Mockito.times(1)).apply(any());
	}
	
	@Test(expected = NullPointerException.class)
	public void filterWithNullPredicate(){
		RemoteableOptional.of(BigDecimal.TEN).filter(null);
	}
	
	@Test
	public void filterWithEmpty(){
		@SuppressWarnings("unchecked")
		Predicate<Object> predicate = Mockito.mock(Predicate.class);
		RemoteableOptional.<Object>empty().filter(predicate);
		
		Mockito.verify(predicate, Mockito.never()).test(Mockito.any());
	}
	
	@Test
	public void filterWithPresentTrue(){
		Object value = new Object();
		@SuppressWarnings("unchecked")
		Predicate<Object> predicate = Mockito.mock(Predicate.class);
		Mockito.when(predicate.test(Mockito.any())).thenReturn(true);
		RemoteableOptional<Object> result = RemoteableOptional.<Object>of(value).filter(predicate);
		
		assertThat(result.get(), sameInstance(value));
	}
	
	@Test
	public void filterWithPresentFalse(){
		Object value = new Object();
		@SuppressWarnings("unchecked")
		Predicate<Object> predicate = Mockito.mock(Predicate.class);
		Mockito.when(predicate.test(Mockito.any())).thenReturn(false);
		RemoteableOptional<Object> result = RemoteableOptional.<Object>of(value).filter(predicate);
		
		assertThat(result.isPresent(), is(false));
	}
	
	@Test
	public void equalsSameInstances(){
		RemoteableOptional<BigDecimal> remoteableOptional = RemoteableOptional.of(BigDecimal.TEN);
		
		assertThat(remoteableOptional.equals(remoteableOptional), is(true));
	}
	
	@Test
	public void equalsNotSameInstanceOf(){
		RemoteableOptional<BigDecimal> remoteableOptional = RemoteableOptional.of(BigDecimal.TEN);
		BigDecimal bigDecimal = BigDecimal.TEN;
		
		assertThat(remoteableOptional.equals(bigDecimal), is(false));
	}
	
	@Test
	public void equalsSameValues(){
		RemoteableOptional<BigDecimal> remoteableOptionalFirst = RemoteableOptional.of(BigDecimal.TEN);
		RemoteableOptional<BigDecimal> remoteableOptionalSecond = RemoteableOptional.of(BigDecimal.TEN);
		
		assertThat(remoteableOptionalFirst.equals(remoteableOptionalSecond), is(true));
	}
	
	@Test
	public void equalsNotSameValues(){
		RemoteableOptional<BigDecimal> remoteableOptionalFirst = RemoteableOptional.of(BigDecimal.TEN);
		RemoteableOptional<BigDecimal> remoteableOptionalSecond = RemoteableOptional.of(BigDecimal.ONE);
		
		assertThat(remoteableOptionalFirst.equals(remoteableOptionalSecond), is(false));
	}
}
