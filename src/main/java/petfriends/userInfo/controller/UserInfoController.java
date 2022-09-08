package petfriends.userInfo.controller;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import petfriends.userInfo.dto.UserInfoInterface;
import petfriends.userInfo.dto.UserInfoResponseDto;
import petfriends.userInfo.model.UserImage;
import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.repository.UserImageRepository;
import petfriends.userInfo.repository.UserInfoRepository;
import petfriends.userInfo.service.UserInfoService;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/userInfos")
public class UserInfoController {

	private final UserInfoService userInfoService;
	private final UserInfoRepository userInfoRepository;

	@GetMapping("/user-info")
	public UserInfo requestUserInfo(@RequestParam("id") String id) {
		return userInfoService.findById(id);
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody UserInfo userInfo) {
			return ResponseEntity.ok(userInfoService.signup(userInfo));
	}

	// @GetMapping("/me")
  //   public ResponseEntity<UserInfoResponseDto> getMyMemberInfo() {
  //       return ResponseEntity.ok(userInfoService.getMyInfo());
  //   }

	@GetMapping("/{userId}")
	public ResponseEntity<UserInfo> getMyUserInfo(@PathVariable String userId) {

		UserInfo userInfo = userInfoService.getMyUserInfo(userId);

		if(userInfo != null){
			return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
		}
		//내용 없을 때
		return  new ResponseEntity<UserInfo>(userInfo, HttpStatus.NO_CONTENT);

	}


	@PostMapping("/image/upload")
	public String uploadUserImage(HttpServletRequest request) throws IOException {
			return userInfoService.uploadUserImage(request);
	}


	@GetMapping("/image/{userId}")
	public ResponseEntity<byte[]> downloadUserImage(@PathVariable String userId) {
				Optional<UserInfo> user = userInfoRepository.findById(userId);

				// if(user.isPresent()) {

				// 	UserInfo userInfo = user.get();
				// 	HttpHeaders headers = new HttpHeaders();
				// 		headers.add("Content-Type", userInfo.getMimeType());
				// 		headers.add("Content-Length", String.valueOf(userInfo.getUserImage().length));
				// 	return new ResponseEntity<byte[]>(userInfo.getUserImage(), headers, HttpStatus.OK);
				// }

				if(user.isPresent()) {

					UserInfo userInfo = user.get();
					// HttpHeaders headers = new HttpHeaders();
					// 	headers.add("Content-Type", userInfo.getMimeType());
					// 	headers.add("Content-Length", String.valueOf(userInfo.getUserImage().length));
					return new ResponseEntity(userInfo.getUserImage(), HttpStatus.OK);
				}

			return null;

	}


	@GetMapping("/check/{userId}")
	public ResponseEntity<UserInfoResponseDto> getMemberInfo(@PathVariable String userId) {
			return ResponseEntity.ok(userInfoService.getUserInfo(userId));
	}

  /**
	 * 산책 랭킹
	 * @return
	 */
	@GetMapping("/selectWalkRnk")
	public ResponseEntity<List<UserInfo>> selectWalkRnk() {

		List<UserInfo> userInfoInterfaces = userInfoService.selectWalkRnk();

		if(userInfoInterfaces != null){
			return new ResponseEntity<List<UserInfo>>(userInfoInterfaces, HttpStatus.OK);
		}
		//내용 없을 때
		return  new ResponseEntity<List<UserInfo>>(userInfoInterfaces, HttpStatus.NO_CONTENT);

  }

	/**
	 * 별점 랭킹
	 * @return
	 */
	@GetMapping("/selectStarRnk")
	public ResponseEntity<List<UserInfo>> selectStarRnk() {

		List<UserInfo> userInfoInterfaces = userInfoService.selectStarRnk();

		if(userInfoInterfaces != null){
			return new ResponseEntity<List<UserInfo>>(userInfoInterfaces, HttpStatus.OK);
		}
		//내용 없을 때
		return  new ResponseEntity<List<UserInfo>>(userInfoInterfaces, HttpStatus.NO_CONTENT);

  }



	@PostMapping("/checkDummy")
	public ResponseEntity checkDummy(HttpServletRequest request){
		System.out.println("@@@@@@@@@@@@@@@@@@@@checkDummy@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/checkDummy")
	public ResponseEntity checkDummyGet(HttpServletRequest request){
		System.out.println("@@@@@@@@@@@@@@@@@@@@checkDummyGet@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		return new ResponseEntity(HttpStatus.OK);
	}


}





// @GetMapping("/image/{id}")
// 	public ResponseEntity<byte[]> downloadUserImage(@PathVariable Long id) {
// 				Optional<UserImage> user = userImageRepository.findById(id);

// 				if(user.isPresent()) {

// 					UserImage userImage = user.get();
// 					HttpHeaders headers = new HttpHeaders();
// 						headers.add("Content-Type", userImage.getMimeType());
// 						headers.add("Content-Length", String.valueOf(userImage.getUserImage().length));
// 					return new ResponseEntity<byte[]>(userImage.getUserImage(), headers, HttpStatus.OK);
// 				}

// 			return null;

// 	}