package travel.travel_community.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import travel.travel_community.entity.baseEntity.TimeEntity;
import travel.travel_community.entity.enums.Role;
import travel.travel_community.entity.mapping.LikedPost;
import travel.travel_community.entity.mapping.ScrappedPost;
import travel.travel_community.entity.mapping.TravelItemLikedPost;
import travel.travel_community.entity.posts.Comment;
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
    private List<TravelPost> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LikedPost> likedPosts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ScrappedPost> scrappedPosts = new ArrayList<>();

    // 좋아요한 게시글 찾기
    public List<TravelPost> getLikedPosts() {
        return likedPosts.stream()
                .map(LikedPost::getPost)
                .collect(Collectors.toList());
    }

    //스크랩한 게시글 찾기
    public List<TravelPost> getScrappedPosts() {
        return scrappedPosts.stream()
                .map(ScrappedPost::getPost)
                .collect(Collectors.toList());
    }

    // 작성한 게시글 찾기
    public List<TravelPost> getAuthoredPosts() {
        return posts;
    }

    // 댓글 단 게시글 찾기
    public List<TravelPost> getCommentedPosts() {
        return comments.stream()
                .map(Comment::getTravelPost)
                .distinct()
                .collect(Collectors.toList());
    }



    @OneToMany(mappedBy = "author")
    private List<TravelItemPost> travelItemPosts = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<TravelItemComment> travelItemComments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TravelItemLikedPost> likedTravelItemPosts = new ArrayList<>();

    // 여행 물품 게시글 관련 메서드
    public List<TravelItemPost> getLikedTravelItemPosts() {
        return likedTravelItemPosts.stream()
                .map(TravelItemLikedPost::getPost)
                .collect(Collectors.toList());
    }

    public List<TravelItemPost> getAuthoredTravelItemPosts() {
        return travelItemPosts;
    }

    public List<TravelItemPost> getCommentedTravelItemPosts() {
        return travelItemComments.stream()
                .map(TravelItemComment::getTravelItemPost)
                .distinct()
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
