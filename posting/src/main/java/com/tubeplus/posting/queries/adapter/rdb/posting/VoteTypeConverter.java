package com.tubeplus.posting.queries.adapter.rdb.posting;

import com.tubeplus.posting.queries.adapter.rdb.common.AbstractBaseEnumConverter;
import com.tubeplus.posting.queries.application.posting.domain.vote.VoteType;
import jakarta.persistence.Converter;


//@Converter(autoApply = true)
public class VoteTypeConverter extends AbstractBaseEnumConverter<VoteType, Integer, String> {

    @SuppressWarnings("unused")
    public VoteTypeConverter() {
        super(VoteType.class);
    }
}

