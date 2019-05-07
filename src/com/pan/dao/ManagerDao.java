package com.pan.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.pan.model.Manager;
import com.pan.util.HibernateUtil;

/**
 * 管理员DAO类
 * @author Administrator
 *
 */
public class ManagerDao {

	/**
	 * 管理员登录验证
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Manager login(Manager manager)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createQuery("from Manager as m where m.userName=:userName and m.password=:password ");
		query.setString("userName", manager.getUserName());
		query.setString("password", manager.getPassword());
		Manager resultManager=(Manager)query.uniqueResult();
		session.getTransaction().commit();
		return resultManager;
	}
}
