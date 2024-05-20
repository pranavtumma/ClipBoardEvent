package com.clipboard.event;

import java.util.EventObject;

public class ClipboardEvent extends EventObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8944614802610605676L;

	
	private String copiedText;

	public ClipboardEvent(Object source, String copiedText) {
		super(source);
		this.copiedText = copiedText;
	}

	public String getCopiedText() {
		return copiedText;
	}

}
