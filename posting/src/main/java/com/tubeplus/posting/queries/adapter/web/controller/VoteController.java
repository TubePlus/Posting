package com.tubeplus.posting.queries.adapter.web.controller;


import com.tubeplus.posting.queries.adapter.web.common.ApiResponse;
import com.tubeplus.posting.queries.adapter.web.common.ApiTag;
import com.tubeplus.posting.queries.application.posting.port.in.WebVoteUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

@Validated
@ApiTag(path = "/api/v1/board-service/votes", name = "Posting Vote API")
public class VoteController {

    private final WebVoteUseCase voteService;

    @GetMapping("/test")
    public ApiResponse<String> test() {
        return ApiResponse.ofSuccess("test");
    }

}
