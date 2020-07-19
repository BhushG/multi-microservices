package com.javabrains.exception;

public class ServiceDiscoveryException extends Exception{
    public ServiceDiscoveryException(String serviceId) {
        super("Could Not Discover Service with Id: " + serviceId);
    }
}
