package biscord.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

@Table(name = "verification_token")
@Entity(name = "VerificationToken")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class VerificationToken {

    /*
        ENUM
            1) UsageType
            2) CurrentState
     */
    public enum UsageType{
        VERIFY_EMAIL, RETRIEVE_PASSWORD
    }

    public enum CurrentState{
        WAITING, VERIFIED, EXPIRED
    }

    /*
        Fields
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private Instant createdAt;

    @Column(
            name = "expired_at",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private Instant expiredAt;

    @Column(
            name = "verified_at",
            columnDefinition = "TIMESTAMP"
    )
    private Instant verifiedAt;

    @Column(name = "token")
    private String token;

    @Column(name = "code")
    private String code;

    @Column(
            //Warning! 'usage' is keyword of MySQL
            name = "usage_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private UsageType usageType;

    @Column(
            name = "cur_state",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private CurrentState currentState;

    /*
        References to Member
     */
    @ManyToOne
    @JoinColumn(
            name = "member_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "verification_token_member_id_fk"),
            nullable = false
    )
    private Member member;


    /*
        Constructors
            1) @NoArgsContructor
            2) @Builder (createdAt, validTimeSpanInSecond, usage, member)
     */
    @Builder
    public VerificationToken(@NonNull Integer validTimeSpanInSecond, @NonNull Member member, @NonNull UsageType usageType) {

        //인증 생성 일시는 엔티티 객체 생성 일시로 정함
        this.createdAt = Instant.now();

        //Non null params
        this.expiredAt = createdAt.plusSeconds(validTimeSpanInSecond);
        this.member = member;
        this.usageType = usageType;
    }


    /*
        ToDo:
            User Defined Methods(include setter like methods)
                1) extends(Integer minutes)
     */


    /*
        Override <Object> methods
            1) @EqualsAndHashCode
            2) toString(): Referenced By 필드 제외
     */

    @Override
    public String toString() {
        return "VerificationToken{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", expiredAt=" + expiredAt +
                ", verifiedAt=" + verifiedAt +
                ", token='" + token + '\'' +
                ", code='" + code + '\'' +
                ", usageType=" + usageType +
                ", member=" + member +
                '}';
    }
}
