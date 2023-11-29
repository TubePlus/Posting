package com.tubeplus.posting.queries.application.board.domain;

import com.tubeplus.posting.queries.global.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@AllArgsConstructor
public enum BoardType implements BaseEnum<String, String> {

    NORMAL("N"),
    MEMBERSHIP("M");


    private String code;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return super.name();
    }


}
