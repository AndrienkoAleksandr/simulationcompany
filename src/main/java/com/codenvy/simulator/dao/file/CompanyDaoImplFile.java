package com.codenvy.simulator.dao.file;

import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.dao.CompanyDao;
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
                + company.getFullName() + Constant.FILE_BASE_DATE_SEPARATOR +" "
                + company.getProfit() + "}";
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
        return companyWithSameId;
    }

    private Company generateCompanyFromLine(String line) {
        Company company = new Company();
        company.setId(getIdFromLine(line));
        int beginLine = line.indexOf(Constant.FILE_BASE_DATE_SEPARATOR) + 1;
        int endLine = line.indexOf(Constant.FILE_BASE_DATE_SEPARATOR, beginLine);
        String name = trim(line.substring(beginLine, endLine)) + 1;
        company.setFullName(name);
        beginLine = line.indexOf(Constant.FILE_BASE_DATE_SEPARATOR, endLine) + 1;
        endLine = line.indexOf("}");
        String profit = trim(line.substring(beginLine, endLine));
        company.setProfit(Double.parseDouble(profit));
        return company;
    }
}
