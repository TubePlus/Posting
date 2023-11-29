package com.tubeplus.posting.queries.application.posting.port.out;

import com.tubeplus.posting.queries.application.posting.domain.posting.Posting;

public interface PostingEventPublishable {
    void publishPostingRead(Posting posting);
}
