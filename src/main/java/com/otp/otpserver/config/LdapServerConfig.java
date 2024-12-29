package com.otp.otpserver.config;

import com.unboundid.ldap.listener.*;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.OperationType;
import com.unboundid.ldap.sdk.schema.Schema;
import com.unboundid.ldif.LDIFException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Handler;

@Configuration
@Slf4j
public class LdapServerConfig {

    @Bean
    public InMemoryDirectoryServer inMemoryDirectoryServer() throws LDAPException {
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=example,dc=com,rdn=org");

        // Configure the listener with the desired port and other settings
        InMemoryListenerConfig listenerConfig = new InMemoryListenerConfig("default",
                null, // Use default address (localhost)
                389, // Set the desired port
                null, // Use default ServerSocketFactory
                null, // Use default SocketFactory
                null, // No StartTLS
                false, // Do not request client certificate
                false); // Do not require client certificate
        config.setListenerConfigs(listenerConfig);
        config.setAllowedOperationTypes(getOperationTypes());
        config.setLDAPDebugLogHandler(new LdapDebugLogHandler());
        config.setListenerExceptionHandler(new LdapExceptionHandler());
        config.setJSONAccessLogHandler(new LdapJsonAccessLogHandler());
        config.setBaseDNs("dc=example,dc=com,rdn=org");
        config.setCodeLogDetails("/root/tata/source/otp-pr/otp-server/ldaplog/ldaplog.log",true);
        config.setAccessLogHandler(new LdapAccessLogHandler());
        try {
            config.setSchema(Schema.getSchema("default"));
        } catch (Exception e) {
           log.error("error get schema : {}",e.getLocalizedMessage());
        }
        config.addInMemoryOperationInterceptor(new LdapInMemoryOperationInterceptor());
        InMemoryDirectoryServer server = new InMemoryDirectoryServer(config);
//        server.addSchema(InMemoryDirectoryServer.getDefaultSchema());
        server.startListening();
        return server;
    }


    private static Collection<OperationType> getOperationTypes() {
        Collection<OperationType> operationTypes=new ArrayList<>();
        operationTypes.add(OperationType.ADD);
        operationTypes.add(OperationType.COMPARE);
        operationTypes.add(OperationType.DELETE);
        operationTypes.add(OperationType.ABANDON);
        operationTypes.add(OperationType.BIND);
        operationTypes.add(OperationType.EXTENDED);
        operationTypes.add(OperationType.MODIFY);
        operationTypes.add(OperationType.MODIFY_DN);
        operationTypes.add(OperationType.SEARCH);
        operationTypes.add(OperationType.UNBIND);
        return operationTypes;
    }

    @Bean
    public LdapContextSource contextSource() throws LDAPException {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://127.0.0.1:" + inMemoryDirectoryServer().getListenPort());
        contextSource.setBase("rdn=org,dc=examplee,dc=com");

        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() throws LDAPException {
        return new LdapTemplate(contextSource());
    }

}