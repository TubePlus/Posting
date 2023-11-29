package com.tubeplus.posting.queries.application.posting.service;

import com.tubeplus.posting.queries.adapter.web.error.BusinessException;
import com.tubeplus.posting.queries.adapter.web.error.ErrorCode;
import com.tubeplus.posting.queries.application.posting.domain.comment.Comment;
import com.tubeplus.posting.queries.application.posting.port.in.PostingCommentUseCase;
import com.tubeplus.posting.queries.application.posting.port.in.WebCommentUseCase;
import com.tubeplus.posting.queries.application.posting.port.out.CommentPersistable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService implements WebCommentUseCase, PostingCommentUseCase {

    private final CommentPersistable commentPersistence;


    //queries
    @Override
    public List<Comment> readComments(ReadCommentsInfo readInfo) {

        CommentPersistable.FindCommentDto dto = CommentPersistable.FindCommentDto.of(readInfo);

        List<Comment> comments
                = commentPersistence.findComments(dto)
                .ifExceptioned.thenThrow(ErrorCode.FIND_ENTITY_FAILED);

        if (comments.isEmpty())
            throw new BusinessException(ErrorCode.NOT_FOUND_RESOURCE);

        return comments;
    }

    @Override
    public long countComments(long postingId) {

        return commentPersistence.countComments(postingId)
                .ifExceptioned.thenThrow(new BusinessException(
                        ErrorCode.FIND_ENTITY_FAILED, "countComments failed"));
    }


}
