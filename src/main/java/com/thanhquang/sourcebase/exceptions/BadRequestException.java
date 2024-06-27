package com.thanhquang.sourcebase.exceptions;

import java.io.Serializable;

import com.thanhquang.sourcebase.exceptions.error_code.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class BadRequestException extends Exception implements Serializable {

    private final ErrorCode errorCode;
}
