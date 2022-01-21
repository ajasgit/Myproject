package com.ems.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.Exception.ResourceNotFoundException;
import com.ems.Model.EmployeeModel;
import com.ems.Service.ReportService;
import com.ems.Service.Imp.EmployeeServiceImp;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@Controller
public class EmployeeController {

	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeServiceImp empService;

	@Autowired
	ReportService reportService;
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@Valid @RequestBody EmployeeModel model) {

		log.info("Save Employee");
		 empService.save(model);
		 return"Data Saved Successfully";
		 
	}

	@PostMapping("/getEmployeeById")
	public ResponseEntity<?> getEmployeeById(@RequestParam("id") Long id) {
		EmployeeModel employeeModel = empService.findById(id);
		if (employeeModel != null) {
			return new ResponseEntity<>(employeeModel, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResourceNotFoundException("Id Not found"), HttpStatus.OK);
		}
	}

	@PostMapping("/deleteEmployeeById")
	public String deleteEmployeeById(@RequestParam("id") Long id) {

		EmployeeModel employeeModel = empService.findById(id);
		if (employeeModel != null) {
			return empService.deleteById(id);

		} else {

			throw new ResourceNotFoundException("Not found Employee with id = " + id);

		}

	}

	@PostMapping("/findByManager")
	public ResponseEntity<List<EmployeeModel>> findByManager(@RequestParam("name") String name) {

		try {
			return new ResponseEntity<List<EmployeeModel>>(empService.findByManager(name), HttpStatus.OK);
		} catch (Exception e) {

			throw new ResourceNotFoundException("Not found Employee with id = " + name);

		}
	}

	@GetMapping("/getallemployee")
	public ResponseEntity<List<EmployeeModel>> getAllEmployee() {
		return new ResponseEntity<List<EmployeeModel>>(empService.findAll(), HttpStatus.OK);

	}

	@PutMapping("/employeeUpdate/{id}")
	public String update(@RequestBody EmployeeModel e, @PathVariable int id) {
		try {
			return empService.update(e, id) + " Employee(s) updated successfully";
		} catch (Exception ex) {

			throw new ResourceNotFoundException("not valida data");
		}

	}
	
	@GetMapping("/get/{format}")
	public String pdfEmployee( @PathVariable String format) throws FileNotFoundException, JRException {

		log.info("Generate Pdf");
		 reportService.exportReport(format);
		 return"Successfully";
		 
	}
	
	
	
	@GetMapping("/report/{format}")
	public String exportReport(@PathVariable String reportFormat) throws FileNotFoundException, JRException {
		
		
		List<EmployeeModel> employees=empService.findAll();
		File file=ResourceUtils.getFile("classpath:report.jrxml");
		JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());

				JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(employees);
				Map<String,Object> parameter=new HashMap();
				parameter.put("created by", "AJAS");
				JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport, parameter,dataSource);
				if(reportFormat.equalsIgnoreCase("html")) {
					JasperExportManager.exportReportToHtmlFile(jasperPrint, "D:\\STS-Works\\MyProject\\src\\main\\resources\\report"+"\\employee.html");
					
				}
				
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\STS-Works\\MyProject\\src\\main\\resources\\report"+"\\report.pdf");
			
				}	
				
				return "created " ;
			}
	
}
