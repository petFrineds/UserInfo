package petfriends.userInfo.service;

import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public UserInfo findById(String id) {
        Optional<UserInfo> option = userInfoRepository.findById(id);
        if (!option.isPresent()) {
            return null;
        }
        return option.get();
    }
}