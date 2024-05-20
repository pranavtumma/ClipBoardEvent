package com.clipboard.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyKeyListener implements KeyListener {
    
	@Override
    public void onKeyPress(String copiedText, long timeDiff) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date resultDate = new Date(timeDiff);
        System.out.println("Copied Text: " + copiedText);
        System.out.println("Time Taken: " + sdf.format(resultDate));
    }
}