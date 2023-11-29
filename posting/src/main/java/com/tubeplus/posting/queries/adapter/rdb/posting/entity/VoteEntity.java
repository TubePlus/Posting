package com.tubeplus.posting.queries.adapter.rdb.posting.entity;


import com.tubeplus.posting.queries.adapter.rdb.common.BaseEntity;
import com.tubeplus.posting.queries.adapter.rdb.posting.VoteTypeConverter;
import com.tubeplus.posting.queries.application.posting.domain.vote.Vote;
import com.tubeplus.posting.queries.application.posting.domain.vote.VoteType;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "vote")
public class VoteEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "posting_id", referencedColumnName = "id", nullable = false)
    private PostingEntity posting;

    @Column(name = "voter_uuid", nullable = false)
    private String voterUuid;

    @Setter
    @Column(name = "vote_type", nullable = false)
    @Convert(converter = VoteTypeConverter.class)
    private VoteType voteType;


    public Vote buildDomain() {

        return Vote.builder()
                .id(id)
                .postingId(posting.getId())
                .voterUuid(voterUuid)
                .voteType(voteType)
                .build();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "createdDateTime = " + getCreatedDateTime() + ", " +
                "updatedDateTime = " + getUpdatedDateTime() + ", " +
                "voterUuid = " + getVoterUuid() + ", " +
                "voteType = " + getVoteType() + ")";
    }

    @Override
    public int hashCode() {
        return Long.hashCode(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VoteEntity vote)) {
            return false;
        }

        return getId() == vote.getId() &&
                getCreatedDateTime().equals(vote.getCreatedDateTime()) &&
                getVoterUuid().equals(vote.getVoterUuid()) &&
                getVoteType().equals(vote.getVoteType());
    }
}
