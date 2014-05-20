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
                + company.getTotalProfit() + Constant.FILE_BASE_DATE_SEPARATOR + " "
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
        if (company.getTotalProfit() != null) {
            companyWithSameId.setTotalProfit(company.getTotalProfit());
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
        String[] companyParam = new String[4];
        String separator = String.valueOf(Constant.FILE_BASE_DATE_SEPARATOR);
        for(int i = 0; i < companyParam.length; i++) {
            beginLine = line.indexOf(Constant.FILE_BASE_DATE_SEPARATOR, endLine);
            if (i == companyParam.length - 1) {separator = "}";}
            endLine = line.indexOf(separator, beginLine + 1);
            companyParam[i] = trim(line.substring(beginLine + 1, endLine));
        }
        if (!companyParam[0].equals("null")) {
            company.setFullName(companyParam[0]);
        }
        if (!companyParam[1].equals("null")) {
            company.setProfit(Double.parseDouble(companyParam[1]));
        }
        if (!companyParam[2].equals("null")) {
            company.setTotalProfit(Double.parseDouble(companyParam[2]));
        }
        if (!companyParam[3].equals("null") ) {
            company.setTypeOfSavingData(companyParam[3]);
        }
        return company;
    }
}