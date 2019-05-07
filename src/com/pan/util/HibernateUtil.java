package com.pan.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate工厂类
 * @author Administrator
 *
 */
public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	/**
	 * 获取静态工厂
	 * @return
	 */
    private static SessionFactory buildSessionFactory() {
    	Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
    }

    /**
     * 获取session工厂
     * @return
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
