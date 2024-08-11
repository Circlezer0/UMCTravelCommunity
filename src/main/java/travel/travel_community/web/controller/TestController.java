package travel.travel_community.web.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel.travel_community.entity.Image;
import travel.travel_community.entity.User;
import travel.travel_community.entity.mapping.ImageOfTravelPost;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.entity.posts.regions.Continent;
import travel.travel_community.entity.posts.regions.Country;
import travel.travel_community.service.TravelItemPostCategoryService;
import travel.travel_community.service.TravelPostCategoryService;
import travel.travel_community.service.TravelPostService;
import travel.travel_community.service.UserService;
import travel.travel_community.web.dto.postDTO.travelPostDTO.TravelPostRequestDTO;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final TravelPostService travelPostService;
    private final UserService userService;
    private final TravelPostCategoryService travelPostCategoryService;
    private final TravelItemPostCategoryService travelItemPostCategoryService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", travelPostService.getLatestPosts(0));
        return "home";
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        TravelPost post = travelPostService.findTravelPostById(id);
        model.addAttribute("title", post.getTitle());
        model.addAttribute("content", post.getContent());
        return "post";
    }

    @GetMapping("/create")
    public String createPostForm(Model model) {
        return "create-post";
    }

    @PostMapping("/create")
    public String createPost(TravelPostRequestDTO.CreatePostDTO request) {
        User author = userService.findUserByUserId(request.getUserid());
        Continent continent = travelPostCategoryService.findContinentByName(request.getContinent());
        Country country = travelPostCategoryService.findCountryByNameAndContinent(request.getCountry(), continent);
        TravelPost post = new TravelPost();
        post.setAuthor(author);
        post.setContinent(continent);
        post.setCountry(country);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post = travelPostService.createPost(post);
        return "redirect:/";
    }

    @GetMapping("/addContinent")
    public String addContinent(@RequestParam(value = "continent") String continent) {
        travelPostCategoryService.addContinent(continent);
        return "redirect:/";
    }

    @GetMapping("/addCountry")
    public String addCountry(@RequestParam(value = "continent") String continent,
                             @RequestParam(value = "country") String country) {
        Continent continentByName = travelPostCategoryService.findContinentByName(continent);
        travelPostCategoryService.addCountry(continentByName, country);
        return "redirect:/";
    }

    @PostConstruct
    public void regionInit() {
        travelPostCategoryService.initializeRegions();
    }

    @PostConstruct
    public void categoryInit() {
        travelItemPostCategoryService.initializeCategories();
    }
}
