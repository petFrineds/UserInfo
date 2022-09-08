package petfriends.userInfo.dto;

import petfriends.userInfo.model.UserInfo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserImageResponseDto {
    private byte[] userimage;

      public static UserImageResponseDto of(UserInfo userInfo) {
          return new UserImageResponseDto(userInfo.getUserImage());
      }

}
