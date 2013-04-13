package org.cevh.util;


public abstract class KeyValueListenerSupport<T> implements KeyValueListener {

	protected final T save;

	public KeyValueListenerSupport(T save) {
		this.save = save;
	}

}
