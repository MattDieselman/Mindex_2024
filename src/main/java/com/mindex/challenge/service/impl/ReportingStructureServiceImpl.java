package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    private int totalNumberReports;

    // Gather and format all direct reports for this employee and all under them.
    private List<Employee> formatDirectReports(Employee currentEmployee){

        List<Employee> directReports = currentEmployee.getDirectReports();
        List<Employee> tempReports = new ArrayList<Employee>();

        if(directReports != null) {
            for (Employee element : directReports) {

                // Increment the total number of employees we've hit so far
                totalNumberReports++ ;
                
                // Find the proper employee and add them to the tempReports list
                tempReports.add( employeeRepository.findByEmployeeId( element.getEmployeeId() ));
                
            }
        }
        return tempReports;
    }

    private Employee getAllReports(Employee employee) {
        LOG.debug("Retrieving all reports for employee [{}]", employee.getFirstName()+employee.getLastName());
        employee.setDirectReports(formatDirectReports(employee));

        // Recursively loop through all children of this employee
        for (Employee element : employee.getDirectReports()) {
            getAllReports(element);
        }
        return employee;
    }


    /* Note to call out:
        This will have issues if an employee has multiple managers or reports.
        We could theoretically put a unique constraint on the overall list of reports,
        but this could impact reporting structure formatting.
        We could also just accept that this will be a know issue that may keep the formatting
        proper but will result in inflated "reports" values
    */
    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Retrieving all reports for employee id [{}]", id);
        totalNumberReports=0;

        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        ReportingStructure reportingStructure = new ReportingStructure(getAllReports(employee), totalNumberReports);

        return reportingStructure;
    }

}
