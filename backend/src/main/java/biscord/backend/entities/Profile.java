package biscord.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Table(
        name = "profile",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "profile_avatar_filename_uk",
                        columnNames = "avatar_filename"
                )
        }
)
@Entity(name = "Profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Profile {

    /*
        PFK
     */
    @Id
    @Column(name = "member_id")
    private Long id;

    @MapsId
    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "member_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "profile_member_id_fk"),
            nullable = false
    )
    private Member member;

    /*
        Fields
     */
    @Column(name = "avatar_filename")
    private String avatarFilename;

    @Column(
            name = "banner_rgb_val",
            nullable = false
    )
    private Integer bannerRGBValue;

    @Column(
            name = "introduce",
            length = 190
    )
    private String introduce;

    /*
        Constructors
            1) @NoArgsConstructor
            2) (member)
     */
    public Profile(@NonNull Member member) {
        //non null params
        this.member = member;

        //생성 시 고정 값 할당
        this.avatarFilename = null;
        this.introduce = "";
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        this.bannerRGBValue = random.nextInt(0xFFFFFF);
    }


    /*
        ToDo:
            User Defined methods(include setter like methods)
     */


    /*
        Override <Object> method
            1) @EqualsAndHashCode
            2) toString(): Referenced By 필드 제외
     */
    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", member=" + member +
                ", avatarFilename='" + avatarFilename + '\'' +
                ", bannerRGBValue=" + bannerRGBValue +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
