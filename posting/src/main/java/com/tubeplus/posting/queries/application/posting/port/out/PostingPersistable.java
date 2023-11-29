package com.tubeplus.posting.queries.application.posting.port.out;


import com.tubeplus.posting.queries.application.posting.domain.posting.Posting;
import com.tubeplus.posting.queries.application.posting.port.out.PostingPersistable.FindPostingsDto.FieldsFindCondition;
import com.tubeplus.posting.queries.application.posting.port.out.PostingPersistable.FindPostingsDto.SortedFindRange.SortBy.PivotField;
import com.tubeplus.posting.queries.global.Exceptionable;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.tubeplus.posting.queries.application.posting.port.in.PostingUseCase.*;


public interface PostingPersistable {

    // Both used in query and command
    Exceptionable<Optional<Posting>, Long> findPosting(long postingId);

    // Queries
    boolean existNextPosting(FindPostingsDto dto);

    Exceptionable<Long, FieldsFindCondition> countPostings(FieldsFindCondition condition);

    Exceptionable<List<Posting>, FindPostingsDto> findPostings(FindPostingsDto dto);

    @Data(staticConstructor = "of")
    class FindPostingsDto {

        private final FieldsFindCondition fieldsFindCondition;
        private final SortedFindRange sortedRange;

        public static FindPostingsDto of(InfoToPagePostingData infoToPage) {
            return FindPostingsDto.of(
                    FieldsFindCondition.builtFrom(infoToPage),
                    SortedFindRange.of(infoToPage)
            );
        }

        public static FindPostingsDto of(InfoToFeedPostingData infoToFeed) {
            return FindPostingsDto.of(
                    FieldsFindCondition.builtFrom(infoToFeed),
                    SortedFindRange.of(infoToFeed)
            );
        }

        @Data
        @Builder
        public static class FieldsFindCondition {
            private Long cursorId;
            private final Long boardId;
            private final String authorUuid;
            private final Boolean pin;
            private final String titleContaining;
            private final String contentsContaining;
            private final Boolean softDelete;

            public static FieldsFindCondition builtFrom(InfoToPagePostingData infoToPage) {

                SearchPostingsInfo searchInfo = infoToPage.getSearchInfo();

                return FieldsFindCondition.builder()
                        .boardId(searchInfo.getBoardId())
                        .authorUuid(searchInfo.getAuthorUuid())
                        .pin(searchInfo.getPin())
                        .titleContaining(searchInfo.getTitleContaining())
                        .contentsContaining(searchInfo.getContentContaining())
                        .softDelete(searchInfo.getSoftDelete())
                        .build();
            }

            public static FieldsFindCondition builtFrom(InfoToFeedPostingData infoToFeed) {

                SearchPostingsInfo searchInfo = infoToFeed.getSearchInfo();

                return FieldsFindCondition.builder()
                        .cursorId(infoToFeed.getFeedReq().getCursorId())
                        .boardId(searchInfo.getBoardId())
                        .authorUuid(searchInfo.getAuthorUuid())
                        .pin(searchInfo.getPin())
                        .titleContaining(searchInfo.getTitleContaining())
                        .contentsContaining(searchInfo.getContentContaining())
                        .softDelete(searchInfo.getSoftDelete())
                        .build();
            }
        }


        @Data(staticConstructor = "of")
        public static class SortedFindRange {

            private final Integer limit;
            private final Long offset;
            private final SortBy sortBy;

            @Data(staticConstructor = "of")
            public static class SortBy {
                public final PivotField pivotField;
                private final boolean ascending;

                public enum PivotField {
                    ID,
                    VOTE_COUNT
                }
            }

            public static SortedFindRange of(InfoToPagePostingData pageInfo) {
                PageRequest pageReq = pageInfo.getPageReq();
                return SortedFindRange.of(
                        pageReq.getPageSize(),
                        pageReq.getOffset(),
                        SortBy.of(PivotField.ID, false)
                );
            }

            public static SortedFindRange of(InfoToFeedPostingData feedInfo) {

                Integer feedSize
                        = feedInfo.getFeedReq().getFeedSize();
                SortBy sortBy
                        = SortBy.of(PivotField.ID, false); //todo 프론트단에서 받아오는 정렬정보 여기 넣기

                return SortedFindRange.of(feedSize, null, sortBy);
            }

            public static SortedFindRange NotSpecified() {

                return SortedFindRange.of(0, 0L, null);
            }

        }
    }

}
