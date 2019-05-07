package com.pan.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.pan.dao.PaperDao;
import com.pan.dao.QuestionDao;
import com.pan.model.PageBean;
import com.pan.model.Paper;
import com.pan.model.Question;
import com.pan.util.PageUtil;
import com.pan.util.PropertiesUtil;
import com.pan.util.ResponseUtil;
import com.pan.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class QuestionAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	
	private QuestionDao questionDao=new QuestionDao();
	private PaperDao paperDao=new PaperDao();
	
	private List<Question> questionList;
	private List<Paper> paperList;
	private String mainPage;
	
	private String questionId;
	private Question question;
	private String title;
	
	private String page;
	private int total;
	private String pageCode;
	
	private Question s_question;
	
	

	public List<Paper> getPaperList() {
		return paperList;
	}

	public void setPaperList(List<Paper> paperList) {
		this.paperList = paperList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getPageCode() {
		return pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}
	
	

	public Question getS_question() {
		return s_question;
	}

	public void setS_question(Question s_question) {
		this.s_question = s_question;
	}
	
	

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * 查询试题信息
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception{
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		if(s_question!=null){
			session.setAttribute("s_question", s_question);
		}else{
			Object o=session.getAttribute("s_question");
			if(o!=null){
				s_question=(Question)o;
			}else{
				s_question=new Question();
			}
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		questionList=questionDao.getQuestions(s_question,pageBean);
		total=questionDao.questionCount(s_question);
		pageCode=PageUtil.genPagation(request.getContextPath()+"/question!list",total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		mainPage="question/questionList.jsp";
		return SUCCESS;
	}
	
	/**
	 * 通过id获取试题
	 * @return
	 * @throws Exception
	 */
	public String getQuestionById()throws Exception{
		question=questionDao.getQuestion(questionId);
		mainPage="question/questionShow.jsp";
		return SUCCESS;
	}
	
	/**
	 * 预编辑操作
	 * @return
	 * @throws Exception
	 */
	public String preSave()throws Exception{
		paperList=paperDao.getPapers();
		if(StringUtil.isNotEmpty(questionId)){
			question=questionDao.getQuestion(questionId);
			title="修改试题信息";
		}else{
			title="添加试题信息";
		}
		mainPage="question/questionSave.jsp";
		return SUCCESS;
	}
	
	/**
	 * 删除试题
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception{
		question=questionDao.getQuestion(questionId);
		questionDao.deleteQuestion(question);
		JSONObject resultJson=new JSONObject();
		resultJson.put("success",true);
		ResponseUtil.write(resultJson,ServletActionContext.getResponse());
		return null;
	}
	
	/**
	 * 保存试题
	 * @return
	 * @throws Exception
	 */
	public String saveQuestion()throws Exception{
		if(StringUtil.isNotEmpty(questionId)){
			question.setId(Integer.parseInt(questionId));
		}
		questionDao.saveQuestion(question);
		return "save";
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}

}
