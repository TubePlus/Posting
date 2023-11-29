package com.tubeplus.posting.queries.adapter.web.controller;


import com.tubeplus.posting.queries.adapter.web.common.ApiResponse;
import com.tubeplus.posting.queries.adapter.web.common.ApiTag;
import com.tubeplus.posting.queries.application.posting.domain.comment.Comment;
import com.tubeplus.posting.queries.application.posting.port.in.WebCommentUseCase;
import com.tubeplus.posting.queries.application.posting.port.in.WebCommentUseCase.ReadCommentsInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

@Validated
@ApiTag(path = "/api/v1/board-service/comments", name = "Posting Comment API")
public class CommentController {

    private final WebCommentUseCase commentService;


    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // queries
    @Operation(summary = "댓글/대댓글 조회",
            description = "대댓글일 경우 parentId를 입력, 원 댓글일 경우 parentId에 null")
    @GetMapping()
    public ApiResponse<List<Comment>> readComments
            (
                    @RequestParam("posting-id") @Min(1)
                    long postingId,
                    @RequestParam(name = "parent-id", required = false) @Min(1)
                    Long parentId
            ) {

        ReadCommentsInfo readInfo
                = ReadCommentsInfo.of(postingId, parentId);

        List<Comment> comments
                = commentService.readComments(readInfo);

        return ApiResponse.ofSuccess(comments);
    }


}
