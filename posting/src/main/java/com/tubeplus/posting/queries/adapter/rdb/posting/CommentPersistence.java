package com.tubeplus.posting.queries.adapter.rdb.posting;

import com.tubeplus.posting.queries.adapter.rdb.posting.dao.CommentJpaDataRepository;
import com.tubeplus.posting.queries.adapter.rdb.posting.entity.CommentEntity;
import com.tubeplus.posting.queries.application.posting.domain.comment.Comment;
import com.tubeplus.posting.queries.application.posting.port.out.CommentPersistable;
import com.tubeplus.posting.queries.global.Exceptionable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Component
public class CommentPersistence implements CommentPersistable {

    private final CommentJpaDataRepository commentJpaDataRepo;


    @Override
    public Exceptionable<Long, Long> countComments(long postingId) {

        return Exceptionable.act(commentJpaDataRepo::countByPostingId, postingId);
    }

    protected final CommentEntity getValidParent(Long parentId, long postingId) {

        CommentEntity parentComment
                = commentJpaDataRepo.findById(parentId)
                .orElseThrow(() -> new RuntimeException("parent comment is not found."));

        if (parentComment.getPosting().getId() != postingId) {
            throw new RuntimeException("postingId between parent and child is not matched.");
        }
        return parentComment;
    }


    @Transactional
    @Override
    public Exceptionable<List<Comment>, FindCommentDto> findComments(FindCommentDto findDto) {

        return Exceptionable.act(dto -> {

            CommentEntity parentComment =
                    dto.isFindingChildren()
                            ? getValidParent(dto.getParentId(), dto.getPostingId())
                            : null;

            List<CommentEntity> foundEntities
                    = commentJpaDataRepo.findByPostingIdAndParentComment(
                    dto.getPostingId(), parentComment);

            List<Comment> foundComments
                    = foundEntities
                    .stream()
                    .map(this::entityToDomain)
                    .toList();

            return foundComments;

        }, findDto);
    }

    protected Comment entityToDomain(CommentEntity entity) {

        Long parentId =
                entity.getParentComment() == null
                        ? null
                        : entity.getParentComment().getId();


        boolean hasChild
                = entity.getParentComment() == null
                && commentJpaDataRepo.findFirstByParentComment(entity).isPresent();


        return Comment.of(
                entity.getId(),
                entity.getPosting().getId(),
                Comment.CommentViewInfo.builder()
                        .parentId(parentId)
                        .hasChild(hasChild)
                        .content(entity.getContent())
                        .commenterUuid(entity.getCommenterUuid())
                        .build()
        );
    }


}
