package travel.travel_community.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import travel.travel_community.entity.baseEntity.TimeEntity;
import travel.travel_community.entity.enums.Role;
import travel.travel_community.entity.mapping.LikedTravelPost;
import travel.travel_community.entity.mapping.LikedTravelItemPost;
import travel.travel_community.entity.mapping.ScrapTravelItemPost;
import travel.travel_community.entity.mapping.ScrapTravelPost;
import travel.travel_community.entity.posts.TravelComment;
import travel.travel_community.entity.posts.TravelItemComment;
import travel.travel_community.entity.posts.TravelItemPost;
import travel.travel_community.entity.posts.TravelPost;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends TimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userid;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @OneToMany(mappedBy = "author")
    private List<TravelPost> travelPosts = new ArrayList<>();
    @OneToMany(mappedBy = "author")
    private List<TravelItemPost> travelItemPosts = new ArrayList<>();
    // 작성한 게시글 찾기
    public List<TravelPost> getAuthoredTravelPosts() {
        return travelPosts;
    }
    public List<TravelItemPost> getAuthoredTravelItemPosts() {
        return travelItemPosts;
    }


    @OneToMany(mappedBy = "author")
    private List<TravelComment> travelComments = new ArrayList<>();
    @OneToMany(mappedBy = "author")
    private List<TravelItemComment> travelItemComments = new ArrayList<>();
    // 댓글 단 게시글 찾기
    public List<TravelPost> getCommentedTravelPosts() {
        return travelComments.stream()
                .map(TravelComment::getTravelPost)
                .distinct()
                .collect(Collectors.toList());
    }
    public List<TravelItemPost> getCommentedTravelItemPosts() {
        return travelItemComments.stream()
                .map(TravelItemComment::getTravelItemPost)
                .distinct()
                .collect(Collectors.toList());
    }


    @OneToMany(mappedBy = "user")
    private List<LikedTravelPost> likedTravelPosts = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<LikedTravelItemPost> likedTravelItemPosts = new ArrayList<>();
    // 좋아요한 게시글 찾기
    public List<TravelPost> getLikedTravelPosts() {
        return likedTravelPosts.stream()
                .map(LikedTravelPost::getPost)
                .collect(Collectors.toList());
    }
    public List<TravelItemPost> getLikedTravelItemPosts() {
        return likedTravelItemPosts.stream()
                .map(LikedTravelItemPost::getPost)
                .collect(Collectors.toList());
    }


    @OneToMany(mappedBy = "user")
    private List<ScrapTravelPost> scrapTravelPosts = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<ScrapTravelItemPost> scrapTravelItemPosts = new ArrayList<>();
    // 스크랩한 게시글 찾기
    public List<TravelPost> getScrapTravelPosts() {
        return scrapTravelPosts.stream()
                .map(ScrapTravelPost::getPost)
                .collect(Collectors.toList());
    }
    public List<TravelItemPost> getScrapTravelItemPosts() {
        return scrapTravelItemPosts.stream()
                .map(ScrapTravelItemPost::getPost)
                .collect(Collectors.toList());
    }





    /**
     * Security 로그인 / 회원가입 시 id로 사용할 값
     * @return 유저의 아이디
     */
    @Override
    public String getUsername() {
        return this.userid;
    }

    /**
     * Security 로그인 / 회원가입 시 password로 사용할 값
     * @return 유저의 비밀번호
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
