package golden.horde.personnel.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PersonnelUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (!("userok".equals(username) || "operator".equals(username))) {
				throw new UsernameNotFoundException(String.format("User '%s' doesn't exist", username));
		}
		List<GrantedAuthority> authorities;
		if ("userok".equals(username))
			authorities = AuthorityUtils.createAuthorityList("ROLE_USEROK");
		else
			authorities = AuthorityUtils.createAuthorityList("ROLE_OPERATOR");
		UserDetails userDetails = new User(username, username, authorities);
		return userDetails;
	}
}
