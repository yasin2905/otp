package com.otp.otpserver.config;

import com.unboundid.ldap.listener.LDAPListenerClientConnection;
import com.unboundid.ldap.listener.LDAPListenerExceptionHandler;
import com.unboundid.ldap.sdk.LDAPException;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

@Component
public class LdapExceptionHandler implements LDAPListenerExceptionHandler {


    @Override
    public void connectionCreationFailure(Socket socket, Throwable throwable) {
        System.out.println("Connection creation failed");
    }

    @Override
    public void connectionTerminated(LDAPListenerClientConnection ldapListenerClientConnection, LDAPException e) {
        System.out.println("Connection terminated");
    }
}
