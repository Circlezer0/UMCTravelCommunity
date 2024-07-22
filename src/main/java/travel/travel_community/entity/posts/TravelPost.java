package travel.travel_community.entity.posts;

import jakarta.persistence.*;
import lombok.*;
import travel.travel_community.entity.User;
import travel.travel_community.entity.baseEntity.AbstractPost;
import travel.travel_community.entity.baseEntity.TimeEntity;
import travel.travel_community.entity.mapping.LikedPost;
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
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikedPost> likes = new ArrayList<>();

    @Override
    public void addLike(User user) {
        LikedPost likedPost = new LikedPost();
        likedPost.setUser(user);
        likedPost.setPost(this);
        this.likes.add(likedPost);
        this.setLikeCount(this.getLikeCount() + 1);
    }

    @Override
    public void removeLike(User user) {
        this.likes.removeIf(likedPost -> likedPost.getUser().equals(user));
        this.setLikeCount(this.likes.size());
    }
}