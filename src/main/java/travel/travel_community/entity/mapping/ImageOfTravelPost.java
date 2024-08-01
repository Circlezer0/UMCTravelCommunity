package travel.travel_community.entity.mapping;

import jakarta.persistence.*;
import lombok.*;
import travel.travel_community.entity.Image;
import travel.travel_community.entity.baseEntity.TimeEntity;
import travel.travel_community.entity.posts.TravelPost;

@Entity
@Table(name = "image_travel_post_mapping")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageOfTravelPost extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private TravelPost post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;
}
