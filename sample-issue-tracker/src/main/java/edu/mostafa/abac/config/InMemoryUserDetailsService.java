package edu.mostafa.abac.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import edu.mostafa.abac.web.model.Project;
import edu.mostafa.abac.web.model.ProjectUser;
import edu.mostafa.abac.web.model.UserRole;
import edu.mostafa.abac.web.services.UserService;

@Component
public class InMemoryUserDetailsService implements UserDetailsService, UserService {
	private Map<String, ProjectSecurityUser> users = new HashMap<>();
	
	public InMemoryUserDetailsService() {
	}
	
	
	@PostConstruct
	private void init() {
		this.users = new HashMap<>();
		// commented lines correspond to user:password base64 encoded string
		// Those string are used in the "Authentication" header in every request
		// "YWRtaW46cGFzc3dvcmQ="
		users.put("admin", new ProjectSecurityUser("admin", "password", UserRole.ADMIN));
		// "cG0xOnBhc3N3b3Jk"
		// ----!
		users.put("pm1", new ProjectSecurityUser("pm1", "password", UserRole.PM));
		// "cG0yOnBhc3N3b3Jk"
		// ----!
		users.put("pm2", new ProjectSecurityUser("pm2", "password", UserRole.PM));
		// "ZGV2MTpwYXNzd29yZA=="
		// ------!
		users.put("dev1", new ProjectSecurityUser("dev1", "password", UserRole.DEVELOPER));
		// "ZGV2MjpwYXNzd29yZA=="
		// ------!
		users.put("dev2", new ProjectSecurityUser("dev2", "password", UserRole.DEVELOPER));
		// "dGVzdDE6cGFzc3dvcmQ="
		// -------!
		users.put("test1", new ProjectSecurityUser("test1", "password", UserRole.TESTER));
		// "dGVzdDI6cGFzc3dvcmQ="
		// -------!
		users.put("test2",new ProjectSecurityUser("test2", "password", UserRole.TESTER));
	}
	
	
	public void createUser(ProjectSecurityUser user) {
		Assert.isTrue(!userExists(user.getUsername()));

		users.put(user.getUsername().toLowerCase(), user);
	}
	
	public boolean userExists(String username) {
		return users.containsKey(username.toLowerCase());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ProjectSecurityUser user = users.get(username.toLowerCase());

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new ProjectSecurityUser(user.getUsername(), user.getPassword(), user.getProject(), user.getRole());
	}

	@Override
	public ProjectUser findUserByName(String name) {
		return users.get(name.toLowerCase());
	}

	@Override
	public List<ProjectUser> findUserByProject(Integer projectId) {
		if(projectId == null)
			return null;
		List<ProjectUser> result = new LinkedList<>();
		for(ProjectSecurityUser user : users.values()) {
			Project project = user.getProject();
			if(project != null && projectId.equals(project.getId())) {
				result.add(user);
			}
		}
		return result;
	}
}
