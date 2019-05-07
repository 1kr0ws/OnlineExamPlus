package com.pan.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.pan.dao.StudentDao;
import com.pan.model.PageBean;
import com.pan.model.Student;
import com.pan.util.DateUtil;
import com.pan.util.PageUtil;
import com.pan.util.PropertiesUtil;
import com.pan.util.ResponseUtil;
import com.pan.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ѧ��Action��
 * @author Administrator
 *
 */
public class StudentAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StudentDao studentDao=new StudentDao();
	private HttpServletRequest request;
	
	private String mainPage;
	
	private Student student;
	private String error;
	
	private String page;
	private int total;
	private String pageCode;
	
	private List<Student> studentList;
	
	private Student s_student;
	
	private String id;  // ѧ�����
	
	private String title; // ����
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	public String getMainPage() {
		return mainPage;
	}
	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}
	
	
	
	
	public String getPageCode() {
		return pageCode;
	}
	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public Student getS_student() {
		return s_student;
	}
	public void setS_student(Student s_student) {
		this.s_student = s_student;
	}
	public List<Student> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * ��¼��֤
	 * @return
	 * @throws Exception
	 */
	public String login()throws Exception{
		HttpSession session=request.getSession();
		Student currentUser=studentDao.login(student);
		if(currentUser==null){
			error="׼��֤�Ż����������!";
			return ERROR;
		}else{
			session.setAttribute("currentUser", currentUser);
			return SUCCESS;
		}
	}
	
	/**
	 * �޸�����Ԥ����
	 * @return
	 * @throws Exception
	 */
	public String preUpdatePassword()throws Exception{
		mainPage="student/updatePassword.jsp";
		return SUCCESS;
	}
	
	/**
	 * �޸�����
	 * @return
	 * @throws Exception
	 */
	public String updatePassword()throws Exception{
		Student s=studentDao.getStudentById(student.getId());
		s.setPassword(student.getPassword());
		studentDao.saveStudent(s);
		mainPage="student/updateSuccess.jsp";
		return SUCCESS;
	}
	
	/**
	 * ��ѯѧ����Ϣ
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception{
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		if(s_student!=null){
			session.setAttribute("s_student", s_student);
		}else{
			Object o=session.getAttribute("s_student");
			if(o!=null){
				s_student=(Student)o;
			}else{
				s_student=new Student();
			}
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		studentList=studentDao.getStudents(s_student,pageBean);
		total=studentDao.studentCount(s_student);
		pageCode=PageUtil.genPagation(request.getContextPath()+"/student!list",total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		mainPage="student/studentList.jsp";
		return SUCCESS;
	}
	
	/**
	 * ��ȡѧ��
	 * @return
	 * @throws Exception
	 */
	public String getStudentById()throws Exception{
		student=studentDao.getStudent(id);
		mainPage="student/studentSave.jsp";
		return SUCCESS;
	}
	
	/**
	 * ����ѧ��
	 * @return
	 * @throws Exception
	 */
	public String saveStudent()throws Exception{
		if(StringUtil.isEmpty(student.getId())){
			student.setId("JS"+DateUtil.getCurrentDateStr());			
		}
		studentDao.saveStudent(student);
		return "save";
	}
	
	/**
	 * ɾ��ѧ��
	 * @return
	 * @throws Exception
	 */
	public String deleteStudent()throws Exception{
		student=studentDao.getStudent(id);
		studentDao.studentDelete(student);
		JSONObject resultJson=new JSONObject();
		resultJson.put("success",true);
		ResponseUtil.write(resultJson,ServletActionContext.getResponse());
		return null;
	}
	
	/**
	 * Ԥ��Ӳ���
	 * @return
	 * @throws Exception
	 */
	public String preSave()throws Exception{
		if(StringUtil.isNotEmpty(id)){
			student=studentDao.getStudent(id);
			title="�޸�ѧ����Ϣ";
		}else{
			title="���ѧ����Ϣ";
		}
		mainPage="student/studentSave.jsp";
		return SUCCESS;
	}
	
	/**
	 * ע���û�
	 * @throws Exception
	 */
	public String logout()throws Exception{
		request.getSession().invalidate();
		return "logout";
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	


}
