package com.sejong.chatservice.core.error.exception;

import com.sejong.chatservice.core.error.code.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String errorDescription;

    public ApiException(ErrorCodeIfs errorCodeIfs){
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs=errorCodeIfs;
        errorDescription=errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription){
        super(errorDescription);
        this.errorCodeIfs=errorCodeIfs;
        this.errorDescription=errorDescription;

    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx){
        super(tx);
        this.errorCodeIfs=errorCodeIfs;
        this.errorDescription=errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs , Throwable tx, String errorDescription){
        super(tx);
        this.errorCodeIfs=errorCodeIfs;
        this.errorDescription=errorDescription;
    }


}
