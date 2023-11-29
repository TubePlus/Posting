package com.tubeplus.posting.queries.application.posting.port.in;

import com.tubeplus.posting.queries.application.posting.domain.posting.PostingPageView;
import com.tubeplus.posting.queries.application.posting.domain.posting.PostingFeedData;
import com.tubeplus.posting.queries.application.posting.domain.posting.PostingView;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;


public interface PostingUseCase {

    PostingView readPostingView(long postingId, String userUuid);


    @Data
    @Builder
    class SearchPostingsInfo {
        private final Long boardId;
        private final String authorUuid;
        private final String titleContaining;
        private final String contentContaining;
        private final Boolean pin;
        private final Boolean softDelete;
    }

    Page<PostingPageView> pagePostings(InfoToPagePostingData info);

    @Data(staticConstructor = "of")
    class InfoToPagePostingData {
        private final SearchPostingsInfo searchInfo;
        private final PageRequest pageReq;
    }


    Feed<PostingFeedData> feedPostingData(InfoToFeedPostingData info);

    @Data(staticConstructor = "of")
    class Feed<T> {
        private final List<T> data;
        private final Long lastCursoredId;
        private final boolean hasNextFeed;

        public final <U> Feed<U> map(Function<T, U> mapper) {

            List<U> mappedData = data.stream().map(mapper).collect(toList());

            return Feed.of(mappedData, lastCursoredId, hasNextFeed);
        }
    }

    @Data(staticConstructor = "of")
    class InfoToFeedPostingData {
        private final SearchPostingsInfo searchInfo;
        private final FeedRequest feedReq;
    }

    @Data(staticConstructor = "of")
    class FeedRequest {
        private final Long cursorId;
        private final Integer feedSize;
    }

}