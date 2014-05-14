package com.codenvy.simulator.dao.file;

import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.entity.Company;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xerces.internal.util.XMLChar.trim;

/**
 * Created by Andrienko Aleksander on 25.03.14.
 */
public class CompanyDaoImplFile extends FileStorage implements CompanyDao{

    private FileManager fileManager = FileManager.getInstance();

    public static Path path = Paths.get(Constant.PATH_TO_COMPANY_FILE);

    @Override
    public void saveOrUpdate(Company company) {
        List<String> lines = new ArrayList<String>();
        lines.addAll(fileManager.readFile(path));
        if (company.getTypeOfSavingData() == null) {
            throw new IllegalArgumentException("field type_saving_data can't be null!!!");
        }
        if (company.getId() != null && company.getId() > 0) {
            String lineCompany = findLineForId(company.getId(), lines);
            if (lineCompany != null) {
                Company companyWithSameId = generateCompanyFromLine(lineCompany);
                company = updateCompany(company, companyWithSameId);
                lines.remove(lineCompany);
            }
        } else {
            company.setId(generateId(lines));
        }
        String line = "{" + company.getId() + Constant.FILE_BASE_DATE_SEPARATOR +" "
                + company.getFullName() + Constant.FILE_BASE_DATE_SEPARATOR + " "
                + company.getProfit() + Constant.FILE_BASE_DATE_SEPARATOR + " "
                + company.getTypeOfSavingData() + "}";
        lines.add(line);
        fileManager.writeToFile(path, lines);
    }

    @Override
    public void deleteFromId(Company company) {
        List<String> lines = new ArrayList<String>();
        lines.addAll(fileManager.readFile(path));
        lines.remove(findLineForId(company.getId(), lines));
        fileManager.writeToFile(path, lines);
    }

    @Override
    public Company getCompanyById(int idCompany) {
        Company company = findCompanyForId(idCompany);
        if (company != null) {
            EmployeeDao employeeDao = new EmployeeDaoImplFile();
            company.setEmployees(employeeDao.getEmployeesByCompanyId(idCompany));
            return company;
        }
        return null;
    }

    public Company findCompanyForId(int id) {
        List<String> listCompany = fileManager.readFile(path);
        String lineCompany = findLineForId(id, listCompany);
        if (lineCompany != null) {
            return generateCompanyFromLine(lineCompany);
        }
        return null;
    }

    private Company updateCompany(Company company, Company companyWithSameId) {
        if (company.getProfit() != null) {
            companyWithSameId.setProfit(company.getProfit());
        }
        if (company.getFullName() != null) {
            companyWithSameId.setFullName(company.getFullName());
        }
        if (company.getTypeOfSavingData() != null) {
            companyWithSameId.setTypeOfSavingData(company.getTypeOfSavingData());
        }
        return companyWithSameId;
    }

    private Company generateCompanyFromLine(String line) {
        Company company = new Company();
        company.setId(getIdFromLine(line));
        line = line.substring(line.indexOf(Constant.FILE_BASE_DATE_SEPARATOR));
        int beginLine = 0;
        int endLine = 0;
        String[] companyParam = new String[3];
        String separator = String.valueOf(Constant.FILE_BASE_DATE_SEPARATOR);
        for(int i = 0; i < 3; i++) {
            beginLine = line.indexOf(Constant.FILE_BASE_DATE_SEPARATOR, endLine);
            if (i == 2) {separator = "}";}
            endLine = line.indexOf(separator, beginLine + 1);
            companyParam[i] = trim(line.substring(beginLine + 1, endLine));
        }
        company.setFullName(companyParam[0]);
        company.setProfit(Double.parseDouble(companyParam[1]));
        company.setTypeOfSavingData(companyParam[2]);
        return company;
    }
}
