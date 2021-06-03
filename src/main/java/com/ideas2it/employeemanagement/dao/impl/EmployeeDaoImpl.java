package com.ideas2it.employeemanagement.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.employeemanagement.dao.EmployeeDao;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.exception.EmployeeManagementException;
import com.ideas2it.logger.EmployeeManagementLogger;
import com.ideas2it.sessionfactory.SessionFactoryYielder;

/**
 * EmployeeDaoImpl performs employee CRUD operations at database by using connection provided by
 * databaseconnection package and by implementing EmployeeDao interface
 *
 * @version 1.1   30-04-2021
 *
 * @author Gopal G
 */

public class EmployeeDaoImpl implements EmployeeDao{
	EmployeeManagementLogger logger = new EmployeeManagementLogger(EmployeeDaoImpl.class);
	
   /**
    * {@inheritdoc}
    */
    public boolean addEmployee(Employee employee) throws EmployeeManagementException {
        boolean success = false;
        Session session =  null;
        Transaction transaction = null;
        try {
            session = SessionFactoryYielder.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            success = true;
            logger.logInfo("Employee added successfully");
        } catch (HibernateException hibernateException) {
            logger.logError(hibernateException);
            try {
                if (!success && null != transaction) {
                    transaction.rollback();
                }
            } catch (Exception exception) {
                logger.logError(exception);
            }
            throw new EmployeeManagementException("Failure to add Employee");
        } finally {
        	closeSession(session);
        }
        return success;
    }
	
   /**
    * {@inheritdoc}
    */
    public Employee fetchIndividualEmployee(String employeeID) throws EmployeeManagementException {
        Employee employee = null;
        Session session = null;
        try {
            session = SessionFactoryYielder.getSessionFactory().openSession();
            employee = (Employee) session.get(Employee.class, employeeID);
            logger.logInfo("Employee fetch done");
        } catch (HibernateException hibernateException) {
            logger.logError(hibernateException);
            throw new EmployeeManagementException("Failure in search employee operation");
        } finally {
        	closeSession(session);
        }
        return employee;
    }
	
   /**
    * {@inheritdoc}
    */
    public boolean updateEmployeeDetails(Employee employee) throws EmployeeManagementException {
        boolean success = false;
        Session session = null;
		Transaction transaction = null;
        try {
            session = SessionFactoryYielder.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
            success = true;
            logger.logInfo("Employee update done");
        } catch (HibernateException hibernateException) {
            logger.logError(hibernateException);
            try {
                if (!success && null != transaction) {
                    transaction.rollback();
                }
			} catch (Exception exception) {
                logger.logError(exception);
            }
            throw new EmployeeManagementException("Failure to update Employee");
        } finally {
        	closeSession(session);
        }
        return success;
    }
	
   /**
    * {@inheritdoc}
    */
    public List<Employee> getAllEmployees() throws EmployeeManagementException {
        Employee employee = null;
        Session session = null;
        List<Employee> allEmployees = null;
        try {
            session = SessionFactoryYielder.getSessionFactory().openSession();
            Query query = session.createQuery("from Employee where isDeleted=false");
            allEmployees = query.list();
            logger.logInfo("Employees fetch done");
        } catch (HibernateException hibernateException) {
            logger.logError(hibernateException);
            throw new EmployeeManagementException("Failure to fetch all employees data");
        } finally {
        	closeSession(session);
        }
        return allEmployees;
    }
    
    
    /**
     * {@inheritdoc}
     */
     public List<Employee> getDeletedEmployees() throws EmployeeManagementException {
         Employee employee = null;
         Session session = null;
         List<Employee> deletedEmployees = null;
         try {
             session = SessionFactoryYielder.getSessionFactory().openSession();
             Query query = session.createQuery("from Employee where isDeleted=true");
             deletedEmployees = query.list();
             logger.logInfo("Deleted employees fetch done");
         } catch (HibernateException hibernateException) {
             logger.logError(hibernateException);
             throw new EmployeeManagementException("Failure to fetch deleted employees");
         } finally {
             closeSession(session);
         }
         return deletedEmployees;
     }
     
    /**
     * {@inheritdoc}
     */
     public void closeSession(Session session) {
    	 if (null != session) {
             session.close();
         }
     }
}