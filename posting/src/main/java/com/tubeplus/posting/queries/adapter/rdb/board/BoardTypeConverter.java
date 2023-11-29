package com.tubeplus.posting.queries.adapter.rdb.board;

import com.tubeplus.posting.queries.adapter.rdb.common.AbstractBaseEnumConverter;
import com.tubeplus.posting.queries.application.board.domain.BoardType;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
public class BoardTypeConverter extends AbstractBaseEnumConverter<BoardType, String, String> {
    public BoardTypeConverter() {
        super(BoardType.class);
    }
}
