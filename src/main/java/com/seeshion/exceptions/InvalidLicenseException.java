package com.seeshion.exceptions;

public class InvalidLicenseException extends Exception{

    public InvalidLicenseException(){

    }

    public InvalidLicenseException(String message) {
        super(message);
    }
}
