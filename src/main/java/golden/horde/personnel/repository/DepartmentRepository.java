package golden.horde.personnel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import golden.horde.personnel.domain.Department;

@Repository
public class DepartmentRepository {

	@PersistenceContext	
	private EntityManager em;
	
	public Iterable<Department> findAll(){
		String hql = "FROM Department as d ORDER BY d.departmentName";
		List<Department> list =  em.createQuery(hql, Department.class).getResultList();	
		return list;
	}	
	public Department find(Long id) {
		Department department = em.find(Department.class, id);
		return department;
	}
	
	public Long save(Department department) {
		em.persist(department);
		em.flush();
		return department.getDepartmentId();
	}
	
	public void update(Department department) {
		em.merge(department);
	}
	
	public void delete(Long id) {
		Department department = em.find(Department.class, id);
		//if (department == null)
		//	throw new NoSuchEntityException();
		em.remove(department);
	}
	
}
