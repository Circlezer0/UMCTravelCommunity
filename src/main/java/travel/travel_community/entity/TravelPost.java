package travel.travel_community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name="travelPost")
public class TravelPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "travelPost", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY )
    private List<Images> imagesList = new ArrayList<>();

    @OneToMany(mappedBy = "travelPost", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY )
    private List<TravelPostCategory> travelPostCategoryList = new ArrayList<>();

    @OneToMany(mappedBy = "travelPost", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY )
    private List<TravelPostLikes> travelPostLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "travelPost", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY )
    private List<TravelPostScrap> travelPostScrapList = new ArrayList<>();

    @OneToMany(mappedBy = "travelPost", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY )
    private List<TravelPostNationality> travelPostNationalityList = new ArrayList<>();

}
