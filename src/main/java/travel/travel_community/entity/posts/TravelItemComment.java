package travel.travel_community.entity.posts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import travel.travel_community.entity.User;
import travel.travel_community.entity.baseEntity.AbstractComment;
import travel.travel_community.entity.baseEntity.TimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_item_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TravelItemComment extends AbstractComment {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_item_post_id", nullable = false)
    private TravelItemPost travelItemPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private TravelItemComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelItemComment> childComments = new ArrayList<>();
}