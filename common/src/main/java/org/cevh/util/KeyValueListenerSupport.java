package org.cevh.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class KeyValueListenerSupport<T> implements KeyValueListener {

	protected final T save;
	
	protected final Logger log;

	public KeyValueListenerSupport(T save) {
		this.save = save;
		log = LoggerFactory.getLogger( this.getClass() );
	}

}
