package com.otp.otpserver.config;

import com.unboundid.ldap.listener.interceptor.*;
import com.unboundid.ldap.sdk.LDAPException;

public class LdapInMemoryOperationInterceptor extends InMemoryOperationInterceptor {

    public LdapInMemoryOperationInterceptor() {
        super();
    }

    @Override
    public void processAddRequest(InMemoryInterceptedAddRequest request) throws LDAPException {
        super.processAddRequest(request);
    }

    @Override
    public void processAddResult(InMemoryInterceptedAddResult result) {
        super.processAddResult(result);
    }

    @Override
    public void processSimpleBindRequest(InMemoryInterceptedSimpleBindRequest request) throws LDAPException {
        super.processSimpleBindRequest(request);
    }

    @Override
    public void processSimpleBindResult(InMemoryInterceptedSimpleBindResult result) {
        super.processSimpleBindResult(result);
    }

    @Override
    public void processSASLBindRequest(InMemoryInterceptedSASLBindRequest request) throws LDAPException {
        super.processSASLBindRequest(request);
    }

    @Override
    public void processSASLBindResult(InMemoryInterceptedSASLBindResult result) {
        super.processSASLBindResult(result);
    }

    @Override
    public void processCompareRequest(InMemoryInterceptedCompareRequest request) throws LDAPException {
        super.processCompareRequest(request);
    }

    @Override
    public void processCompareResult(InMemoryInterceptedCompareResult result) {
        super.processCompareResult(result);
    }

    @Override
    public void processDeleteRequest(InMemoryInterceptedDeleteRequest request) throws LDAPException {
        super.processDeleteRequest(request);
    }

    @Override
    public void processDeleteResult(InMemoryInterceptedDeleteResult result) {
        super.processDeleteResult(result);
    }

    @Override
    public void processExtendedRequest(InMemoryInterceptedExtendedRequest request) throws LDAPException {
        super.processExtendedRequest(request);
    }

    @Override
    public void processExtendedResult(InMemoryInterceptedExtendedResult result) {
        super.processExtendedResult(result);
    }

    @Override
    public void processModifyRequest(InMemoryInterceptedModifyRequest request) throws LDAPException {
        super.processModifyRequest(request);
    }

    @Override
    public void processModifyResult(InMemoryInterceptedModifyResult result) {
        super.processModifyResult(result);
    }

    @Override
    public void processModifyDNRequest(InMemoryInterceptedModifyDNRequest request) throws LDAPException {
        super.processModifyDNRequest(request);
    }

    @Override
    public void processModifyDNResult(InMemoryInterceptedModifyDNResult result) {
        super.processModifyDNResult(result);
    }

    @Override
    public void processSearchRequest(InMemoryInterceptedSearchRequest request) throws LDAPException {
        super.processSearchRequest(request);
    }

    @Override
    public void processSearchEntry(InMemoryInterceptedSearchEntry entry) {
        super.processSearchEntry(entry);
    }

    @Override
    public void processSearchReference(InMemoryInterceptedSearchReference reference) {
        super.processSearchReference(reference);
    }

    @Override
    public void processSearchResult(InMemoryInterceptedSearchResult result) {
        super.processSearchResult(result);
    }

    @Override
    public void processIntermediateResponse(InMemoryInterceptedIntermediateResponse response) {
        super.processIntermediateResponse(response);
    }
}
