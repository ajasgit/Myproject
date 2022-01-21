package com.ems.Service.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ems.Model.ProjectModel;

import QueryDetails.ProjectQuery;

@Service
public class ProjectServiceImp {

	@Autowired
	JdbcTemplate jdbcProject;

	public int save(ProjectModel proModel) {

		return jdbcProject.update(ProjectQuery.ADD_PROJECT, proModel.getId(), proModel.getfDate(), proModel.getpName(),
				proModel.getsDate());

	}

	public List<ProjectModel> findAll() {
		return jdbcProject.query(ProjectQuery.FIND_ALL, BeanPropertyRowMapper.newInstance(ProjectModel.class));
	}

	public List<ProjectModel> findByProjectName(String name) {
		return jdbcProject.query(ProjectQuery.FIND_PROJECT_BYNAME, BeanPropertyRowMapper.newInstance(ProjectModel.class), name);
	}

	public List<ProjectModel> findHighestId() {
		return jdbcProject.query(ProjectQuery.FIND_HIGHEST_ID, BeanPropertyRowMapper.newInstance(ProjectModel.class));
	}

}
