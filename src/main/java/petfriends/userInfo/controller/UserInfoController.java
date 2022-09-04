package petfriends.userInfo.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.service.UserInfoService;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/userInfos")
public class UserInfoController {

	private final UserInfoService userInfoService;

	@GetMapping("/user-info")
	public UserInfo requestUserInfo(@RequestParam("id") String id) {
		return userInfoService.findById(id);
	}
}