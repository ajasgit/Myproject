package com.ems.Service.Imp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ems.Exception.ResourceNotFoundException;
import com.ems.Model.EmployeeModel;

import QueryDetails.EmployeeQuery;

@Component
public class EmployeeServiceImp {

	private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImp.class);

	@Autowired
	private static JdbcTemplate jdbcTemplate;

	//save employee
 @CacheEvict(value="employees", allEntries = true)
	public void save(EmployeeModel empModel) {
		log.info("Save Started");

		try {
			jdbcTemplate.update(EmployeeQuery.ADD_EMPLOYEE, empModel.getEmpAddress(),
					empModel.getEmpAge(), empModel.getEmpMan(), empModel.getEmpName(), empModel.getEmpSalary());

		} catch (Exception e) {

			throw new ResourceNotFoundException("Save failed");
		}
	}

	//delete employee by id
	//@CacheEvict(value="employees", allEntries = true)
	public String deleteById(Long id) {

		jdbcTemplate.update(EmployeeQuery.DELETE_BY_ID, id);
		return "deleted employee where id=" + id;

	}

	//search employess under manager
	public List<EmployeeModel> findByManager(String name) {
		try {
			return jdbcTemplate.query(EmployeeQuery.FIND_BY_MANAGER_NAME,
					BeanPropertyRowMapper.newInstance(EmployeeModel.class), name);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Not found Employee with id = " + name);
		}
	}

	//find employee by id
	@Cacheable("employees")
	public EmployeeModel findById(Long id) {
		try {

			return jdbcTemplate.queryForObject(EmployeeQuery.FIND_BY_ID,

					BeanPropertyRowMapper.newInstance(EmployeeModel.class), id);

		} catch (Exception e) {
			throw new ResourceNotFoundException("Not found Employee with id = " + id);
		}

	}

	//all employees details
	@Cacheable("employees")
	public List<EmployeeModel> findAll() {
		
		return jdbcTemplate.query(EmployeeQuery.FIND_ALL, BeanPropertyRowMapper.newInstance(EmployeeModel.class));
	}

	@CacheEvict(value="employees", allEntries = true)
	public int update(EmployeeModel e, int id) {
		return jdbcTemplate.update(EmployeeQuery.EMP_UPDATE,
				new Object[] { e.getEmpAddress(), e.getEmpAge(), e.getEmpMan(), e.getEmpName(), e.getEmpSalary(), id });
	}

	
	
	
	
	

	
}
