package com.sejong.chatservice.core.common.pagination.enums;

import com.sejong.chatservice.core.common.pagination.CursorPageRequest;
import com.sejong.chatservice.core.error.code.ErrorCode;
import com.sejong.chatservice.core.error.exception.ApiException;

import java.util.Arrays;

public enum SortDirection {
    ASC, DESC;

    public static SortDirection from(String name) {
        return Arrays.stream(SortDirection.values())
                .filter(s -> s.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_SORT_REQUEST));
    }
}
