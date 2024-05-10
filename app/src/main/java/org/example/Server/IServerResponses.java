package org.example.Server;

public interface IServerResponses {
    // ok:
    String connected = "ok:Connected!";
    
    // error:
    String failedConnecting = "error:Failed To Connect!";
    String ineligibleForTest = "error:Invalid Credentials or In Eligible to Attend test. Please contact test admin.";
    
    // signature:
    String endResponseSignature = "signature:@endResponseSignature";
}
