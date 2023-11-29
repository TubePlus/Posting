package com.tubeplus.posting.queries.application.posting.domain.vote;

import com.tubeplus.posting.queries.global.base.BaseEnum;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum VoteType implements BaseEnum<Integer, String> {


    LIKE(1),
    HATE(-1);


    private final Integer code;


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return name();
    }
}
