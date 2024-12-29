package com.otp.otpserver.config;

import org.springframework.stereotype.Component;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

@Component
public class LdapDebugLogHandler extends Handler {

    @Override
    public void publish(LogRecord record) {
        System.out.println("");
    }

    @Override
    public void flush() {
        System.out.println("");

    }

    @Override
    public void close() throws SecurityException {
        System.out.println("");

    }
}
