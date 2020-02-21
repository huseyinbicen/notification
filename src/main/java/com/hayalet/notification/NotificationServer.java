package com.hayalet.notification;

import com.hayalet.notification.config.NotificationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ NotificationConfig.class})
public class NotificationServer {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServer.class, args);
    }
}
