package travel.travel_community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.ImageHandler;
import travel.travel_community.apiPayload.exception.handler.PostHandler;
import travel.travel_community.entity.Image;
import travel.travel_community.repository.ImagePostMappingRepository;
import travel.travel_community.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    /**
     * UPLOAD_DIR
     * 이미지를 저장하는 경로는 D드라이브의 travel_web\images 폴더 내부입니다.
     * 서버 컴퓨터의 디렉터리입니다. D드라이브가 없을수도 있으니 각자의 환경에 맞게 수정해야 합니다.
     */
    private static final String UPLOAD_DIR = "D:\\travel_web\\images";

    private final ImageRepository imageRepository;
    private final ImagePostMappingRepository imageMappingRepository;

    public Image findByUrl(String url) {
        return imageRepository.findImageByUrl(url).orElseThrow(() -> new ImageHandler(ErrorStatus.IMAGE_NOT_FOUND));
    }

    /**
     * 이미지의 이름을 \images\고유_이미지_아이디 형식으로 DB에 저장합니다.
     * 아이디의 생성 규칙은 [현재 시각 + "_" + 파일 이름] 입니다.
     * @param file 이미지 파일
     * @return 이미지 엔티티
     */
    public Image saveImage(MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Image image = new Image();
            image.setUrl("\\images\\" + uniqueFileName);
            image.setOriginalFileName(fileName);

            return imageRepository.save(image);
        } catch (IOException ex) {
            throw new ImageHandler(ErrorStatus.IMAGE_STORE_FAILED);
        }
    }

    /**
     * Orphan 이미지의 아이디 리스트를 반환합니다
     * @return Orphan Image ids
     */
    public List<Long> findOrphanImageIds() {
        List<Long> allImageIds = imageRepository.findAllIds();
        List<Long> usedImageIds = imageMappingRepository.findAllImageIds();
        allImageIds.removeAll(usedImageIds);
        return allImageIds;
    }


    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageHandler(ErrorStatus.IMAGE_NOT_FOUND));
        try {
            System.out.println("delete image url : " + UPLOAD_DIR + URLDecoder.decode(image.getUrl().substring(image.getUrl().lastIndexOf('\\')), "UTF-8"));
            File file = new File(UPLOAD_DIR + URLDecoder.decode(image.getUrl().substring(image.getUrl().lastIndexOf('\\')), "UTF-8"));
            file.delete();
        }catch (Exception e){
            throw new ImageHandler(ErrorStatus.IMAGE_DELETE_FAILED);
        }

        imageRepository.delete(image);
    }

    /**
     * Orphan 이미지 전체를 삭제합니다.
     */
    public void cleanupOrphanImages() {
        List<Long> orphanImageIds = findOrphanImageIds();
        for (Long imageId : orphanImageIds) {
            deleteImage(imageId);
        }
    }
}