package eatku.eatkuserver.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode
{
    // User
    USER_NOT_FOUND(400, "U001", "유저가 존재하지 않습니다."),
    NOT_CORRECT_PASSWORD(400, "U002", "비밀번호가 틀렸습니다."),
    ALREADY_EXIST_NICKNAME(400, "U003", "이미 사용 중인 닉네임입니다."),
    ALREADY_EXIST_EMAIL(400, "U004", "이미 있는 이메일입니다."),
    NOT_EQUAL_PASSWORD(400, "U005", "비밀번호가 일치하지 않습니다."),
    MAIL_AUTH_FAILED(400, "U006", "메일 인증에 실패하였습니다."),
    USER_REGISTER_FAILED(400, "U007", "회원가입에 실패하였습니다."),

    // Authority
    AUTHENTICATION_FAILED(400, "A001", "인증되지 않은 사용자입니다."),
    AUTHORIZATION_FAILED(400, "A002", "인가되지 않은 사용자입니다."),

    // Restaurant
    RESTAURANT_NOT_FOUND(400, "R001", "존재하지 않는 식당입니다."),
    IMAGE_UPLOAD_FAILED(400, "R002", "식당 사진 업로드에 실패하였습니다."),
    RESTAURANT_SEARCH_FAILED(400, "R003", "식당 검색에 실패하였습니다."),
    ILLEGER_SEARCH_PARAMETER(400, "R004", "식당 검색에 필요한 정보가 부족합니다."),

    // Review
    REVIEW_NOT_FOUNT(400, "V001", "존재하지 않는 리뷰입니다."),
    USER_REVIEW_NOT_MATCH(400, "V002", "리뷰 작성자와 일치하지 않습니다."),
    ILLEGAR_REVIEW_SCOPE(400, "V003", "잘못된 리뷰 점수입니다."),
    ILLEGAR_CONTENT_SIZE(400, "V004", "글자수 제한을 넘었습니다."),

    // Likes,
    CAN_NOT_LIKE_POSTS(400, "L001", "좋아요를 누를 수 없습니다."),
    CAN_NOT_UNLIKE_POSTS(400, "L002", "좋아요를 취소할 수 없습니다."),

    // 잘못된 API 요청
    NOT_FOUNT_API(404, "X001", "잘못된 API 요청입니다."),
    SERVER_ERROR(505, "S001", "서버 에러"),

    // location 조작 방지
    NOT_FOUND_LOCATION(400, "O001", "잘못된 위치 입력입니다.");

    private final int status;
    private final String code;
    private final String message;
}
