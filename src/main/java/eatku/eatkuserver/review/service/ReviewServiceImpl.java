package eatku.eatkuserver.review.service;

import eatku.eatkuserver.global.error.ErrorCode;
import eatku.eatkuserver.global.error.exception.EntityNotFoundException;
import eatku.eatkuserver.restaurant.domain.Restaurant;
import eatku.eatkuserver.restaurant.repository.RestaurantRepository;
import eatku.eatkuserver.review.domain.Review;
import eatku.eatkuserver.review.dto.ReviewModifyRequestDto;
import eatku.eatkuserver.review.dto.ReviewRegisterRequestDto;
import eatku.eatkuserver.review.repository.ReviewRepository;
import eatku.eatkuserver.s3.service.S3Service;
import eatku.eatkuserver.user.domain.User;
import eatku.eatkuserver.user.repository.UserRepository;
import eatku.eatkuserver.user.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final S3Service s3Service;

    @Override
    @Transactional
    public String addReview(ReviewRegisterRequestDto request, String token, List<MultipartFile> images) {
        String userEmail = jwtProvider.getAccount(token);

        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND, "올바르지 않은 접근입니다.")
        );

        Restaurant restaurant = restaurantRepository.findRestaurantById(request.getRestaurantId()).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.RESTAURANT_NOT_FOUND, "올바르지 않은 접근입니다.")
        );

        Review review = new Review();

        review.setUser(user);
        review.setScope(request.getScope());
        review.setContent(request.getContent());
        review.setRestaurant(restaurant);

        if(!images.isEmpty()){
            review.setImageUrls(images.stream()
                    .map(image -> {
                        String imageUrl;
                        try {
                            imageUrl = s3Service.saveFile(image, "review_image");
                        } catch (IOException e) {
                            throw new EntityNotFoundException(ErrorCode.IMAGE_UPLOAD_FAILED, "이미지 업로드에 실패하였습니다.");
                        }
                        return imageUrl;
                    })
                    .collect(Collectors.toList()));
        }

        reviewRepository.save(review);

        return "저장 성공";
    }

    @Override
    @Transactional
    public String modifyReview(ReviewModifyRequestDto request, String token) {

        String userEmail = jwtProvider.getAccount(token);

        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND, "올바르지 않은 접근입니다.")
        );

        Review review = reviewRepository.findById(request.getReviewId()).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.REVIEW_NOT_FOUNT, "존재하지 않는 리뷰입니다.")
        );

        if(!Objects.equals(review.getUser().getEmail(), user.getEmail())){
            throw new EntityNotFoundException(ErrorCode.USER_REVIEW_NOT_MATCH, "작성자와 수정자가 일치하지 않습니다.");
        }

        if(request.getScope() < 1 || request.getScope() > 5){
            throw new EntityNotFoundException(ErrorCode.ILLEGAR_REVIEW_SCOPE, "별점은 1~5점만 가능합니다.");
        }

        if(request.getContent().length() > 200){
            throw new EntityNotFoundException(ErrorCode.ILLEGAR_CONTENT_SIZE, "글자수 제한을 넘었습니다.");
        }

        review.setContent(request.getContent());
        review.setScope(request.getScope());


        return "수정 성공";
    }

    @Override
    @Transactional
    public String deleteReview(ReviewModifyRequestDto request, String token) {

        String userEmail = jwtProvider.getAccount(token);

        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND, "올바르지 않은 접근입니다.")
        );

        Review review = reviewRepository.findById(request.getReviewId()).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.REVIEW_NOT_FOUNT, "존재하지 않는 리뷰입니다.")
        );

        if(!Objects.equals(review.getUser().getEmail(), user.getEmail())){
            throw new EntityNotFoundException(ErrorCode.USER_REVIEW_NOT_MATCH, "작성자와 삭제자가 일치하지 않습니다.");
        }

        reviewRepository.delete(review);

        return "삭제 성공";
    }
}
