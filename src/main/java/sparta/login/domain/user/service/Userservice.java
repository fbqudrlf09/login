package sparta.login.domain.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sparta.login.domain.user.dto.UserRegisterDto;
import sparta.login.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class Userservice {

	private final UserRepository userRepository;


	public UserRegisterDto registerUser(UserRegisterDto userRegisterDto) {

		if (userRepository.existsByUsername(userRegisterDto.getUsername())) {
			throw new Exception()
		}



		return null;
	}
}
