package travel.travel_community.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import travel.travel_community.entity.Image;
import travel.travel_community.service.ImageService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    /**
     * 이미지를 서버 컴퓨터 내부에 저장하는 기능입니다.
     * Tiny 에디터에서 tinymce.init({images_upload_url: '/api/v1/images/upload'}) 으로 설정할 수 있습니다.
     * 에디터를 통해 사진을 업로드 한 경우 자동으로 해당 url이 호출되어 이미지가 서버에 저장됩니다.
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        Image savedImage = imageService.saveImage(file);
        Map<String, String> response = new HashMap<>();
        // 이렇게 반환하면 img 태그의 src안에 해당 값으로 설정됨.
        response.put("location", savedImage.getUrl());
        return ResponseEntity.ok(response);
    }

    /**
     * 매핑 관계가 없는 Trash Image 들을 검색하여 서버 컴퓨터에서 삭제하는 기능입니다.
     * Image - ImageOfTravelPost 사이의 아이디 차이를 통해 Orphan 인지를 판단합니다.
     * @return
     */
    @GetMapping("/cleanImages")
    public ResponseEntity<String> deleteOrphanImages() {
        imageService.cleanupOrphanImages();
        return ResponseEntity.ok("okay");
    }
}
