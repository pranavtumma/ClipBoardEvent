//package com.clipboard.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.clipboard.controller.ClipboardController;
//import com.clipboard.listener.MyKeyListener;
//
//@Configuration
//public class ClipboardConfig {
//	
//	@Bean
//    public ClipboardController clipboardController(MyKeyListener keyListener) {
//        ClipboardController controller = new ClipboardController();
//        controller.addKeyListener(keyListener);
//        controller.startListening();
//        return controller;
//    }
//
//    @Bean
//    public MyKeyListener myKeyListener() {
//        return new MyKeyListener();
//    }
//
//}
