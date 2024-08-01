package travel.travel_community.entity.posts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import travel.travel_community.entity.User;
import travel.travel_community.entity.baseEntity.AbstractPost;
import travel.travel_community.entity.mapping.LikedTravelItemPost;
import travel.travel_community.entity.mapping.ScrapTravelItemPost;
import travel.travel_community.entity.mapping.ScrapTravelPost;
import travel.travel_community.entity.mapping.TravelItemPostCategory;
import travel.travel_community.entity.posts.categories.TravelItemCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "travel_item_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TravelItemPost extends AbstractPost {
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelItemPostCategory> postCategories = new ArrayList<>();

    @OneToMany(mappedBy = "travelItemPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelItemComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikedTravelItemPost> likes = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScrapTravelItemPost> scrap = new ArrayList<>();

    @Override
    public void addLike(User user) {
        LikedTravelItemPost likedPost = new LikedTravelItemPost();
        likedPost.setUser(user);
        likedPost.setPost(this);
        this.likes.add(likedPost);
        this.increaseLikeCount();
    }

    @Override
    public void removeLike(User user) {
        this.likes.removeIf(likedPost -> likedPost.getUser().equals(user));
        this.setLikeCount(this.likes.size());
    }

    @Override
    public void addScrap(User user) {
        ScrapTravelItemPost scrapTravelItemPost = new ScrapTravelItemPost();
        scrapTravelItemPost.setUser(user);
        scrapTravelItemPost.setPost(this);
        this.scrap.add(scrapTravelItemPost);
        this.increaseScrapCount();
    }

    @Override
    public void removeScrap(User user) {
        this.scrap.removeIf(scrapTravelItemPost -> scrapTravelItemPost.getUser().equals(user));
        this.setScrapCount(this.scrap.size());
    }

    public void addCategory(TravelItemCategory category) {
        TravelItemPostCategory postCategory = new TravelItemPostCategory();
        postCategory.setPost(this);
        postCategory.setCategory(category);
        this.postCategories.add(postCategory);
    }

    public void removeCategory(TravelItemCategory category) {
        this.postCategories.removeIf(pc -> pc.getCategory().equals(category));
    }

    public List<TravelItemCategory> getCategories() {
        return this.postCategories.stream()
                .map(TravelItemPostCategory::getCategory)
                .collect(Collectors.toList());
    }
}
