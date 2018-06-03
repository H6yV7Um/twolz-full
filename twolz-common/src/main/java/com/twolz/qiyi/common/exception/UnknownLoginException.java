package com.twolz.qiyi.common.exception;


/**
 * Created by liuwei
 * date: 16/7/15
 */
public class UnknownLoginException extends Exception {

    private static final long serialVersionUID = 8918015960590498719L;

    public UnknownLoginException(){
        super();
    }
    /**
     * Constructs a new NoLoginAuthorException.
     *
     * @param message the reason for the exception
     */
    public UnknownLoginException(String message) {
        super(message);
    }

    /**
     * Constructs a new NoLoginAuthorException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public UnknownLoginException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new NoLoginAuthorException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public UnknownLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}

