package com.smant.tms.core.exceptions;

import com.smant.common.exceptions.SmantBaseException;

/**
 * 异常
 */
public class TmsException extends SmantBaseException {
    //用详细信息指定一个异常
    public TmsException(String message) {
        super(message);
    }

    //用指定的详细信息和原因构造一个新的异常
    public TmsException(String message, Throwable cause) {
        super(message, cause);
    }

    //用指定原因构造一个新的异常
    public TmsException(Throwable cause) {
        super(cause);
    }
}
