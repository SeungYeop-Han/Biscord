package biscord.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class AccessLogId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "device_mac")
    private String deviceMac;

    /*
        생성자
        1) @NoArgsConstructor
        2) (Long memberId, String deviceMac)
     */
    public AccessLogId(Long memberId, String deviceMac) {
        this.memberId = memberId;
        this.deviceMac = deviceMac;
    }
}
