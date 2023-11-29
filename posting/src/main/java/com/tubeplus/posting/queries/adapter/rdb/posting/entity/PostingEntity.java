package com.tubeplus.posting.queries.adapter.rdb.posting.entity;

import com.tubeplus.posting.queries.adapter.rdb.board.BoardEntity;
import com.tubeplus.posting.queries.adapter.rdb.common.BaseEntity;
import com.tubeplus.posting.queries.application.posting.domain.posting.Posting;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "posting")
public class PostingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "author_uuid", nullable = false, length = 50)
    private String authorUuid;

    @Column(name = "vote_count", nullable = false)
    @Builder.Default
    private long voteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "board_posting_list",
            joinColumns = @JoinColumn(name = "posting_id"),
            inverseJoinColumns = @JoinColumn(name = "board_id"))
    private BoardEntity board;

    @Column(name = "pin", nullable = false)
    private boolean pin;

    @Column(name = "contents", nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "soft_delete", nullable = false)
    private boolean softDelete;

    @Column(name = "with_image", nullable = false)
    private boolean withImage;


    public static PostingEntity builtFrom(Posting posting, BoardEntity postingBoard) {

        return PostingEntity.builder()
                .id(posting.getId())
                .authorUuid(posting.getAuthorUuid())
                .voteCount(posting.getVoteCount())
                .board(postingBoard)
                .pin(posting.isPin())
                .contents(posting.getContents())
                .title(posting.getTitle())
                .softDelete(posting.isSoftDelete())
                .withImage(posting.isWithImage())
                .build();
    }


    public Posting buildDomain() {

        return Posting.builder()
                .id(id)
                .authorUuid(authorUuid)
                .voteCount(voteCount)
                .boardId(board.getId())
                .pin(pin)
                .contents(contents)
                .title(title)
                .softDelete(softDelete)
                .withImage(withImage)
                .build();
    }


}