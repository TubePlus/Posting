package com.tubeplus.posting.queries.adapter.rdb.posting.entity;

import com.tubeplus.posting.queries.adapter.rdb.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "posting_id", referencedColumnName = "id", nullable = false)
    private PostingEntity posting;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = true)
    private CommentEntity parentComment;

    @Setter
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "commenter_uuid", nullable = false, length = 50)
    private String commenterUuid;

}
