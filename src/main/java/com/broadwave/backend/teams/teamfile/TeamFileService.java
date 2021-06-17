package com.broadwave.backend.teams.teamfile;

import com.broadwave.backend.common.UploadFileUtils;
import com.broadwave.backend.teams.Team;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Slf4j
@Service
public class TeamFileService {

    private static final Logger logger = LoggerFactory.getLogger(TeamFileService.class);
    private final Path rootLocation;
    private final TeamFileRepository teamFileRepository;

    @Autowired
    public TeamFileService(String uploadPath, TeamFileRepository teamFileRepository) {
        this.teamFileRepository = teamFileRepository;
        logger.info("PATH :: " + uploadPath);
        this.rootLocation = Paths.get(uploadPath + "teamfiles");
    }

    // 기타사진 파일업로드
    public TeamFile store(MultipartFile file, Team team) throws Exception {
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file: " + file.getOriginalFilename());
            }
            String saveFileAddress = UploadFileUtils.fileSave(rootLocation.toString(), file);
            if (saveFileAddress.toCharArray()[0] == '/') {
                saveFileAddress = saveFileAddress.substring(1);
            }
            Resource resource = loadAsResource(saveFileAddress);
            TeamFile saveFile = new TeamFile();
            saveFile.setTeamId(team);
            saveFile.setFileAddress(saveFileAddress);
            saveFile.setFileSize(resource.contentLength());
            saveFile.setFileInsertDate(LocalDateTime.now());
            saveFile = teamFileRepository.save(saveFile);
            log.info("파일 저장완료");
            return saveFile;
        } catch (IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    private Path loadPath(String fileName) {
        return rootLocation.resolve(fileName);
    }

    public Resource loadAsResource(String fileName) throws Exception {
        try {
            if (fileName.toCharArray()[0] == '/') {
                fileName = fileName.substring(1);
            }
            Path file = loadPath(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("Could not read file: " + fileName);
            }
        } catch (Exception e) {
            throw new Exception("Could not read file: " + fileName);
        }
    }

}
