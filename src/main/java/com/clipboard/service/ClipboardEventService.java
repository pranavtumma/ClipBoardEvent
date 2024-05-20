package com.clipboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clipboard.entity.ClipboardEventEntity;
import com.clipboard.repository.ClipboardEventRepository;

@Service
public class ClipboardEventService {

	@Autowired
	private ClipboardEventRepository clipboardEventRepository;
	
	
	public void saveToDatabase(String copiedText) {
        ClipboardEventEntity clipboardEvent = new ClipboardEventEntity();
        clipboardEvent.setCopiedText(copiedText);
        clipboardEventRepository.save(clipboardEvent);
        System.out.println("Copied text saved to the database.");
    }

}
