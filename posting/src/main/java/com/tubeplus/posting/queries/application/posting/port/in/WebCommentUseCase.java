package com.tubeplus.posting.queries.application.posting.port.in;

import com.tubeplus.posting.queries.application.posting.domain.comment.Comment;
import lombok.Data;

import java.util.List;

public interface WebCommentUseCase {


    List<Comment> readComments(ReadCommentsInfo readInfo);

    @Data(staticConstructor = "of")
    class ReadCommentsInfo {
        private final long postingId;
        private final Long parentCommentId;
    }

}
