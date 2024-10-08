package eatku.eatkuserver.restaurant.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LectureBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LB_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    private String name;

}
