package com.broadwave.backend.safety.safetyDtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Time :
 * Remark : 뉴딜 계측 기반 안전성 추정 데이터 제공 저장 MapperDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class SafetyMapperDto {

    private Long id; // 고유ID값
    private String sfName; // 교량명
    private String sfForm; // 교량형식
    private String sfRank; // 교량등급
    private Double sfLength; // 총 길이
    private Double sfWidth; // 총 폭
    private Integer sfNum; // 경간수
    private Integer sfCompletionYear; // 준공년도
    private Double sfFactor; // 안전율
    private MultipartFile sfImage; // 사진업로드 파일

}
