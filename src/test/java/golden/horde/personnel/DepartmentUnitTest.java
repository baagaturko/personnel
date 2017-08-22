package golden.horde.personnel;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import golden.horde.personnel.domain.Department;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonnelApplication.class)
@WebAppConfiguration
//@Ignore

@AutoConfigureMockMvc // для упрощения создания mockMvc
public class DepartmentUnitTest {

	/*@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}
	*/
	
	@Autowired
    private MockMvc mockMvc;
	
	private HttpMessageConverter jsonConverter;
	
	@Autowired	
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.jsonConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.jsonConverter);
    }

	private String json(Object o) throws IOException {
		MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
		jsonConverter.write(o, MediaType.APPLICATION_JSON, outputMessage);
		return outputMessage.getBodyAsString();
	}

	@Test
	public void _a() throws Exception {
		mockMvc.perform(get("/personnel/api/v1/departments")
				.with(httpBasic("userok","userok"))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[0].departmentId", is(1)))
				.andExpect(jsonPath("$[0].departmentName", is("Административно-хозяйственный отдел")))
				.andExpect(jsonPath("$[1].departmentId", is(4)))
				.andExpect(jsonPath("$[1].departmentName", is("Бухгалтерия")));
	}

	@Test
	public void _b() throws Exception {
		mockMvc.perform(get("/personnel/api/v1/departments/3")
				.with(httpBasic("userok","userok")))
				// .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.departmentId", is(3)))
				.andExpect(jsonPath("$.departmentName", is("Транспортный цех")));
	}

	@Test
	public void _c() throws Exception {
		mockMvc.perform(get("/personnel/api/v1/departments/1000000000")
				.with(httpBasic("userok","userok")))
				// .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void _d() throws Exception {
		Department department = new Department();
		department.setDepartmentName("Эникейщики и прочая шушваль");
		//String departmentJson = json(department);
		String departmentJson = "{\"departmentName\":\"Эникейщики\"}";
		this.mockMvc.perform(post("/personnel/api/v1/departments")
				.with(httpBasic("operator","operator"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(departmentJson))
				.andExpect(status().isCreated());
	}
	


}
