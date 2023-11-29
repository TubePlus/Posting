package com.tubeplus.posting.queries.application.posting.domain.vote;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vote {
    private final long id;
    private final long postingId;
    private final String voterUuid;
    private final VoteType voteType;
}
