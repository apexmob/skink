package com.apexmob.skink;

import java.util.ArrayList;
import java.util.List;

public class DefaultNodeManager2 implements NodeManager2 {
	
	private final List<OnStartElementListener> onStartElementListeners = new ArrayList<>();
	private final List<OnEndElementListener> onEndElementListeners = new ArrayList<>();
	private final List<OnTextListener> onTextListeners = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	@Override
	public void addOnStartElementListener(OnStartElementListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		
		onStartElementListeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	@Override
	public boolean removeOnStartElementListener(OnStartElementListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		
		return onStartElementListeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	@Override
	public void addOnEndElementListener(OnEndElementListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		
		onEndElementListeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	@Override
	public boolean removeOnEndElementListener(OnEndElementListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		
		return onEndElementListeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	@Override
	public void addOnTextListener(OnTextListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		
		onTextListeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	@Override
	public boolean removeOnTextListener(OnTextListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		
		return onTextListeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the StartElement provided is null.
	 */
	@Override
	public void startElement(StartElement start) {
		if (start == null) {
			throw new IllegalArgumentException("The StartElement provided is null");
		}
		
		for (OnStartElementListener listener : onStartElementListeners) {
			listener.onStartElement(start);
		}
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the EndElement provided is null.
	 */
	@Override
	public void endElement(EndElement end) {
		if (end == null) {
			throw new IllegalArgumentException("The EndElement provided is null");
		}
		
		for (OnEndElementListener listener : onEndElementListeners) {
			listener.onEndElement(end);
		}
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the Text provided is null.
	 */
	@Override
	public void text(Text text) {
		if (text == null) {
			throw new IllegalArgumentException("The Text provided is null");
		}
		
		for (OnTextListener listener : onTextListeners) {
			listener.onText(text);
		}
	}

	@Override
	public void registerListener(NodeListener listener) {
		addOnStartElementListener(listener);
		addOnEndElementListener(listener);
		addOnTextListener(listener);
	}

	@Override
	public void deregisterListener(NodeListener listener) {
		removeOnStartElementListener(listener);
		removeOnEndElementListener(listener);
		removeOnTextListener(listener);
	}

	//TO BE REMOVED
	
}
