package com.tubeplus.posting.queries.adapter.rdb.posting;

import com.tubeplus.posting.queries.adapter.rdb.posting.dao.PostingJpaDataRepository;
import com.tubeplus.posting.queries.adapter.rdb.posting.dao.PostingQDslRepositoryCustom;
import com.tubeplus.posting.queries.adapter.rdb.posting.entity.PostingEntity;
import com.tubeplus.posting.queries.application.posting.domain.posting.Posting;
import com.tubeplus.posting.queries.application.posting.port.out.PostingPersistable;
import com.tubeplus.posting.queries.global.Exceptionable;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@SuppressWarnings({"ConstantConditions", "UnnecessaryLocalVariable"})
@Slf4j
@Component
@PersistenceContext
@RequiredArgsConstructor
public class PostingPersistence implements PostingPersistable {

    private final PostingJpaDataRepository jpaDataRepo;
    private final PostingQDslRepositoryCustom queryDslRepo;

    @Override
    public Exceptionable<Optional<Posting>, Long> findPosting(long postingId) {

        return Exceptionable.act(id ->
        {
            Optional<PostingEntity> optionalEntity
                    = jpaDataRepo.findById(id);

            Optional<Posting> optionalPosting
                    = optionalEntity.map(PostingEntity::buildDomain);

            return optionalPosting;

        }, postingId);
    }

    @Override
    public boolean existNextPosting(FindPostingsDto findDto) {

        return queryDslRepo.existNextPosting(findDto);

    }

    @Override
    public Exceptionable<Long, FindPostingsDto.FieldsFindCondition> countPostings(FindPostingsDto.FieldsFindCondition condition) {

        return Exceptionable.act(queryDslRepo::countPostingEntities, condition);
    }

    @Override
    public Exceptionable<List<Posting>, FindPostingsDto> findPostings(FindPostingsDto findDto) {

        return Exceptionable.act(dto ->
        {
            List<PostingEntity> foundPostingEntities
                    = queryDslRepo.findPostingEntities(dto);

            List<Posting> foundPostings
                    = foundPostingEntities.stream()
                    .map(PostingEntity::buildDomain)
                    .collect(Collectors.toList());

            return foundPostings;

        }, findDto);
    }

}
