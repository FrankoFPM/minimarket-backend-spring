package org.minimarket.minimarketbackendspring.services.interfaces;

public interface AuthService {
    boolean authenticate(String email, String password);
}
