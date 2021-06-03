package com.ideas2it.sessionfactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

/**
 * SessionFactoryYielder is a singleton class equipped with methods for providing SessionFactory instance
 * required by hibernate.
 *
 * @version 1.0   27-04-2021
 *
 * @author Gopal G
 */

public class SessionFactoryYielder {
    private static SessionFactoryYielder sessionFactoryYielder = null;
    private static SessionFactory sessionFactory = null;

   /**
    * Constructor that renders singleton characteristics to SessionFactoryYielder
    */
    private SessionFactoryYielder() {
    }
    
   /**
    * Returns an instance of SessionFactory
    *
    * @return sessionFactoryYielder an instance of the SessionFactoryYielder class
    */
    public static SessionFactoryYielder getInstance() {
        if (null == sessionFactoryYielder) {
            sessionFactoryYielder = new SessionFactoryYielder();
        }
        return sessionFactoryYielder;
    }
	
   /**
    * Creates a SessionFactory object using hibernate configuration and returns it on request
    *
    * @return sessionfactory An instance of SessionFactory to create sessions
    */
    public static SessionFactory getSessionFactory() {
        try {
            if (null == sessionFactory) {
                //System.out.println("connecting...");
               /* sessionFactory = new Configuration().configure
                        ("/resources/hibernate/properties/hibernate.cfg.xml").buildSessionFactory();*/
                sessionFactory = new Configuration().configure
                        ("/resources/hibernate/properties/hibernate.cfg.xml").buildSessionFactory();
                /*sessionFactory = new Configuration().configure
                        ("hibernate.cfg.xml").buildSessionFactory();*/
            }
        } catch(HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
        return sessionFactory;
    }
}




/*
public class SessionFactoryYielder {
	private static SessionFactory sessionFactory = null;

	private SessionFactoryYielder() {
	}

	/**
	 * It is used to  establish and provide connection to SQL database
	 *
	public static SessionFactory getSessionFactory() {

		if (null == sessionFactory) {     
			Configuration configuration = new Configuration().
					configure("hibernate/properties/hibernate.cfg.xml");   
			sessionFactory =  configuration.buildSessionFactory();
		}
		return sessionFactory;
	}
}*/