package travel.travel_community.entity.posts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import travel.travel_community.entity.baseEntity.AbstractComment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TravelComment extends AbstractComment {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_post_id", nullable = false)
    private TravelPost travelPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private TravelComment parentTravelComment;

    @OneToMany(mappedBy = "parentTravelComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelComment> childTravelComments = new ArrayList<>();
}