package com.tubeplus.posting.queries.application.posting.service;

import com.tubeplus.posting.queries.adapter.web.error.ErrorCode;
import com.tubeplus.posting.queries.application.posting.domain.vote.Vote;
import com.tubeplus.posting.queries.application.posting.port.in.PostingVoteUseCase;
import com.tubeplus.posting.queries.application.posting.port.in.WebVoteUseCase;
import com.tubeplus.posting.queries.application.posting.port.out.VotePersistable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@SuppressWarnings("UnnecessaryLocalVariable")
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService implements PostingVoteUseCase, WebVoteUseCase {


    private final VotePersistable votePersistence;


    //posting use case
    public Optional<Vote> findUserVote(Long postingId, String userUuid) {

        VotePersistable.FindVoteDto dto = VotePersistable.FindVoteDto.of(postingId, userUuid);

        Optional<Vote> optionalUserVote
                = votePersistence.findVote(dto)
                .ifExceptioned.thenThrow(ErrorCode.FIND_ENTITY_FAILED);

        return optionalUserVote;
    }

}
