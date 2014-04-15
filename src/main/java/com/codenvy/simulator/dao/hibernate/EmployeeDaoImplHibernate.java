package com.codenvy.simulator.dao.hibernate;

import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import java.util.List;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;


/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
public class EmployeeDaoImplHibernate implements EmployeeDao {

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public void saveOrUpdate(Employee employee) {
        Session session = null;
        try {
            session = getSession();
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Employee employee) {
        Session session = null;
        try {
            session = getSession();
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<Employee> findEmployeesWithFirstName(String name, int idCompany) {
        Session session = null;
        List<Employee> employeeList = null;
        try {
            session = getSession();
            session.beginTransaction();
            employeeList = session.createQuery("FROM Employee WHERE firstName=:firstName AND idCompany=:idCompany")
                    .setString("firstName", name)
                    .setInteger("idCompany", idCompany)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employeeList;
    }

    @Override
    public List<Employee> orderBySalary(int idCompany) {
        Session session = null;
        List<Employee> employeeList = null;
        try {
            session = getSession();
            session.beginTransaction();
            employeeList = session.createCriteria(Employee.class)
                    .addOrder(Order.asc("salary"))
                    .add(Expression.like("idCompany", idCompany))
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employeeList;
    }

    @Override
    public List<Employee> findEmployeeWithSecondName(int idCompany) {
        Session session = null;
        List<Employee> employeeList = null;
        try {
            session = getSession();
            session.beginTransaction();
            employeeList = session.createCriteria(Employee.class)
                    .addOrder(Order.asc("secondName"))
                    .add(Expression.like("idCompany", idCompany))
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employeeList;
    }
}
