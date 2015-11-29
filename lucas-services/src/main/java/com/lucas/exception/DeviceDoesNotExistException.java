package com.lucas.exception;

public class DeviceDoesNotExistException extends RuntimeException{
        
        private static final long serialVersionUID = 1L;

        public DeviceDoesNotExistException() { 
            super();
        }

        public DeviceDoesNotExistException(String message) {
            super(message);
        }

        public DeviceDoesNotExistException(Throwable cause) {
            super(cause);
        }

        public DeviceDoesNotExistException(String message, Throwable cause) {
            super(message, cause);
        }
    }
