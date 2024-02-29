package eatku.eatkuserver.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum Recommendation {
    RECOMMENDATION1(1, "가벼운 식사를 원한다면?", "혼밥"),
    RECOMMENDATION2(2, "친구들과 함께..좋시좋사..", "회식"),
    RECOMMENDATION3(3, "우리,,@@ 동년배들,,, 다 여기서 공부한다", "카공"),
    RECOMMENDATION4(4, "1차로 어디가지?", "회식"),
    RECOMMENDATION5(5, "여기가면 여자친구가 생긴다고??!(교배해요)", "데이트");

    private final int code;
    private final String title;
    private final String hashtag;

    private static final Map<Integer, String> CODE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Recommendation::getCode, Recommendation::name))
    );

    public static Recommendation of(int code){
        return Recommendation.valueOf(CODE_MAP.get(code));
    }

}
