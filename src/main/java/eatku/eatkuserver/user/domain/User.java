package eatku.eatkuserver.user.domain;

import eatku.eatkuserver.like.domain.Like;
import eatku.eatkuserver.review.domain.Review;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long Id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @ColumnTransformer(
            read = "FUNCTION_DECRYPT(PASSWORD)",     // 칼럼 값을 가져올 때
            write = "FUNCTION_ENCRYPT(?)"                 // 칼럼 값을 쓸 때
    )

    private String password;

    @Column(nullable = false, length = 10)
    private String nickName;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Authority> roles = new ArrayList<>();

    @Column(nullable = true)
    private String profileImageUrl;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Like> likeList = new ArrayList<>();
}
