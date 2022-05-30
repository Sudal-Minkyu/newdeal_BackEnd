package com.broadwave.backend.configs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 호스트링크주입
    @Value("${newdeal.api.front_url}")
    private String front_url;

    @Value("${newdeal.api.front_protocol}")
    private String front_protocol;

    @Value("${newdeal.aws.s3.access.id}")
    private String AWSS3ACCESSID;

    @Value("${newdeal.aws.s3.access.key}")
    private String AWSS3ACCESSKEY;

    @Value("${newdeal.aws.region}")
    private String AWSREGION;

    //  CORS에러 No 'Access-Control-Allow-Origin' header is present on the requested resource. 해결
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                // 허용 URL김지겸 ip http://192.168.0.140:8010, URL최인석 ip http://192.168.0.16:8010, URL성낙원 ip http://192.168.0.132:8010,  URL신동하 ip http://192.168.0.:8010,
                .allowedOrigins(front_protocol+"://localhost:8010", front_protocol+"://"+front_url, front_protocol+"://192.168.0.140:8010", front_protocol+"://192.168.0.132:8010", front_protocol+"://192.168.0.16:8010")
//                .allowedOrigins(front_protocol+"://"+front_url, front_protocol)
                .allowedHeaders("JWT_AccessToken","insert_id");
    }

    @Bean
    public BasicAWSCredentials AwsCredentianls(){
        return new BasicAWSCredentials(AWSS3ACCESSID,AWSS3ACCESSKEY);
    }

    @Bean
    public AmazonS3 AwsS3Client(){
        return AmazonS3ClientBuilder.standard()
                .withRegion(AWSREGION)
                .withCredentials(new AWSStaticCredentialsProvider(this.AwsCredentianls()))
                .build();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "uploadPath")
    public String uploadPath() {
        return uploadFileDir;
    }

    @Value("${newdeal.fileupload.url}")
    String uploadFileDir;

}