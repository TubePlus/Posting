package com.tubeplus.posting.queries.adapter.kafka.posting;

import com.tubeplus.posting.queries.application.posting.domain.posting.Posting;
import com.tubeplus.posting.queries.application.posting.port.out.PostingEventPublishable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PostingKafkaAdapter implements PostingEventPublishable {
    @Override
    public void publishPostingRead(Posting posting) {
    }
}
