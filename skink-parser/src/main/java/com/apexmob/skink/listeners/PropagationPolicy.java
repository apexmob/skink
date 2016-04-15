package com.apexmob.skink.listeners;

/**
 * The PropagationPolicy enumeration is used to indicate what types of node events should be propagated to
 * other listeners. 
 */
public enum PropagationPolicy {
	
	/**
	 * Include all node events.
	 */
	All,
	
	/**
	 * Include only tags.
	 */
	TagsOnly,
	
	/**
	 * Include only the parent tags.
	 */
	ParentTagOnly;

}
