package com.clipboard.controller;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.clipboard.entity.ClipboardEventEntity;
import com.clipboard.listener.KeyListener;
import com.clipboard.repository.ClipboardEventRepository;

@Controller
public class ClipboardController {
	
//	@Autowired
//	private ClipboardEventService clipboardEventService;
	
    private List<KeyListener> keyListeners = new ArrayList<>();
    
    @Autowired
	private ClipboardEventRepository clipboardEventRepository;
    
    private long lastKeyPressTime = 0;

    public void addKeyListener(KeyListener listener) {
        keyListeners.add(listener);
    }

    public void removeKeyListener(KeyListener listener) {
        keyListeners.remove(listener);
    }

    public void startListening() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            clipboard.addFlavorListener(flavorEvent -> {
                if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                    try {
                        String copiedText = (String) clipboard.getData(DataFlavor.stringFlavor);
                        long timeDiff = getTimeDifference();
                        System.out.println("Copied Text: " + copiedText);
                        System.out.println("Time Taken: " + formatTime(timeDiff));
                        notifyKeyListeners(copiedText, timeDiff);
//                        if (clipboardEventService != null) {
//                            clipboardEventService.saveToDatabase(copiedText);
                        saveToDatabase(copiedText);
//                        } else {
//                            System.err.println("ClipboardEventService is null. Cannot save to database.");
//                        }
                    } catch (UnsupportedFlavorException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Create a dummy frame to handle the Ctrl+C events
            Frame frame = new Frame();
            frame.setSize(0, 0);
            frame.setLocation(-100, -100);
            frame.setVisible(true);

            frame.addKeyListener(new ClipboardKeyListener());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyKeyListeners(String copiedText, long timeDiff) {
        for (KeyListener listener : keyListeners) {
            listener.onKeyPress(copiedText, timeDiff);
        }
    }

    private long getTimeDifference() {
        long currentTime = System.currentTimeMillis();
        long timeDiff = currentTime - lastKeyPressTime;
        lastKeyPressTime = currentTime;
        return timeDiff;
    }

    private String formatTime(long timeInMillis) {
        long seconds = (timeInMillis / 1000) % 60;
        long minutes = (timeInMillis / (1000 * 60)) % 60;
        long hours = (timeInMillis / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private class ClipboardKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {
                notifyKeyListeners(getClipboardContents(), getTimeDifference());
            }
        }
    }

    private String getClipboardContents() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(null);

        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                return (String) transferable.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }
    
    public void saveToDatabase(String copiedText) {
        ClipboardEventEntity clipboardEvent = new ClipboardEventEntity();
        clipboardEvent.setCopiedText(copiedText);
        clipboardEventRepository.save(clipboardEvent);
        System.out.println("Copied text saved to the database.");
    }
}
