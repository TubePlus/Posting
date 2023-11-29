package com.tubeplus.posting.queries.application.posting.port.out;


import com.tubeplus.posting.queries.application.posting.domain.vote.Vote;
import com.tubeplus.posting.queries.global.Exceptionable;
import lombok.Data;

import java.util.Optional;


public interface VotePersistable {

    Exceptionable<Optional<Vote>, Long> findVote(Long voteId);

    Exceptionable<Optional<Vote>, FindVoteDto> findVote(FindVoteDto dto);

    @Data(staticConstructor = "of")
    class FindVoteDto {
        final Long postingId;
        final String voterUuid;
    }


}
