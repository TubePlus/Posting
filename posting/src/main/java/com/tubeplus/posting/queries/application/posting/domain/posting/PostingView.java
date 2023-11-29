package com.tubeplus.posting.queries.application.posting.domain.posting;


import com.tubeplus.posting.queries.application.posting.port.in.PostingVoteUseCase;
import com.tubeplus.posting.queries.application.posting.domain.vote.Vote;
import com.tubeplus.posting.queries.application.posting.port.in.PostingCommentUseCase;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@Builder
public class PostingView {

    private final String authorUuid;

    private final long voteCount;

    private final String contents;

    private final String title;

    private final Long userVoteId;

    private final boolean withImage;

    private final long commentsCount;


    public static PostingView madeFrom(Posting posting,
                                       String userUuid,
                                       PostingVoteUseCase voteService,
                                       PostingCommentUseCase commentService) {

        /**/
        Long userVoteId
                = voteService.findUserVote(posting.getId(), userUuid)
                .map(Vote::getId).orElse(null);

        /**/
        long commentsCount
                = commentService.countComments(posting.getId());

        /**/
        return PostingView.builder()
                .authorUuid(posting.getAuthorUuid())
                .voteCount(posting.getVoteCount())
                .contents(posting.getContents())
                .title(posting.getTitle())
                .userVoteId(userVoteId)
                .withImage(posting.isWithImage())
                .commentsCount(commentsCount)
                .build();
    }


}
