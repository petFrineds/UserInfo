package petfriends.userInfo.repository;

import petfriends.userInfo.dto.UserInfoInterface;
import petfriends.userInfo.dto.UserInfoResponseDto;
import petfriends.userInfo.model.UserInfo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String>{

    Optional<UserInfo> findByUserId(String userId);
    boolean existsByUserId(String userId);
    // List<UserInfo> findAllByUserId(String userId);

    // 산책 랭킹
    @Query(value = "select user_id as userId from user_info order by walk_count desc", nativeQuery = true)
    public List<UserInfoInterface> selectWalkRnk();

    // 별점 랭킹
    @Query(value = "select user_id as userId from user_info order by avg_score desc", nativeQuery = true)
    public List<UserInfoInterface> selectStarRnk();


}
