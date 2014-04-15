package com.codenvy.simulator.dao.file;

import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.dao.file.comparator.ComparatorEmployeeBySecondName;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.entity.EmployeeWithFixedSalary;
import com.codenvy.simulator.entity.EmployeeWithHourlyWages;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;

import static com.sun.org.apache.xerces.internal.util.XMLChar.trim;

/**
 * Created by Andrienko Aleksander on on 25.03.14.
 */
public class EmployeeDaoImplFile extends FileStorage implements EmployeeDao{

    private FileManager fileManager = FileManager.getInstance();

    private Path path = Paths.get(Constant.pathToEmployeeFile);

    @Override
    public void saveOrUpdate(Employee employee) {
        if(!IdCompanyIsRight(employee)) {
            return;
        }
        List<String> lines = new ArrayList<String>();
        lines.addAll(fileManager.readFile(path));
        if (employee.getId() != null && employee.getId() > 0) {
            String lineEmployee = findLineForId(employee.getId(), lines);
            if (lineEmployee != null) {
                Employee employeeWithSameId = generateEmployeeFromLine(lineEmployee);
                employee = updateEmployee(employee, employeeWithSameId);
                lines.remove(lineEmployee);
            }
        } else {
            employee.setId(generateId(lines));
        }
        Class cl = employee.getClass();
        String dType = cl.getSimpleName();

        String line = "{" + employee.getId() + ", "
                + employee.getFirstName() + ", "
                + employee.getSecondName() + ", "
                + employee.getDataOfBirth() + ", "
                + employee.getSalary() + ", "
                + employee.getIdCompany() + ", "
                + dType + "}";
        lines.add(line);
        fileManager.writeToFile(path, lines);
    }

    @Override
    public void delete(Employee employee) {
        List<String> lines = new ArrayList<String>();
        lines.addAll(fileManager.readFile(path));
        lines.remove(findLineForId(employee.getId(), lines));
        fileManager.writeToFile(path, lines);
    }

    private boolean IdCompanyIsRight(Employee employee) {
        if(employee.getIdCompany() != null && employee.getIdCompany() > 0.0 ) {
            CompanyDaoImplFile companyDao = new CompanyDaoImplFile();
            if (companyDao.findCompanyForId(employee.getIdCompany()) != null) {
                return true;
            }
        }
        return false;
    }

    private Employee updateEmployee(Employee employee, Employee employeeWithSameId) {
        if (employee.getDataOfBirth() != null) {
            employeeWithSameId.setDataOfBirth(employee.getDataOfBirth());
        }
        if (employee.getFirstName() != null) {
            employeeWithSameId.setFirstName(employee.getFirstName());
        }
        if (employee.getSecondName() != null) {
            employeeWithSameId.setSecondName(employee.getSecondName());
        }
        if (employee.getSalary() != null) {
            employeeWithSameId.setSalary(employee.getSalary());
        }
        if (employee.getIdCompany() != null) {
            employeeWithSameId.setIdCompany(employee.getIdCompany());
        }
        return employeeWithSameId;
    }

    private Employee generateEmployeeFromLine(String line) {
        if (line == null || line.equals("")) {
            return null;
        }
        int id = getIdFromLine(line);
        line = line.substring(line.indexOf(","));
        int beginLine = 0;
        int endLine = 0;
        String[] employeeParam = new String[5];
        for(int i = 0; i < 5; i++) {
            beginLine = line.indexOf(",", endLine);
            endLine = line.indexOf(",", beginLine + 1);
            employeeParam[i] = trim(line.substring(beginLine + 1, endLine));
        }
        String dType = trim(line.substring(endLine + 1 , line.length() - 1));
        Employee employee = null;
        if (dType.equals("EmployeeWithFixedSalary")) {
            employee = new EmployeeWithFixedSalary();
        }
        if (dType.equals("EmployeeWithHourlyWages")) {
            employee = new EmployeeWithHourlyWages();
        }
        employee.setId(id);
        employee.setFirstName(employeeParam[0]);
        employee.setSecondName(employeeParam[1]);
        employee.setDataOfBirth(Date.valueOf(employeeParam[2]));
        employee.setSalary(Double.parseDouble(employeeParam[3]));
        employee.setIdCompany(Integer.parseInt(employeeParam[4]));
        return employee;
    }

    public List<Employee> getAllEmployeeByIdCompany(int idCompany) {
        List<String> listLineEmployee = fileManager.readFile(path);
        List<Employee> employees = new ArrayList<Employee>();

        for (String line: listLineEmployee) {
            Employee employee = generateEmployeeFromLine(line);
            if (employee.getIdCompany() == idCompany) {
                employees.add(employee);
            }
        }
        return employees;
    }

    @Override
    public List<Employee> findEmployeesWithFirstName(String name, int idCompany) {
        List<Employee> employees = getAllEmployeeByIdCompany(idCompany);
        List<Employee> employeeList = new ArrayList<Employee>();
        for (Employee employee: employees) {
            if (employee.getFirstName().equals(name)) {
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    @Override
    public List<Employee> orderBySalary(int idCompany) {
        List<Employee> employees = getAllEmployeeByIdCompany(idCompany);
        Collections.sort(employees, new ComparatorEmployeeBySecondName());
        return employees;
    }

    @Override
    public List<Employee> findEmployeeWithSecondName(int idCompany) {
        List<Employee> employees = getAllEmployeeByIdCompany(idCompany);
        Collections.sort(employees, new ComparatorEmployeeBySecondName());
        return employees;
    }
}
