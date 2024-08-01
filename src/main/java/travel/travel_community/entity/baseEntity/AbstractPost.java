package travel.travel_community.entity.baseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import travel.travel_community.entity.User;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractPost extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column
    private int likeCount = 0;

    @Column
    private int scrapCount = 0;

    @Column
    private int viewCount = 0;

    public abstract void addLike(User user);
    public abstract void removeLike(User user);
    public abstract void addScrap(User user);
    public abstract void removeScrap(User user);

    public void increaseLikeCount() {this.likeCount++;}
    public void increaseScrapCount() {this.scrapCount++;}
    public void increaseViewCount() {
        this.viewCount++;
    }
}