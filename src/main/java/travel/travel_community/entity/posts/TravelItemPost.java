package travel.travel_community.entity.posts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import travel.travel_community.entity.User;
import travel.travel_community.entity.baseEntity.AbstractPost;
import travel.travel_community.entity.baseEntity.TimeEntity;
import travel.travel_community.entity.mapping.ScrappedPost;
import travel.travel_community.entity.mapping.TravelItemLikedPost;
import travel.travel_community.entity.mapping.TravelItemPostCategory;
import travel.travel_community.entity.mapping.TravelItemScrappedPost;
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
    private List<TravelItemLikedPost> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelItemScrappedPost> scrappes = new ArrayList<>();

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

    @Override
    public void addScrap(User user) {
        /*ScrappedPost scrappedPost = new ScrappedPost();
        scrappedPost.setUser(user);
        scrappedPost.setPost(this);
        this.scrappes.add(scrappedPost);
        this.setScrapCount(this.getScrapCount() + 1);*/
    }

    @Override
    public void removeScrap(User user) {
        /*this.scrappes.removeIf(likedPost -> likedPost.getUser().equals(user));
        this.setScrapCount(this.scrappes.size());*/
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
