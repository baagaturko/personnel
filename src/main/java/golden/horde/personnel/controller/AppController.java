package golden.horde.personnel.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import golden.horde.personnel.domain.Department;
import golden.horde.personnel.exception.ResourceNotFoundException;
import golden.horde.personnel.service.DepartmentService;

@RestController
@RequestMapping("/personnel/api/v1")
public class AppController {

	@Autowired
	DepartmentService departmentService;

	// http://localhost:2019/personnel/api/v1/ping
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public ResponseEntity<String> ping() {
		return new ResponseEntity<>("PONG", HttpStatus.OK);
	}

	// http://localhost:2019/personnel/api/v1/departments
	@PreAuthorize("hasAuthority('ROLE_USEROK')")
	@RequestMapping(value = "/departments", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Department>> getAllDepartments() {
		Iterable<Department> departments = departmentService.findAll();
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}

	// http://localhost:2019/personnel/api/v1/3
	@PreAuthorize("hasAuthority('ROLE_USEROK')")
	@RequestMapping(value = "/departments/{departmentId}", method = RequestMethod.GET)
	public ResponseEntity<Department> getDepartment(@PathVariable Long departmentId) {
		Department d = departmentService.find(departmentId);
		if (d == null)
			throw new ResourceNotFoundException("Department with id " + departmentId + " not found");
		return new ResponseEntity<Department>(d, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ROLE_OPERATOR')")
	@RequestMapping(value = "/departments", method = RequestMethod.POST)
	public ResponseEntity<?> createPoll(@RequestBody Department department) {
		Long newId = departmentService.save(department);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newEntityUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newId).toUri();
		responseHeaders.setLocation(newEntityUri);
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ROLE_OPERATOR')")
	@RequestMapping(value = "/departments/{departmentId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateDepartment(@RequestBody Department department, @PathVariable Long departmentId) {
		department.setDepartmentId(departmentId); // ?????
		departmentService.update(department);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ROLE_OPERATOR')")
	@RequestMapping(value = "/departments/{departmentId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteDepartment(@PathVariable Long departmentId) {
		departmentService.delete(departmentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
