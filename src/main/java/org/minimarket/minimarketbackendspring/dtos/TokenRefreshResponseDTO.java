package org.minimarket.minimarketbackendspring.dtos;

/**
 * DTO para respuesta de operaciones que pueden requerir refresh de token
 */
public class TokenRefreshResponseDTO {
  private boolean success;
  private String message;
  private boolean requiresTokenRefresh;
  private Object data;

  public TokenRefreshResponseDTO() {
  }

  public TokenRefreshResponseDTO(boolean success, String message, boolean requiresTokenRefresh) {
    this.success = success;
    this.message = message;
    this.requiresTokenRefresh = requiresTokenRefresh;
  }

  public TokenRefreshResponseDTO(boolean success, String message, boolean requiresTokenRefresh, Object data) {
    this.success = success;
    this.message = message;
    this.requiresTokenRefresh = requiresTokenRefresh;
    this.data = data;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isRequiresTokenRefresh() {
    return requiresTokenRefresh;
  }

  public void setRequiresTokenRefresh(boolean requiresTokenRefresh) {
    this.requiresTokenRefresh = requiresTokenRefresh;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
