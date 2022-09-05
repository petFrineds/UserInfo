package petfriends.userInfo.service;

import petfriends.userInfo.dto.UserInfoDto;
import petfriends.userInfo.dto.UserInfoResponseDto;
import petfriends.userInfo.model.UserImage;
import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.repository.UserImageRepository;
import petfriends.userInfo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserImageRepository userImageRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfo findById(String id) {
        Optional<UserInfo> option = userInfoRepository.findById(id);
        if (!option.isPresent()) {
             throw new RuntimeException("유저 정보가 없습니다.");
        }
        return option.get();
    }

    /**
     * 사용자 정보 확인
     * @param userId
     * @return
     */
    public UserInfoResponseDto getUserInfo(String userId) {
            return userInfoRepository.findByUserId(userId)
                            .map(UserInfoResponseDto::of)
                            .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }


    /**
     * 회원 가입
     * @param userInfo
     * @return
     */
    public String signup(UserInfo userInfo) {
        if (userInfoRepository.existsByUserId(userInfo.getUserId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);

        return userInfo.getUserId();
    }

     /**
		 * 사용자 전체 조회
		 * @param userId
		 * @return
		 */
		public UserInfo getMyUserInfo(String userId) {
            Optional<UserInfo> userInfo = userInfoRepository.findByUserId(userId);
            return  userInfo.get();
    }


    /**
		 * 사용자 이미지 저장
		 * @param request
		 * @return
		 * @throws IOException
		 */
		public Long uploadUserImage(HttpServletRequest request) throws IOException{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            String userId = multipartRequest.getParameter("user_id");

            // System.out.println("file name ======= " + file.getOriginalFilename());
            // System.out.println("userid ======= " + multipartRequest.getParameter("user_id"));

            LocalDateTime current = LocalDateTime.now();

            UserImage userImage = new UserImage();
            userImage.setMimeType(file.getContentType());
            userImage.setOriginalName(file.getOriginalFilename());
            userImage.setUserImage(file.getBytes());
            userImage.setUserId(userId);
            userImage.setCreateDate(java.sql.Timestamp.valueOf(current));

            UserImage saveUuserImg =  userImageRepository.save(userImage);
            Long imageId = saveUuserImg.getId();

            Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserId(userId);
            UserInfo userInfo = userInfoOptional.get();
            userInfo.setImageId(imageId);
            userInfoRepository.save(userInfo);

            return imageId;
    }



}