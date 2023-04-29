package school.sptech.springgoogleoauth2authentication.model;

import lombok.Data;

@Data
public class AccessDeniedModel {

  private String message;
  private String missing;

  public AccessDeniedModel(String message, String missing) {
    this.message = message;
    this.missing = missing;
  }
}
