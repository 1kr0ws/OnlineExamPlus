package com.pan.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.pan.dao.ExamDao;
import com.pan.dao.QuestionDao;
import com.pan.model.Exam;
import com.pan.model.PageBean;
import com.pan.model.Question;
import com.pan.util.PageUtil;
import com.pan.util.PropertiesUtil;
import com.pan.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ����Action��
 * @author Administrator
 *
 */
public class ExamAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ExamDao examDao=new ExamDao();
	private QuestionDao questionDao=new QuestionDao();
	
	private HttpServletRequest request;
	
	private String mainPage;
	
	private Exam exam;
	private Exam s_exam;
	
	private List<Exam> examList;
	
	private String page;
	private int total;
	private String pageCode;

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
	
	
	
	public List<Exam> getExamList() {
		return examList;
	}

	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}
	
	
	

	public Exam getS_exam() {
		return s_exam;
	}

	public void setS_exam(Exam s_exam) {
		this.s_exam = s_exam;
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

	/**
	 * ����/��ӿ��Գɼ�
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception{
		Map<String, String[]> keyMap = new HashMap<String, String[]>();
        keyMap = request.getParameterMap();
        Iterator<Entry<String,String[]>> it2 = keyMap.entrySet().iterator();
        int totalScore=0;
        int singleScore=0;
        int moreScore=0;
        while (it2.hasNext()) {
            Entry<String, String[]> entry = it2.next();  
            String keyStr=entry.getKey();
            String values[]=entry.getValue();
            String key;
            String value="";
            if(keyStr.equals("exam.student.id")||keyStr.equals("exam.paper.id")){
            	continue;
            }
            if(keyStr.split("-")[1].equals("r")){  // ��ѡ
            	key=keyStr.split("-")[2];
            	value=values[0];
            	singleScore+=this.calScore(key, value, "1");
            }else{  // ��ѡ
            	key=keyStr.split("-")[2];
            	for(String s:values){
            		value+=s+",";
            	}
            	value=value.substring(0,value.length()-1);
            	moreScore+=this.calScore(key, value, "2");
            }
        }
        totalScore=singleScore+moreScore;
        exam.setSingleScore(singleScore);
        exam.setMoreScore(moreScore);
        exam.setScore(totalScore);
        exam.setExamDate(new Date());
        examDao.saveExam(exam);
		mainPage="exam/examResult.jsp";
		return SUCCESS;
	}
	
	/**
	 * ����ÿ����Ŀ�ĵ÷�
	 * @param questionId
	 * @param userAnswer
	 * @return
	 */
	private int calScore(String questionId,String userAnswer,String type)throws Exception{
		Question question=questionDao.getQuestion(questionId);
		if(userAnswer.equals(question.getAnswer())){
			if("1".equals(type)){
				return 20;
			}else{
				return 30;
			}
		}else{
			return 0;
		}
	}
	
	/**
	 * ��ȡ���Գɼ�
	 * @return
	 * @throws Exception
	 */
	public String getExams()throws Exception{
		examList=examDao.getExams(s_exam,null);
		mainPage="exam/myExam.jsp";
		return SUCCESS;
	}
	
	/**
	 * ��ȡ���п��Գɼ�
	 * @return
	 * @throws Exception
	 */
	public String examList()throws Exception{
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		if(s_exam!=null){
			session.setAttribute("s_exam", s_exam);
		}else{
			Object o=session.getAttribute("s_exam");
			if(o!=null){
				s_exam=(Exam)o;
			}else{
				s_exam=new Exam();
			}
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		examList=examDao.getExams(s_exam,pageBean);
		total=examDao.examCount(s_exam);
		pageCode=PageUtil.genPagation(request.getContextPath()+"/exam!examList",total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		mainPage="exam/examList.jsp";
		return SUCCESS;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
}
