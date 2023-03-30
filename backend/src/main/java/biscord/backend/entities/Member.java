package biscord.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Table(
        name = "member",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "member_email_uk",
                        columnNames = "email"
                ),
                @UniqueConstraint(
                        name = "member_name_code_composite_uk",
                        columnNames = {"name", "code"}
                )
        }
)
@Entity(name = "Member")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode

public class Member {

    /*
        Enum: CurrentState
     */
    public enum CurrentState{
        ONLINE, AWAY, BUSY, OFFLINE
    }

    /*
        Fields
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @Column(
            name = "code",
            nullable = false
    )
    private Integer code;

    @Column(
            name = "name",
            length = 32,
            nullable = false
    )
    private String name;

    @Column(
            name = "enc_passwd",
            nullable = false
    )
    private String encryptedPassword;

    @Column(
            name = "birth_date",
            nullable = false
    )
    private LocalDate birthDate;

    @Column(
            name = "created_at",
            columnDefinition = "DATETIME",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "is_email_verified",
            nullable = false
    )
    private boolean isEmailVerified;

    @Column(
            name = "is_activated",
            nullable = false
    )
    private boolean isActivated;

    @Column(
            name = "cur_state",
            nullable = false
    )
    private CurrentState currentState;

    /*
        Referenced By
            1) verificationToken
            2) Profile
            3) AccessLog
     */
    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<VerificationToken> verificationTokens = new ArrayList<>();

    @OneToOne(
            mappedBy = "member",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Profile profile;

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<AccessLog> accessLogs = new ArrayList<>();

    /*
        Constructors
            1) @NoArgsConstructor
            2) @Builder (email, code, name, encryptedPassword, birthDate, createdAt)
     */
    @Builder
    public Member(@NonNull String email, @NonNull String name, @NonNull Integer code,
                  @NonNull String encryptedPassword, @NonNull LocalDate birthDate) {

        //Non null params
        this.email = email;
        this.name = name;
        this.code = code;
        this.encryptedPassword = encryptedPassword;
        this.birthDate = birthDate;

        //가입 일시는 엔티티 객체 생성 일시로 정함
        this.createdAt = LocalDateTime.now();

        //생성 시 고정 값 할당
        this.isEmailVerified = false;
        this.isActivated = true;
        this.currentState = CurrentState.OFFLINE;
    }


    /*
        ToDo:
            User Defined Methods(include setter like methods)
     */


    /*
        ToDo:
            Implements UserDetails
     */


    /*
        Override <Object> methods
            1) @EqualsAndHashCode
            2) toString(): Referenced By 필드 제외
     */
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", birthDate=" + birthDate +
                ", createdAt=" + createdAt +
                ", isEmailVerified=" + isEmailVerified +
                ", isActivated=" + isActivated +
                ", currentState=" + currentState +
                '}';
    }

}
