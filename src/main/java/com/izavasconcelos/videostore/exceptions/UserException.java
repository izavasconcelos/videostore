package com.izavasconcelos.videostore.exceptions;

public class UserException extends RuntimeException{
  public UserException(String message, Throwable cause) {
    super(message, cause);
  }
}
