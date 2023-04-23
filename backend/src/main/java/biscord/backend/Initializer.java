package biscord.backend;

import biscord.backend.entities.AccessLog;
import biscord.backend.entities.Member;
import biscord.backend.entities.Profile;
import biscord.backend.repositories.AccessLogRepository;
import biscord.backend.repositories.MemberRepository;
import biscord.backend.repositories.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDate;

@Component
public class Initializer implements CommandLineRunner {

    /*
        DI
            1) MemberRepository
     */
    private final MemberRepository memberRepository;
    private final AccessLogRepository accessLogRepository;
    private final ProfileRepository profileRepository;

    public Initializer(
            MemberRepository memberRepository,
            ProfileRepository profileRepository,
            AccessLogRepository accessLogRepository
    ) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
        this.accessLogRepository = accessLogRepository;
    }

    /*
        @Override
            1) CommandLineRunner.run(String ... args)
     */
    @Override
    public void run(String... args) throws Exception {
        SimpleEntityCrudTest();
    }

    private void SimpleEntityCrudTest() {
        //테스트 멤버 엔티티 생성
        Member tester = Member.builder()
                .email("test@test.com")
                .encryptedPassword("ODWNqowidn1o3if09")
                .name("Tester")
                .code(9999)
                .birthDate(LocalDate.of(1997, 9, 29))
                .build();

        //프로필 엔티티 생성
        Profile testerProfile = new Profile(tester);

        //로컬 MAC 주소를 가져오기
        String stringMac = getLocalMac();

        //접속 기록 엔티티 생성
        AccessLog accessLog;
        accessLog = new AccessLog(tester, stringMac);

        //데이터베이스에 저장
        //1:1관계에서 자식 엔티티를 save 하면 알아서 부모 엔티티를 먼저 save 해준다
        profileRepository.save(testerProfile);
        //1:n 관계에서 n(자식) 쪽 엔티티를 save 하기 전 반드시 부모 엔티티를 먼저 save해야 한다.(안 해줌)
        accessLogRepository.save(accessLog);
    }

    private String getLocalMac() {
        InetAddress ip;
        String stringMac;
        AccessLog accessLog;
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);
            byte[] byteArrMac = networkInterface.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteArrMac.length; i++) {
                sb.append(String.format("%02X%s", byteArrMac[i], (i < byteArrMac.length - 1) ? ":" : ""));
            }
            stringMac = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            stringMac = "1234567890AB";
        }
        return stringMac;
    }
}