package com.pan.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.pan.model.PageBean;
import com.pan.model.Question;
import com.pan.model.Student;
import com.pan.util.HibernateUtil;
import com.pan.util.StringUtil;

/**
 * 问题DAO类
 * @author Administrator
 *
 */
public class QuestionDao {

	/**
	 * 通过问题id获取问题实体
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	public Question getQuestion(String questionId)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Question question=(Question) session.get(Question.class, Integer.parseInt(questionId));
		session.getTransaction().commit();
		return question;
	}
	
	/**
	 * 判断执行的试卷下有无题目
	 * @param paperId
	 * @return
	 * @throws Exception
	 */
	public boolean existQuestionByPaperId(String paperId)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createQuery("from Question as q where q.paper.id=:paperId");
		query.setString("paperId", paperId);
		@SuppressWarnings("unchecked")
		List<Student> studentList=(List<Student>)query.list();
		session.getTransaction().commit();
		if(studentList.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取所有题目
	 * @param s_question
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public List<Question> getQuestions(Question s_question,PageBean pageBean)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer hql=new StringBuffer("from Question");
		if(StringUtil.isNotEmpty(s_question.getSubject())){
			hql.append(" and subject like '%"+s_question.getSubject()+"%'");
		}
		Query query=session.createQuery(hql.toString().replaceFirst("and", "where"));
		if(pageBean!=null){
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getPageSize());
		}
		@SuppressWarnings("unchecked")
		List<Question> questionList=(List<Question>)query.list();
		session.getTransaction().commit();
		return questionList;
	}
	
	/**
	 * 查询试题记录数
	 * @param s_question
	 * @return
	 * @throws Exception
	 */
	public int questionCount(Question s_question)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer sql=new StringBuffer("select count(*) from t_question");
		if(StringUtil.isNotEmpty(s_question.getSubject())){
			sql.append(" and subject like '%"+s_question.getSubject()+"%'");
		}
		Query query=session.createSQLQuery(sql.toString().replaceFirst("and", "where"));
		int count=((BigInteger)query.uniqueResult()).intValue();
		session.getTransaction().commit();
		return count;
	}
	

	
	/**
	 * 保存试题实体
	 * @param question
	 * @throws Exception
	 */
	public void saveQuestion(Question question)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.merge(question);
		session.getTransaction().commit();
	}
	
	/**
	 * 删除试题
	 * @param question
	 * @throws Exception
	 */
	public void deleteQuestion(Question question)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(question);
		session.getTransaction().commit();
	}
}
