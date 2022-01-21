package com.ems.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.ems.Model.EmployeeModel;
import com.ems.Service.Imp.EmployeeServiceImp;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
@Service
public class ReportService {

	@Autowired
	public EmployeeServiceImp employeeServiceImp;
	
	
	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
		List<EmployeeModel> employees=employeeServiceImp.findAll();
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
