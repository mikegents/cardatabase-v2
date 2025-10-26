package com.example.cardatabase_v2.utilities;

public class InvalidCarDataException extends RuntimeException {
  public InvalidCarDataException(String message) {
    super(message);
  }
}
