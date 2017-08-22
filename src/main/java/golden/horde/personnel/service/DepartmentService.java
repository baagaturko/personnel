package golden.horde.personnel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import golden.horde.personnel.domain.Department;
import golden.horde.personnel.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentService {

	@Autowired
	private DepartmentRepository repository;
	
	public Iterable<Department> findAll(){
		return repository.findAll();
	}	
	public Department find(Long id) {
		return repository.find(id);
	}
	
	public Long save(Department department) {
		return repository.save(department);
	}
	
	public void update(Department department) {
		repository.update(department);
	}
	
	public void delete(Long id) {
		repository.delete(id);
	}
}
