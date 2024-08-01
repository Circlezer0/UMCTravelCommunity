package travel.travel_community.entity.posts;

import jakarta.persistence.*;
import lombok.*;
import travel.travel_community.entity.Image;
import travel.travel_community.entity.User;
import travel.travel_community.entity.baseEntity.AbstractPost;
import travel.travel_community.entity.mapping.ImageOfTravelPost;
import travel.travel_community.entity.mapping.LikedTravelPost;
import travel.travel_community.entity.mapping.ScrapTravelPost;
import travel.travel_community.entity.posts.regions.Continent;
import travel.travel_community.entity.posts.regions.Country;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TravelPost extends AbstractPost {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "continent_id", nullable = false)
    private Continent continent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "travelPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelComment> travelComments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikedTravelPost> likes = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScrapTravelPost> scrap = new ArrayList<>();

    @Override
    public void addLike(User user) {
        LikedTravelPost likedTravelPost = new LikedTravelPost();
        likedTravelPost.setUser(user);
        likedTravelPost.setPost(this);
        this.likes.add(likedTravelPost);
        this.increaseLikeCount();
    }

    @Override
    public void removeLike(User user) {
        this.likes.removeIf(likedTravelPost -> likedTravelPost.getUser().equals(user));
        this.setLikeCount(this.likes.size());
    }

    @Override
    public void addScrap(User user) {
        ScrapTravelPost scrapTravelPost = new ScrapTravelPost();
        scrapTravelPost.setUser(user);
        scrapTravelPost.setPost(this);
        this.scrap.add(scrapTravelPost);
        this.increaseScrapCount();
    }

    @Override
    public void removeScrap(User user) {
        this.scrap.removeIf(scrapTravelPost -> scrapTravelPost.getUser().equals(user));
        this.setScrapCount(this.scrap.size());
    }

    // 이미지 매핑
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageOfTravelPost> imageUrls = new ArrayList<>();
    public void addImageMapping(Image image) {
        ImageOfTravelPost mapping = ImageOfTravelPost.builder()
                .post(this)
                .image(image)
                .build();
        imageUrls.add(mapping);
    }

    public void removeImageMapping(Image image) {
        imageUrls.removeIf(mapping -> mapping.getImage().equals(image));
    }
}