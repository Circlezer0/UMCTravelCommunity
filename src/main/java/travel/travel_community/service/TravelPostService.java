package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travel.travel_community.entity.User;
import travel.travel_community.entity.mapping.LikedPost;
import travel.travel_community.entity.mapping.ScrappedPost;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.repository.LikedPostRepository;
import travel.travel_community.repository.TravelPostRepository;
import travel.travel_community.repository.ScrappedPostRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TravelPostService {
    private final LikedPostRepository likedPostRepository;
    private final ScrappedPostRepository scrappedPostRepository;
    private final TravelPostRepository travelPostRepository;

    @Transactional
    public TravelPost save(Long postId) {
        return travelPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }
    /**
     * 좋아요 한 번 누르면 좋아요 늘리기
     * 두 번 누르면 좋아요 삭제
     * liked레포지토리에 user와 post가 존재할 때 한 번 더 요청하면 좋아요 -1
     * @param postId
     * @param user
     */
    @Transactional
    public void addLike(Long postId, User user) {
        TravelPost post = travelPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 좋아요가 이미 존재하지 않는 경우
        if (!likedPostRepository.existsByUserAndPost(user, post)) {
            post.setLikeCount(post.getLikeCount() + 1);
            // LikedPost 엔티티 생성
            LikedPost likedPost = new LikedPost();
            likedPost.setUser(user);
            likedPost.setPost(post);
            likedPostRepository.save(likedPost); // 좋아요 저장
        } else {
            post.setLikeCount(post.getLikeCount() - 1);
            likedPostRepository.deleteByUserAndPost(user, post); // 좋아요 삭제
        }

        travelPostRepository.save(post); // 변경된 게시글 저장
    }


    /**
     * 스크랩 한 번 누르면 스크랩 수 늘리기
     * 두 번 누르면 스크랩 삭제
     * scrapped레포지토리에 user와 post가 존재할 때 한 번 더 요청하면 스크랩 -1
     * @param postId
     * @param user
     */

    @Transactional
    public void addScrap(Long postId, User user) {
        TravelPost post = travelPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));


        if (!scrappedPostRepository.existsByUserAndPost(user, post)) {
            post.setScrapCount(post.getScrapCount() + 1);

            ScrappedPost scrappedPost = new ScrappedPost();
            scrappedPost.setUser(user);
            scrappedPost.setPost(post);
            scrappedPostRepository.save(scrappedPost);
        } else {
            post.setScrapCount(post.getScrapCount() - 1);
            scrappedPostRepository.deleteByUserAndPost(user, post);
        }

        travelPostRepository.save(post);
    }

    /**
     * 게시글 조회하면 조회수+1
     * @param postId
     * @param user
     */

    public void addView(Long postId, User user) {
        Optional<TravelPost> travelPost = travelPostRepository.findById(postId);
        TravelPost post = travelPost.get();
        post.setViewCount(post.getViewCount()+1);
        travelPostRepository.save(post);

    }




    /*@Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item item = itemRepository.findOne(itemId);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }*/


    /*public List<LikedPost> findAll(User user){
        List<LikedPost> likedPosts = likedPostRepository.findAll(user);
        return likedPosts.stream()
                .map(l -> new PostRequestDTO(TravelPost post))
                .collect(Collectors.toList())
                .getResults();


    };*/
}
