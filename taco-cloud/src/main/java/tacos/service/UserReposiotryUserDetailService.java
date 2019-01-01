package tacos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tacos.model.User;
import tacos.repository.UserRepository;

@Service
public class UserReposiotryUserDetailService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public UserReposiotryUserDetailService(UserRepository userRepo) {
		this.userRepository = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = this.userRepository.findByUsername(arg0);
		if(user!=null){
			return user;
		}
		throw new UsernameNotFoundException("User '" + arg0 + "' not found");
	}

}
