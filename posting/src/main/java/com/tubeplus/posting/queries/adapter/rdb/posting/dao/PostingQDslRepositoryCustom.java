package com.tubeplus.posting.queries.adapter.rdb.posting.dao;


import com.tubeplus.posting.queries.adapter.rdb.posting.entity.PostingEntity;
import com.tubeplus.posting.queries.application.posting.port.out.PostingPersistable;

import java.util.List;


public interface PostingQDslRepositoryCustom {

    Long countPostingEntities(PostingPersistable.FindPostingsDto.FieldsFindCondition whereCondition);

    List<PostingEntity> findPostingEntities(PostingPersistable.FindPostingsDto findDto);


    boolean existNextPosting(PostingPersistable.FindPostingsDto findDto);
}
