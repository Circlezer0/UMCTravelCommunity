package travel.travel_community.entity.posts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import travel.travel_community.entity.User;
import travel.travel_community.entity.baseEntity.AbstractPost;
import travel.travel_community.entity.baseEntity.TimeEntity;
import travel.travel_community.entity.mapping.TravelItemLikedPost;
import travel.travel_community.entity.posts.categories.TravelItemCategory;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_item_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TravelItemPost extends AbstractPost {
    @ManyToMany
    @JoinTable(
            name = "travel_item_post_categories",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<TravelItemCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "travelItemPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelItemComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelItemLikedPost> likes = new ArrayList<>();

    @Override
    public void addLike(User user) {
        TravelItemLikedPost likedPost = new TravelItemLikedPost();
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