package com.clipboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.clipboard.controller.ClipboardController;
import com.clipboard.listener.MyKeyListener;

@SpringBootApplication
@ComponentScan(basePackages = "com.clipboard")
public class ClipboardEventApplication {

	public static void main(String[] args) {
		
		 ClipboardController controller = new ClipboardController();
	        MyKeyListener keyListener = new MyKeyListener();

	        controller.addKeyListener(keyListener);
	        controller.startListening();
	        SpringApplication.run(ClipboardEventApplication.class, args);
		
	}

}
