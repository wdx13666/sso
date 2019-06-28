package com.smallflyingleg.sso.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    private Boolean status;
    private String message;
}
