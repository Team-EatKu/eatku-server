package eatku.eatkuserver.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    // User
    USER_LOGIN_SUCCESS(200, "U001", "로그인에 성공했습니다"),
    USER_JOIN_SUCCESS(200, "U002", "회원가입에 성공했습니다."),
    USER_EMAIL_VALIDATE(200, "U003", "중복되지 않는 유효한 이메일입니다."),
    EMAIL_SEND_SUCCESS(200, "U004", "이메일 발송에 성공하였습니다."),
    EMAIL_AUTH_SUCCESS(200, "U005", "이메일 인증에 성공하였습니다."),
    USER_SEARCH_LIST_GET_SUCCESS(200, "U008", "이름에 해당 문자열이 포함되는 유저 리스트를 불러왔습니다."),
    USER_SEARCH_GET_SUCCESS(200, "U008", "이름에 해당하는 유저의 정보를 불러왔습니다."),
    EMAIL_CHECK_SUCCESS(200, "U009", "사용 가능한 이메일입니다."),
    NICKNAME_CHECK_SUCCESS(200, "U010", "사용 가능한 닉네임입니다."),
    USER_LIKELIST_SUCCESS(200, "U011", "사용자의 찜하기 목록을 불러오는데 성공하였습니다."),
    USER_REVIEWLIST_SUCCESS(200, "U012", "사용자의 리뷰 목록을 불러오는데 성공하였습니다."),
    USER_PROFILE_MODIFY_SUCCESS(200, "U013", "사용자의 프로필 사진 변경에 성공하였습니다."),

    // Restaurant
    RESTAURANT_REGISTER_SUCCESS(200, "R001", "식당 정보 등록에 성공하였습니다."),
    RESTAURANT_SEARCH_SUCCESS(200, "R002", "식당 검색에 성공하였습니다."),
    RESTAURANT_INFORMATION_SUCCESS(200, "R003", "식당 조회에 성공하였습니다."),
    RESTAURANT_RECOMMEND_SUCCESS(200, "R004", "식당 추천에 성공하였습니다."),

    // Review
    REVIEW_REGISTER_SUCCESS(200, "V001", "리뷰 등록에 성공하였습니다."),
    REVIEW_MODIFY_SUCCESS(200,"V002" ,"리뷰 수정에 성공하였습니다." ),
    REVIEW_DELETE_SUCCESS(200, "V003", "리뷰 삭제에 성공하였습니다."),

    // Like
    LIKE_SAVE_OR_DELETE_SUCCESS(200, "L001", "찜하기 작업에 성공하였습니다."),
    LIKE_USER_LIST_GET_SUCCESS(200, "L003", "좋아요 누른 유저 목록을 불러왔습니다.");

    private final int status;
    private final String code;
    private final String message;
}
