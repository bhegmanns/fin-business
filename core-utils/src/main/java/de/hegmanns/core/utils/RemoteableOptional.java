package de.hegmanns.core.utils;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Optional for remoting.
 * 
 * @author B. Hegmanns
 *
 * @param <T> type
 */
public class RemoteableOptional<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final RemoteableOptional<?> EMPTY = new RemoteableOptional<>();
	
	private final T value;
	
	private RemoteableOptional() {
		this.value = null;
	}
	
	public static <T> RemoteableOptional<T> empty(){
		@SuppressWarnings("unchecked")
		RemoteableOptional<T> remoteableOptional = (RemoteableOptional<T>) EMPTY;
		return remoteableOptional;
	}
	
	private RemoteableOptional(T value){
		this.value = Objects.requireNonNull(value);
	}
	
	public static <T> RemoteableOptional<T> of(T value){
		return new RemoteableOptional<>(value);
	}
	
	public static <T> RemoteableOptional<T> ofNullable(T value){
		return value == null ? empty() : of(value);
	}
	
	public T get(){
		if (value == null){
			throw new NoSuchElementException("No Value present");
		}
		
		return value;
	}
	
	public boolean isPresent(){
		return value != null;
	}
	
	public void ifPresent(Consumer<? super T> consumer){
		if (value != null){
			consumer.accept(value);
		}
	}
	
	public RemoteableOptional<T> filter(Predicate<? super T> predicate){
		Objects.requireNonNull(predicate);
		if (!isPresent()){
			return this;
		}
		else
		{
			return predicate.test(value) ? this : empty();
		}
	}
	
	public <U> RemoteableOptional<U> map(Function<? super T, ? extends U> mapper){
		Objects.requireNonNull(mapper);
		if (!isPresent()){
			return empty();
		}else{
			return RemoteableOptional.ofNullable(mapper.apply(value));
		}
	}
	
	public <U> RemoteableOptional<U> flatMap(Function<? super T, RemoteableOptional<U>> mapper){
		Objects.requireNonNull(mapper);
		if (!isPresent()){
			return empty();
		}else{
			return Objects.requireNonNull(mapper.apply(value));
		}
	}
	
	public T orElse(T other){
		return value != null ? value : other;
	}
	
	public T orElseGet(Supplier<? extends T> other){
		return value != null ? value : other.get();
	}
	
	public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }
	
	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof RemoteableOptional)) {
            return false;
        }

        RemoteableOptional<?> other = (RemoteableOptional<?>) obj;
        return Objects.equals(value, other.value);
    }
	
	@Override
	public int hashCode(){
		return Objects.hashCode(value);
	}
	
	@Override
	public String toString(){
		return value != null ? String.format("RemoteableOptional[%s]", value) : "RemoteableOptional.empty";
	}
}
