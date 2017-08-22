package golden.horde.personnel;

import static org.junit.Assert.assertTrue;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import golden.horde.personnel.domain.Department;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@Ignore
public class DepartmentIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void _a() {
		//ResponseEntity<String> response = restTemplate.getForEntity("/personnel/api/v1/ping", String.class);
		
		ResponseEntity<String> response = restTemplate.exchange("/personnel/api/v1/ping", 
				HttpMethod.GET, 
				new HttpEntity<String>(getBasiAuthHeaders("userok", "userok")), 
				String.class);
		
		String pong = response.getBody();
		assertTrue("Ожидалось PONG, но прибыло " + pong, pong.equals("PONG"));
	}
	
	@Test
	public void _b() {
		//ResponseEntity<Department> response = restTemplate.getForEntity("/personnel/api/v1/departments/3", Department.class);
		
		ResponseEntity<Department> response = restTemplate.exchange("/personnel/api/v1/departments/3", 
				HttpMethod.GET, 
				new HttpEntity<String>(getBasiAuthHeaders("userok", "userok")), 
				Department.class);
		
		Department d = response.getBody();
		assertTrue("Ожидали id = 3, но получили " + d.getDepartmentId(), d.getDepartmentId() == 3);
		assertTrue("Ожидали транспортный цех, но получили " + d.getDepartmentName(), d.getDepartmentName().equals("Транспортный цех"));
	}
	
	private HttpHeaders getBasiAuthHeaders(String name, String password) {
		String plainCreds = name + ":" + password;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		return headers;
	}
}
