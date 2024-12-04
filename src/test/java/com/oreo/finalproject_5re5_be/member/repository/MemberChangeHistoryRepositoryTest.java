package com.oreo.finalproject_5re5_be.member.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.oreo.finalproject_5re5_be.code.entity.Code;
import com.oreo.finalproject_5re5_be.code.repository.CodeRepository;
import com.oreo.finalproject_5re5_be.member.entity.Member;
import com.oreo.finalproject_5re5_be.member.entity.MemberChangeHistory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberChangeHistoryRepositoryTest {

    @Autowired
    private MemberChangeHistoryRepository memberChangeHistoryRepository;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private MemberRepository memberRepository;

    private List<MemberChangeHistory> dummy = new ArrayList<>();

    private Member member;
    private Code code;

    /**
     * 초기화 작업
     * - 자동 주입 확인
     * - 더미 데이터, DB 테이블 초기화
     * - 더미 데이터 생성 및 저장
     * - 픽스처 생성 및 확인
     *  - 1. 코드 - 회원 아이디 필드 (MF001)
     *  - 2. 회원
     */
    @BeforeEach
    void setUp() {
        assertNotNull(memberChangeHistoryRepository);
        assertNotNull(memberRepository);
        assertNotNull(codeRepository);

        dummy.clear();
        memberRepository.deleteAll();
        codeRepository.deleteAll();
        memberChangeHistoryRepository.deleteAll();

        createCode();
        createMember();
        createDummy();

        assertNotNull(code);
        assertNotNull(member);
    }

    /**
     * 회원 변경 이력 테스트 목록
     * - 성공/실패하는 경우에 대해서 테스트 코드 작성
     *
     * 1. 회원 변경 이력 생성
     *    - 성공
     *      1. 회원 변경 이력 생성에 성공함
     *    - 실패
     *      1. 테이블 제약 조건 위배로 회원 변경 이력 생성 실패
     *      2. 회원 ID가 존재하지 않아 회원 변경 이력 생성 실패
     *      3. 데이터 입력 형식 오류로 회원 변경 이력 생성 실패
     *
     * 2. 회원 변경 이력 조회
     *    2-1. 특정 회원 ID로 조회
     *       - 성공
     *         1. 특정 회원 ID로 조회 성공
     *       - 실패
     *         1. 없는 회원 ID로 조회 실패
     *    2-2. 모든 회원 변경 이력 조회
     *       - 성공
     *         1. 모든 회원 변경 이력 조회 성공
     *       - 실패
     *         1. 데이터베이스 연결 문제로 조회 실패
     *
     * 3. 회원 가장 최근 수정
     *    - 성공
     *      1. 회원 가장 최근 수정 내역 업데이트 성공
     *    - 실패
     *      1. 테이블 제약 조건 위배로 최근 수정 실패
     *      2. 회원 ID가 존재하지 않아 최근 수정 실패
     *      3. 데이터 입력 형식 오류로 최근 수정 실패
     *
     * 4. 회원 변경 이력 삭제
     *    - 성공
     *      1. 특정 회원 변경 이력 삭제 성공
     *    - 실패
     *      1. 없는 회원 변경 이력을 삭제하려고 할 때 삭제 실패
     *      2. 데이터베이스 연결 문제로 삭제 실패
     */

    @Test
    @DisplayName("1. 회원 변경 이력 생성 - 1. 회원 변경 이력 생성에 성공함")
    void 회원_변경_이력_생성_성공() {
        // 회원이 아이디를 변경함
        // 회원 생성, 코드(회원 아이디 필드) 생성은 이미 되어 있음

        // 회원 아이디 변경
        // 변경 반영 잘 되었는지 확인
        // 회원 변경 이력 생성
        // Repository를 통해 저장
        // 저장된 데이터를 조회
        // 변경된 값 내용 비교

        String originId = member.getId();
        String newId = "new qwerfde2312";
        member.setId(newId);
        Member savedMember = memberRepository.save(member);
        assertNotNull(savedMember);

        MemberChangeHistory target = dummy.get(0);
        target.setBefVal(originId);
        target.setAftVal(newId);
        MemberChangeHistory savedHistory = memberChangeHistoryRepository.save(target);
        assertNotNull(savedHistory);

        assertEquals(target.getBefVal(), savedHistory.getBefVal());
        assertEquals(target.getAftVal(), savedHistory.getAftVal());
    }

    @Test
    @DisplayName("1. 회원 변경 이력 생성 - 1. 테이블 제약 조건 위배로 회원 변경 이력 생성 실패")
    void 회원_변경_이력_제약조건_생성_실패() {
        // 회원이 아이디를 변경함
        // 회원 생성, 코드(회원 아이디 필드) 생성은 이미 되어 있음


    }

    @Test
    @DisplayName("1. 회원 변경 이력 생성 - 2. 회원 ID가 존재하지 않아 회원 변경 이력 생성 실패")
    void 회원_변경_이력_ID_없음_생성_실패() {
        // 이 부분은 여기서 작성할 필요 없음. 서비스에서 처리할 로직
    }

    @Test
    @DisplayName("1. 회원 변경 이력 생성 - 3. 데이터 입력 형식 오류로 회원 변경 이력 생성 실패")
    void 회원_변경_이력_형식오류_생성_실패() {
        // 이 부분은 여기서 작성할 필요 없음. 이미 Member 엔티티에서 데이터 입력 형식을 제한하고 있음
    }

    @Test
    @DisplayName("2. 회원 변경 이력 조회 - 2-1. 특정 회원 ID로 조회 - 1. 특정 회원 ID로 조회 성공")
    void 회원_변경_이력_ID_조회_성공() {
        // 회원이 아이디를 변경함
        // 회원 생성, 코드(회원 아이디 필드) 생성은 이미 되어 있음

        // 이미 등록된 회원에 여러개의 변경 이력 등록함
        // 특정 회원 ID로 조회
        // 조회된 결과가 맞는지 확인
    }

    @Test
    @DisplayName("2. 회원 변경 이력 조회 - 2-1. 특정 회원 ID로 조회 - 1. 없는 회원 ID로 조회 실패")
    void 회원_변경_이력_ID_조회_실패() {
        // 회원이 아이디를 변경함
        // 회원 생성, 코드(회원 아이디 필드) 생성은 이미 되어 있음

        // 이미 등록된 회원에 여러개의 변경 이력 등록함
        // 존재하지 않는 회원 ID로 조회
        // 조회된 결과가 맞는지 확인
    }

    @Test
    @DisplayName("2. 회원 변경 이력 조회 - 2-2. 모든 회원 변경 이력 조회 - 1. 모든 회원 변경 이력 조회 성공")
    void 회원_변경_이력_전체조회_성공() {
        // 회원이 아이디를 변경함
        // 회원 생성, 코드(회원 아이디 필드) 생성은 이미 되어 있음

        // 여러 회원을 등록함
        // 각 회원마다 여러개의 변경 이력 등록함
        // 모든 회원 변경 이력 조회
    }

    @Test
    @DisplayName("2. 회원 변경 이력 조회 - 2-2. 모든 회원 변경 이력 조회 - 1. 데이터베이스 연결 문제로 조회 실패")
    void 회원_변경_이력_전체조회_DB문제_실패() {
        // 여기서 테스트할 필요 없음
    }

    @Test
    @DisplayName("3. 회원 가장 최근 수정 - 1. 회원 가장 최근 수정 내역 업데이트 성공")
    void 회원_가장최근수정_성공() {

    }

    @Test
    @DisplayName("3. 회원 가장 최근 수정 - 1. 테이블 제약 조건 위배로 최근 수정 실패")
    void 회원_가장최근수정_제약조건_실패() {

    }

    @Test
    @DisplayName("3. 회원 가장 최근 수정 - 2. 회원 ID가 존재하지 않아 최근 수정 실패")
    void 회원_가장최근수정_ID없음_실패() {

    }

    @Test
    @DisplayName("3. 회원 가장 최근 수정 - 3. 데이터 입력 형식 오류로 최근 수정 실패")
    void 회원_가장최근수정_형식오류_실패() {

    }

    @Test
    @DisplayName("4. 회원 변경 이력 삭제 - 1. 특정 회원 변경 이력 삭제 성공")
    void 회원_변경_이력_삭제_성공() {

    }

    @Test
    @DisplayName("4. 회원 변경 이력 삭제 - 1. 없는 회원 변경 이력을 삭제하려고 할 때 삭제 실패")
    void 회원_변경_이력_삭제_실패_없는이력() {

    }

    @Test
    @DisplayName("4. 회원 변경 이력 삭제 - 2. 데이터베이스 연결 문제로 삭제 실패")
    void 회원_변경_이력_삭제_DB문제_실패() {

    }


    @Test
    @DisplayName("회원 가장 최근 변경 이력 조회")
    void 회원_가장_최근_변경_이력_조회() {

        // 변경 이력 여러개를 등록함
        List<MemberChangeHistory> memberChangeHistories = memberChangeHistoryRepository.saveAll(dummy);
        assertEquals(dummy.size(), memberChangeHistories.size());


        // 가장 최근 이력을 조회하는 쿼리 실행
        MemberChangeHistory foundLatestHistory = memberChangeHistoryRepository.findLatestHistoryByIdAndCode(member.getSeq(),code.getCode()).get();

        // 결과 비교
        assertNotNull(foundLatestHistory);
        assertEquals(dummy.get(2).getAftVal(), foundLatestHistory.getAftVal());
        System.out.println("foundLatestHistory = " + foundLatestHistory);
    }

    private void createCode() {
        Code dummy = Code.builder()
                        .codeSeq(1L)
                        .cateNum("MB")
                        .code("MF001")
                        .name("회원아이디필드")
                        .ord(1)
                        .chkUse("Y")
                        .comt("회원 아이디 필드입니다.")
                        .build();

        Code savedCode = codeRepository.save(dummy);
        assertNotNull(savedCode);
        code = savedCode;
    }

    private void createMember() {
        Member dummy = Member.builder()
                            .id("qwerfde2312")
                            .password("asdf12341234@")
                            .email("qwefghnm1212@gmail.com")
                            .name("홍길동")
                            .normAddr("서울시 강남구")
                            .locaAddr("서울시")
                            .detailAddr("서초동 123-456")
                            .passAddr("서초대로 59-32")
                            .chkValid('Y')
                            .build();
        Member savedMember = memberRepository.save(dummy);
        assertNotNull(savedMember);
        member = savedMember;
    }

    private void createDummy() {
        // 현재 시간과 최대 시간 세팅
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.MAX;

        // DATETIME 형식으로 변환하기 위한 포맷터 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 포맷팅된 문자열로 변환
        String formattedDateTime = now.format(formatter);
        String formattedEnd = end.format(formatter);


        // 더미 데이터 생성
        dummy.add(MemberChangeHistory.builder()
                                    .member(member)
                                    .chngFieldCode(code)
                                    .befVal("qwerfde2312")
                                    .aftVal("(1)new qwerfde2312")
                                    .chngFieldCode(code)
                                    .applDate(formattedDateTime)
                                    .endDate(formattedEnd)
                                    .build());

        dummy.add(MemberChangeHistory.builder()
                                    .member(member)
                                    .chngFieldCode(code)
                                    .befVal("(1)new qwerfde2312")
                                    .aftVal("(2)new qwerfde2312")
                                    .chngFieldCode(code)
                                    .applDate(formattedDateTime)
                                    .endDate(formattedEnd)
                                    .build());

        dummy.add(MemberChangeHistory.builder()
                                    .member(member)
                                    .chngFieldCode(code)
                                    .befVal("(2)new qwerfde2312")
                                    .aftVal("(3)new qwerfde2312")
                                    .chngFieldCode(code)
                                    .applDate(formattedDateTime)
                                    .endDate(formattedEnd)
                                    .build());
    }
}