package com.ideas2it.projectmanagement.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.exception.EmployeeManagementException;
import com.ideas2it.projectmanagement.dao.ProjectDao;
import com.ideas2it.projectmanagement.model.Project;
import com.ideas2it.sessionfactory.SessionFactoryYielder;
import com.ideas2it.logger.EmployeeManagementLogger;

/**
 * ProjectDaoImpl performs project CRUD operations at database by using connection provided by
 * databaseconnection package and by implementing ProjectDao interface
 *
 * @version 1.1   01-05-2021
 *
 * @author Gopal G
 */

public class ProjectDaoImpl implements ProjectDao {
	private EmployeeManagementLogger logger = new EmployeeManagementLogger(ProjectDaoImpl.class);
	
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public boolean addProject(Project project) throws EmployeeManagementException {
        Session session = null;
        Transaction transaction = null;
        boolean success = false;
        try {
            session = SessionFactoryYielder.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
            success = true;
            logger.logInfo("Project added successfully");
        } catch (HibernateException hibernateException) {
            logger.logError(hibernateException);
            try {
                if (null != transaction) {
                    transaction.rollback();
                }
            } catch (Exception e) {
                //e.printStackTrace();
                logger.logError(e);
            }
            throw new EmployeeManagementException("Failure to add new project");
		} finally {
			closeSession(session);
        }
        return success;
    }
	
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public Project fetchIndividualProject(int projectID) throws EmployeeManagementException {
        Project project = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = SessionFactoryYielder.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            project = (Project) session.get(Project.class, projectID);
            logger.logInfo("Project fetched");
        } catch (HibernateException hibernateException) {
            //hibernateException.printStackTrace();
            logger.logError(hibernateException);
            throw new EmployeeManagementException("Failure to fetch individual project");
        } finally {
        	closeSession(session);
        }
        return project;
    }
	
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public boolean updateProjectDetails(Project project) throws EmployeeManagementException {
        Session session = null;
        Transaction transaction = null;
        boolean success = false;
        try {
            session = SessionFactoryYielder.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(project);
            transaction.commit();
            success = true;
            logger.logInfo("Project updated");
        } catch (HibernateException hibernateException) {
            logger.logError(hibernateException);
            try {
            	if (null != transaction) {
                    transaction.rollback();
                }
            } catch (Exception e) {
                //e.printStackTrace();
                logger.logError(e);
            }
            throw new EmployeeManagementException("Failure to update project details");
		} finally {
			closeSession(session);
        }
        return success;
    }
	
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public List<Project> getAllProjects() throws EmployeeManagementException {
        Project project = null;
        Session session = null;
        List<Project> allProjects = null;
        try {
            session = SessionFactoryYielder.getSessionFactory().openSession();
            Query query = session.createQuery("from Project where isDeleted=false");
            allProjects = query.list();
            logger.logInfo("Projects fetched");
        } catch (HibernateException hibernateException) {
            logger.logError(hibernateException);
            throw new EmployeeManagementException("Failure to fetch all projetcs");
        } finally {
        	closeSession(session);
        }
        return allProjects;		
    }
    
    /**
     * {@inheritdoc}
     * @throws EmployeeManagementException 
     */
     public List<Project> getDeletedProjects() throws EmployeeManagementException {
         Project project = null;
         Session session = null;
         List<Project> deletedProjects = null;
         try {
             session = SessionFactoryYielder.getSessionFactory().openSession();
             Query query = session.createQuery("from Project where isDeleted=true");
             deletedProjects = query.list();
             logger.logInfo("Deleted projects fetched");
         } catch (HibernateException hibernateException) {
             logger.logError(hibernateException);
             throw new EmployeeManagementException("Failure to fetch deleted projects");
         } finally {
             closeSession(session);
         }
         return deletedProjects;		
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