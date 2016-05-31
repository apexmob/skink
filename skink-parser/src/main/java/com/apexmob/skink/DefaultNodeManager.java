package com.apexmob.skink;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultNodeManager implements NodeManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultNodeManager.class);
	
	private final List<OnStartElementListener> onStartElementListeners = new LinkedList<>();
	private final List<OnEndElementListener> onEndElementListeners = new LinkedList<>();
	private final List<OnTextListener> onTextListeners = new LinkedList<>();

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
			try {
				listener.onStartElement(start);
			} catch (RuntimeException e) {
				logger.error("An error occurred while processing StartElement=" + start.getContent(), e);
			}
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
			try {
				listener.onEndElement(end);
			} catch (RuntimeException e) {
				logger.error("An error occurred while processing EndElement=" + end.getContent(), e);
			}
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
			try {
				listener.onText(text);
			} catch (RuntimeException e) {
				logger.error("An error occurred while processing Text=" + text.getContent(), e);
			}
		}
	}

	@Override
	public void addNodeListener(NodeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		if (listener instanceof OnStartElementListener) {
			addOnStartElementListener((OnStartElementListener) listener);
		}
		if (listener instanceof OnEndElementListener) {
			addOnEndElementListener((OnEndElementListener) listener);
		}
		if (listener instanceof OnTextListener) {
			addOnTextListener((OnTextListener) listener);
		}
	}

	@Override
	public boolean removeNodeListener(NodeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		
		boolean retVal = false;
		if (listener instanceof OnStartElementListener) {
			retVal = removeOnStartElementListener((OnStartElementListener) listener) || retVal;
		}
		if (listener instanceof OnEndElementListener) {
			retVal = removeOnEndElementListener((OnEndElementListener) listener) || retVal;
		}
		if (listener instanceof OnTextListener) {
			retVal = removeOnTextListener((OnTextListener) listener) || retVal;
		}
		
		return retVal;
	}

	@Override
	public void registerListener(NodeListener listener) {
		addNodeListener(listener);
	}

	@Override
	public void deregisterListener(NodeListener listener) {
		removeNodeListener(listener);
	}

	//TO BE REMOVED
	
}
