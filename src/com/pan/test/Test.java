package com.pan.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.pan.util.HibernateUtil;

public class Test {

	public static void main(String[] args) {
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession(); // ����һ��session
	    session.beginTransaction(); // ��������
	    
	   
	    
	    session.getTransaction().commit(); // �ύ����
	    session.close(); // �ر�session
	}
}
