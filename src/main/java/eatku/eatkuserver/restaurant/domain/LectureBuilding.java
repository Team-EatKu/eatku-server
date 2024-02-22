package eatku.eatkuserver.restaurant.domain;

import jakarta.persistence.*;

@Entity
public class LectureBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LB_ID")
    private Long id;

    private String name;

}
