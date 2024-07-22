package travel.travel_community.entity.mapping;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import travel.travel_community.entity.User;
import travel.travel_community.entity.baseEntity.AbstractLikedPost;
import travel.travel_community.entity.baseEntity.TimeEntity;
import travel.travel_community.entity.posts.TravelItemPost;
import travel.travel_community.entity.posts.categories.TravelItemCategory;

@Entity
@Table(name = "travel_item_post_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TravelItemPostCategory extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private TravelItemPost post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private TravelItemCategory category;

    // 여기에 추가 필드를 넣을 수 있습니다. 예:
    // private int orderIndex;
}