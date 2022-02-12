package nextstep.subway.acceptance.favorite;

import static nextstep.subway.acceptance.auth.AuthStep.*;
import static nextstep.subway.acceptance.favorite.FavoriteStep.*;
import static nextstep.subway.acceptance.member.MemberSteps.*;
import static nextstep.subway.acceptance.station.StationSteps.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.subway.acceptance.AcceptanceTest;
import nextstep.subway.acceptance.line.LineSteps;

@DisplayName("즐겨찾기 관리 기능")
public class FavoriteAcceptanceTest extends AcceptanceTest {
    /**
     *
     * Feature: 즐겨찾기를 관리 한다.
     *
     *   Scenario: 즐겨찾기 관리
     *      Given 로그인이 되어있고
     *      And 출발, 도착 지하철 역이 등록 되어있고
     *      And 지하철 노선이 등록되어 있고
     *      And 지하철 노선에 지하철 역이 등록 되어있다.
     *
     *      When 즐겨찾기 추가를 요청
     *      Then 즐겨찾기 추가됨
     *      When 즐겨찾기 목록 조회 요청
     *      Then 즐겨찾기 목록 조회됨
     *      When 즐겨찾기 삭제 요청
     *      Then 즐겨찾기 삭제됨
     *
     * */
    @DisplayName("즐겨찾기 관리")
    @Test
    void manage() {
        // given
        String accessToken = 회원_생성_하고_로그인_됨("username@username.com", "password1234", 10);
        Long 교대역 = 지하철역_생성_요청_하고_ID_반환("교대역");
        Long 양재역 = 지하철역_생성_요청_하고_ID_반환("양재역");
        LineSteps.지하철_노선_생성_요청(LineSteps.createLineCreateParams(교대역, 양재역));

        // 추가
        Long 즐겨찾기 = 즐겨찾기_추가_요청_하고_ID_반환(
            accessToken, 교대역, 양재역
        );

        // 목록 조회
        즐겨찾기_목록_조회_요청_성공(
            즐겨찾기_목록_조회_요청(accessToken),
            즐겨찾기, 교대역, 양재역
        );

        // 삭제
        즐겨찾기_삭제_요청_성공(
            즐겨찾기_삭제_요청(accessToken, 즐겨찾기)
        );
    }

    /**
     *
     * Feature: 로그인 하지 않은채로 즐겨찾기를 관리에 실패 한다.
     *
     *   Scenario: 즐겨찾기 관리 실패 - 권한 없음
     *      And 출발, 도착 지하철 역이 등록 되어있고
     *      And 지하철 노선이 등록되어 있고
     *      And 지하철 노선에 지하철 역이 등록 되어있다.
     *
     *      When 즐겨찾기 추가를 요청
     *      Then 즐겨찾기 추가 실패
     *      When 즐겨찾기 목록 조회 요청
     *      Then 즐겨찾기 목록 조회 실패
     *      When 즐겨찾기 삭제 요청
     *      Then 즐겨찾기 삭제 실패
     *
     * */
    @DisplayName("즐겨찾기 관리 실패 - 권한이 없을 경우")
    @Test
    void manageFailCase() {
        // given
        Long 교대역 = 지하철역_생성_요청_하고_ID_반환("교대역");
        Long 양재역 = 지하철역_생성_요청_하고_ID_반환("양재역");
        LineSteps.지하철_노선_생성_요청(LineSteps.createLineCreateParams(교대역, 양재역));

        // 추가
        권한_없음(
            즐겨찾기_추가_요청("", 교대역, 양재역)
        );

        // 목록 조회
        권한_없음(
            즐겨찾기_목록_조회_요청("")
        );

        // 삭제
        권한_없음(
            즐겨찾기_삭제_요청("", 1L)
        );
    }
}