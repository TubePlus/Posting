package com.tubeplus.posting.queries.adapter.web.controller;

import com.tubeplus.posting.queries.adapter.web.common.ApiResponse;
import com.tubeplus.posting.queries.adapter.web.common.ApiTag;
import com.tubeplus.posting.queries.adapter.web.controller.vo.PostingsSearchTypeReq;
import com.tubeplus.posting.queries.adapter.web.controller.vo.PostingsViewTypeReq;
import com.tubeplus.posting.queries.adapter.web.controller.vo.VoReadPostingSimpleData;
import com.tubeplus.posting.queries.adapter.web.error.BusinessException;
import com.tubeplus.posting.queries.adapter.web.error.ErrorCode;
import com.tubeplus.posting.queries.application.posting.domain.posting.PostingView;
import com.tubeplus.posting.queries.application.posting.port.in.PostingUseCase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

@Validated
@ApiTag(path = "/api/v1/board-service/postings", name = "Posting API")
public class PostingController {


    private final PostingUseCase postingService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }


    // Read
    @Operation(summary = "게시판내 게시물 목록 정보로 조회", description = "제목, 고정글 여부등의 간단한 정보 목록 조회")
    @GetMapping()
    public ApiResponse<VoReadPostingSimpleData.Res> readPostingSimpleData
    (
            @RequestParam("search-type-req") @NotNull PostingsSearchTypeReq searchType,
            @RequestParam("view-type-req") @NotNull PostingsViewTypeReq viewType,
            VoReadPostingSimpleData.Req reqParam
    ) {

        if (searchType.checkBadRequest(reqParam) || viewType.checkBadRequest(reqParam))
            throw new BusinessException(ErrorCode.BAD_REQUEST);

        VoReadPostingSimpleData.Res responseVo
                = viewType.driveService(postingService, reqParam);

        return ApiResponse.ofSuccess(responseVo);
    }


    @Operation(summary = "id로 게시물 읽기", description = "게시물 id로 개별 게시물 조회 및 읽기(조회수 반영등) 처리")
    @GetMapping("/{id}")
    public ApiResponse<PostingView> readPosting
            (
                    @PathVariable("id") @Min(1) long id,
                    @RequestParam("user-uuid") @NotBlank String userUuid
            ) {

        PostingView postingView
                = postingService.readPostingView(id, userUuid);

        return ApiResponse.ofSuccess(postingView);
    }

}
