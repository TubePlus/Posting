package com.tubeplus.posting.queries.application.posting.service;

import com.tubeplus.posting.queries.adapter.web.error.BusinessException;
import com.tubeplus.posting.queries.adapter.web.error.ErrorCode;
import com.tubeplus.posting.queries.application.posting.domain.posting.Posting;
import com.tubeplus.posting.queries.application.posting.domain.posting.PostingPageView;
import com.tubeplus.posting.queries.application.posting.port.in.PostingUseCase;
import com.tubeplus.posting.queries.application.posting.port.out.PostingPersistable;
import com.tubeplus.posting.queries.application.posting.domain.posting.PostingFeedData;
import com.tubeplus.posting.queries.application.posting.domain.posting.PostingView;
import com.tubeplus.posting.queries.application.posting.port.in.PostingCommentUseCase;
import com.tubeplus.posting.queries.application.posting.port.in.PostingVoteUseCase;
import com.tubeplus.posting.queries.application.posting.port.out.PostingEventPublishable;
import com.tubeplus.posting.queries.global.Exceptionable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;


@SuppressWarnings("UnnecessaryLocalVariable")
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostingService implements PostingUseCase {

    // driving service
    private final PostingVoteUseCase voteService;
    private final PostingCommentUseCase commentService;

    // driven by service
    private final PostingPersistable postingPersistence;
    private final PostingEventPublishable eventPublisher;


    // both used in query and command
    private Posting getPosting(long postingId) {

        Optional<Posting> optionalFound
                = postingPersistence.findPosting(postingId)
                .ifExceptioned
                .thenThrow(ErrorCode.FIND_ENTITY_FAILED);

        Posting foundPosting
                = optionalFound.orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_RESOURCE));

        return foundPosting;
    }


    // Queries
    @Override
    public PostingView readPostingView(long postingId, String userUuid) {

        Posting foundPosting
                = this.getPosting(postingId);

        eventPublisher.publishPostingRead(foundPosting);

        return PostingView.madeFrom(
                foundPosting,
                userUuid,
                voteService,
                commentService
        );
    }

    @Override
    public Page<PostingPageView> pagePostings(InfoToPagePostingData infoToPage) {

        /**/
        List<Posting> foundPagePostings;

        PostingPersistable.FindPostingsDto dto = PostingPersistable.FindPostingsDto.of(infoToPage);

        foundPagePostings = postingPersistence.findPostings(dto)
                .ifExceptioned.thenThrow(ErrorCode.FIND_ENTITY_FAILED);

        if (foundPagePostings.isEmpty())
            throw new BusinessException(
                    ErrorCode.NOT_FOUND_RESOURCE, "No postings to page found.");


        //count 쿼리 최적화 위한 함수형 변수
        LongSupplier countPostingsFunction
                = () -> postingPersistence.countPostings(dto.getFieldsFindCondition())
                .ifExceptioned.thenThrow(ErrorCode.COUNT_ENTITY_FAILED);


        /**/
        Page<PostingPageView> pagedPostingView;

        Page<Posting> pagedPostings //todo 첫페이지랑 마지막 언저리페이지들만 count 쿼리 날리도록 최적화 수정
                = PageableExecutionUtils.getPage // PageableExecutionUtils.getPage: count 쿼리 최적화 위해 사용
                (foundPagePostings, infoToPage.getPageReq(), countPostingsFunction);

        pagedPostingView
                = pagedPostings.map(
                posting -> PostingPageView.madeFrom(posting, commentService)
        );

        return pagedPostingView;
    }

    @Override
    public Feed<PostingFeedData> feedPostingData(InfoToFeedPostingData infoToFeed) {

        PostingPersistable.FindPostingsDto findDto = PostingPersistable.FindPostingsDto.of(infoToFeed);

        /**/
        List<PostingFeedData> feedDataList;

        List<Posting> foundFeedPostings
                = postingPersistence.findPostings(findDto)
                .ifExceptioned.thenThrow(ErrorCode.FIND_ENTITY_FAILED);

        if (foundFeedPostings.isEmpty())
            throw new BusinessException(ErrorCode.NOT_FOUND_RESOURCE, "No postings to feed condition found.");

        feedDataList
                = foundFeedPostings.stream()
                .map(posting -> PostingFeedData.madeFrom(posting, commentService))
                .collect(Collectors.toList());


        /**/
        Long lastCursoredId;

        Posting lastFoundPosting
                = foundFeedPostings.get(foundFeedPostings.size() - 1);

        lastCursoredId = lastFoundPosting.getId();


        /**/
        boolean hasNextFeed;

        findDto.getFieldsFindCondition()
                .setCursorId(lastCursoredId);

        hasNextFeed = Exceptionable.act(postingPersistence::existNextPosting, findDto)
                .ifExceptioned.thenThrow(new BusinessException(
                        ErrorCode.FIND_ENTITY_FAILED, "Failed to check if there is next posting to feed."
                ));


        /**/
        return Feed.of(
                feedDataList,
                lastCursoredId,
                hasNextFeed
        );
    }

}
