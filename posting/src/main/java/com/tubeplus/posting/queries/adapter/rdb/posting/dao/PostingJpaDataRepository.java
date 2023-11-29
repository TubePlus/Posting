package com.tubeplus.posting.queries.adapter.rdb.posting.dao;

import com.tubeplus.posting.queries.adapter.rdb.posting.entity.PostingEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostingJpaDataRepository extends JpaRepository<PostingEntity, Long> {
}