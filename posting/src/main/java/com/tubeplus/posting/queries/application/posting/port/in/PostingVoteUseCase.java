package com.tubeplus.posting.queries.application.posting.port.in;

import com.tubeplus.posting.queries.application.posting.domain.vote.Vote;

import java.util.Optional;

public interface PostingVoteUseCase {

    Optional<Vote> findUserVote(Long postingId, String userUuid);
}
