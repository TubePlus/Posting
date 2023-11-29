package com.tubeplus.posting.queries.adapter.web.controller.vo;

import com.tubeplus.posting.queries.application.posting.domain.posting.PostingFeedData;
import com.tubeplus.posting.queries.application.posting.domain.posting.PostingPageView;
import com.tubeplus.posting.queries.application.posting.port.in.PostingUseCase;
import com.tubeplus.posting.queries.application.posting.port.in.PostingUseCase.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public enum PostingsViewTypeReq {

    FEED {
        public boolean checkBadRequest(VoReadPostingSimpleData.Req reqParam) {
            return reqParam.getFeedSize() == null || reqParam.getFeedSize() <= 0;
        }

        public VoReadPostingSimpleData.Res driveService(PostingUseCase postingService,
                                                        VoReadPostingSimpleData.Req reqParam) {

            Feed<PostingFeedData> feedDataList
                    = postingService.feedPostingData(reqParam.newInfoToFeed());

            return VoReadPostingSimpleData.Res.of(feedDataList);
        }
    },

    PAGE {
        public boolean checkBadRequest(VoReadPostingSimpleData.Req reqParam) {

            return reqParam.getPageIndex() == null || reqParam.getPageIndex() < 0
                    || reqParam.getPageSize() == null || reqParam.getPageSize() <= 0;
        }

        public VoReadPostingSimpleData.Res driveService(PostingUseCase postingService,
                                                        VoReadPostingSimpleData.Req reqParam) {

            Page<PostingPageView> pagedPosting
                    = postingService.pagePostings(reqParam.newInfoToPage());

            return VoReadPostingSimpleData.Res.of(pagedPosting);
        }
    };


    public abstract boolean checkBadRequest(VoReadPostingSimpleData.Req reqParam);

    public abstract VoReadPostingSimpleData.Res driveService(PostingUseCase postingService,
                                                             VoReadPostingSimpleData.Req reqParam);


    public static class PostingsViewTypeReqConverter implements Converter<String, PostingsViewTypeReq> {

        @Override
        public PostingsViewTypeReq convert(String name) {
            return valueOf(name);
        }

    }
}