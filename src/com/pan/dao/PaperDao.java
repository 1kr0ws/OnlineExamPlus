package com.pan.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.pan.model.Paper;
import com.pan.util.HibernateUtil;

/**
 * 试卷DAO类
 * @author Administrator
 *
 */
public class PaperDao {

	/**
	 * 获取所有试卷
	 * @return
	 * @throws Exception
	 */
	public List<Paper> getPapers()throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createQuery("from Paper");
		@SuppressWarnings("unchecked")
		List<Paper> paperList=(List<Paper>)query.list();
		session.getTransaction().commit();
		return paperList;
	}
	
	/**
	 * 获取指定试卷
	 * @param paperId
	 * @return
	 * @throws Exception
	 */
	public Paper getPaper(String paperId)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Paper paper=(Paper)session.get(Paper.class, Integer.parseInt(paperId));
		session.getTransaction().commit();
		return paper;
	}
	
	/**
	 * 保存试卷实体
	 * @param paper
	 * @throws Exception
	 */
	public void savePaper(Paper paper)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.merge(paper);
		session.getTransaction().commit();
	}
	
	/**
	 * 删除试卷
	 * @param paper
	 * @throws Exception
	 */
	public void paperDelete(Paper paper)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(paper);
		session.getTransaction().commit();
	}
}
