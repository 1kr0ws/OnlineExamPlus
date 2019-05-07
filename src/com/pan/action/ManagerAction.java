package com.pan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.pan.dao.ManagerDao;
import com.pan.model.Manager;
import com.opensymphony.xwork2.ActionSupport;

public class ManagerAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	
	private ManagerDao managerDao=new ManagerDao();
	
	private Manager manager;
	private String error;
	
	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	/**
	 * ��¼��֤
	 * @return
	 * @throws Exception
	 */
	public String login()throws Exception{
		HttpSession session=request.getSession();
		Manager currentUser=managerDao.login(manager);
		if(currentUser==null){
			error="�û��������������!";
			return ERROR;
		}else{
			session.setAttribute("currentUser", currentUser);
			return SUCCESS;
		}
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
