package org.minimarket.minimarketbackendspring.dtos.requests;

public class FirebaseTokenDTO {
    private String idToken;
    private String provider;

    public FirebaseTokenDTO() {}

    public FirebaseTokenDTO(String idToken, String provider) {
        this.idToken = idToken;
        this.provider = provider;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}