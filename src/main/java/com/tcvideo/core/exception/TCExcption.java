package com.tcvideo.core.exception;

/**
 * Created by Administrator on 2017-08-25.
 */
public class TCExcption extends RuntimeException{

    public TCExcption() {
        super();
    }

    public TCExcption(String message) {
        super(message);
    }
    public TCExcption(String code, String message) {
        super(message);
    }
    public TCExcption(String message, Throwable cause) {
        super(message,cause);
    }
    public TCExcption(Throwable cause) {
        super(cause);
    }
}
