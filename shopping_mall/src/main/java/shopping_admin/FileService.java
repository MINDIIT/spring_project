package shopping_admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	   // 파일 업로드 메서드
    public String uploadFile(MultipartFile file) throws IOException {
        // 파일 저장 디렉토리 설정
        String uploadDir = "/upload/";
        
        // 고유한 파일명 생성 (UUID를 사용하여 파일 이름을 유니크하게 만듭니다)
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        
        // 저장할 파일 경로 설정
        Path filePath = Paths.get(uploadDir + fileName);
        
        // 파일 디렉토리가 존재하지 않으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 저장
        Files.write(filePath, file.getBytes());
        
        // 파일 경로 반환
        return filePath.toString();
    }
}
