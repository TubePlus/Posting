package com.tubeplus.posting.queries.application.posting.port.out;


import com.tubeplus.posting.queries.application.posting.domain.comment.Comment;
import com.tubeplus.posting.queries.application.posting.port.in.WebCommentUseCase.ReadCommentsInfo;
import com.tubeplus.posting.queries.global.Exceptionable;
import lombok.Data;

import java.util.List;

public interface CommentPersistable {


    Exceptionable<Long, Long> countComments(long postingId);


    Exceptionable<List<Comment>, FindCommentDto> findComments(FindCommentDto dto);

    @Data(staticConstructor = "of")
    class FindCommentDto {
        private final long postingId;
        private final Long parentId;

        public static FindCommentDto of(ReadCommentsInfo readInfo) {
            return FindCommentDto.of(
                    readInfo.getPostingId(),
                    readInfo.getParentCommentId()
            );
        }

        public boolean isFindingParent() {
            return parentId == null;
        }

        public boolean isFindingChildren() {
            return parentId != null;
        }

    }

}
