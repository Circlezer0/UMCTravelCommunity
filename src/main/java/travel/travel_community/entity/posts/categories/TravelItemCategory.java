package travel.travel_community.entity.posts.categories;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import travel.travel_community.entity.baseEntity.TimeEntity;
import travel.travel_community.entity.mapping.TravelItemPostCategory;
import travel.travel_community.entity.posts.TravelItemPost;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "travel_item_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TravelItemCategory extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<TravelItemPostCategory> postCategories = new ArrayList<>();

    public List<TravelItemPost> getPosts() {
        return this.postCategories.stream()
                .map(TravelItemPostCategory::getPost)
                .collect(Collectors.toList());
    }
}