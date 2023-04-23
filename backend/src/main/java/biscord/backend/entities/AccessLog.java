package biscord.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Table(name = "access_log")
@Entity(name = "AccessLog")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class AccessLog {

    /*
        Composite primary key
     */
    @EmbeddedId
    private AccessLogId accessLogId;

    @MapsId("memberId")
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "member_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "access_log_member_fk")
    )
    private Member member;

    /*
        Fields
     */
    @Column(
            name = "last_accessed",
            columnDefinition = "TIMESTAMP"
    )
    private Instant lastAccessed;

    @Column(
            name = "is_auth_info_changed",
            nullable = false
    )
    private boolean isAuthInfoChanged;


    /*
        Constructors
            1) @NoArgsConstructor
            2) (member, deviceMac)
     */
    public AccessLog(@NonNull Member member, @NonNull String deviceMac) {
        //Non null param
        this.member = member;
        this.accessLogId = new AccessLogId(member.getId(), deviceMac);

        // 접속 기록 생성 시 마지막 접속 일시는 현재 서버 시간으로 정함
        this.lastAccessed = Instant.now();
        //생성 시 고정 값 할당
        this.isAuthInfoChanged = false;
    }


    /*
        ToDo:
            User Defined Methods(include setter like methods)
                1) justAccessed()
     */


    /*
        Override <Object> method
            1) @EqualsAndHashCode
            2) toString(): Referenced By 필드 제외
     */
    @Override
    public String toString() {
        return "AccessLog{" +
                "accessLogId=" + accessLogId +
                ", lastAccessed=" + lastAccessed +
                ", isAuthInfoChanged=" + isAuthInfoChanged +
                '}';
    }
}
