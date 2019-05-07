package com.pan.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.pan.model.PageBean;
import com.pan.model.Student;
import com.pan.util.HibernateUtil;
import com.pan.util.StringUtil;

/**
 * 学生DAO类
 * @author Administrator
 *
 */
public class StudentDao {

	/**
	 * 学生登录
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public Student login(Student student)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createQuery("from Student as s where s.id=:id and s.password=:password ");
		query.setString("id", student.getId());
		query.setString("password", student.getPassword());
		Student resultStu=(Student)query.uniqueResult();
		session.getTransaction().commit();
		return resultStu;
	}
	
	/**
	 * 通过id获取学生实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Student getStudentById(String id)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Student student=(Student)session.get(Student.class, id);
		session.getTransaction().commit();
		return student;
	}
	
	/**
	 * 保存学生实体
	 * @param student
	 * @throws Exception
	 */
	public void saveStudent(Student student)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.merge(student);
		session.getTransaction().commit();
	}
	
	/**
	 * 获取所有学生
	 * @return
	 * @throws Exception
	 */
	public List<Student> getStudents(Student s_student,PageBean pageBean)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer hql=new StringBuffer("from Student");
		if(StringUtil.isNotEmpty(s_student.getId())){
			hql.append(" and id like '%"+s_student.getId()+"%'");
		}
		if(StringUtil.isNotEmpty(s_student.getName())){
			hql.append(" and name like '%"+s_student.getName()+"%'");
		}
		Query query=session.createQuery(hql.toString().replaceFirst("and", "where"));
		if(pageBean!=null){
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getPageSize());
		}
		@SuppressWarnings("unchecked")
		List<Student> studentList=(List<Student>)query.list();
		session.getTransaction().commit();
		return studentList;
	}
	
	/**
	 * 查询学生记录数
	 * @param s_student
	 * @return
	 * @throws Exception
	 */
	public int studentCount(Student s_student)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer sql=new StringBuffer("select count(*) from t_student");
		if(StringUtil.isNotEmpty(s_student.getId())){
			sql.append(" and id like '%"+s_student.getId()+"%'");
		}
		if(StringUtil.isNotEmpty(s_student.getName())){
			sql.append(" and name like '%"+s_student.getName()+"%'");
		}
		Query query=session.createSQLQuery(sql.toString().replaceFirst("and", "where"));
		int count=((BigInteger)query.uniqueResult()).intValue();
		session.getTransaction().commit();
		return count;
	}
	
	/**
	 * 保存学生
	 * @param student
	 * @throws Exception
	 */
	public void studentSave(Student student)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.merge(student);
		session.getTransaction().commit();
	}
	
	/**
	 * 删除学生
	 * @param student
	 * @throws Exception
	 */
	public void studentDelete(Student student)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(student);
		session.getTransaction().commit();
	}
	
	/**
	 * 通过id获取学生
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Student getStudent(String id)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Student student=(Student) session.get(Student.class, id);
		session.getTransaction().commit();
		return student;
	}
}
