package org.example.test_unibell.exeptions;

public class InvalidContactTypeException extends RuntimeException {
    public InvalidContactTypeException(String message) {
        super(message);
    }
}