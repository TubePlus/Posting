package com.tubeplus.posting.queries.adapter.rdb.posting;

import com.tubeplus.posting.queries.adapter.rdb.posting.dao.VoteJpaDataRepository;
import com.tubeplus.posting.queries.adapter.rdb.posting.entity.VoteEntity;
import com.tubeplus.posting.queries.application.posting.domain.vote.Vote;
import com.tubeplus.posting.queries.application.posting.port.out.VotePersistable;
import com.tubeplus.posting.queries.global.Exceptionable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
public class VotePersistence implements VotePersistable {


    private final VoteJpaDataRepository voteJpaDataRepo;


    @Override
    public Exceptionable<Optional<Vote>, FindVoteDto> findVote(FindVoteDto findVoteDto) {

        return Exceptionable.act(dto ->
        {

            Optional<VoteEntity> foundEntity
                    = voteJpaDataRepo.findByPostingIdAndVoterUuid
                    (dto.getPostingId(), dto.getVoterUuid());

            Optional<Vote> foundVote
                    = foundEntity.map(VoteEntity::buildDomain);

            return foundVote;

        }, findVoteDto);
    }

    @Override
    public Exceptionable<Optional<Vote>, Long> findVote(Long voteId) {
        return Exceptionable.act(id -> {
            return voteJpaDataRepo.findById(id).map(VoteEntity::buildDomain);
        }, voteId);
    }

}

