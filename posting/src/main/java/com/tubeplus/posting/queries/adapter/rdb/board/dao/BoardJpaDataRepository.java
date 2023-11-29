package com.tubeplus.posting.queries.adapter.rdb.board.dao;

import com.tubeplus.posting.queries.adapter.rdb.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardJpaDataRepository extends JpaRepository<BoardEntity, Long> {

}