package com.pan.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.pan.dao.PaperDao;
import com.pan.dao.QuestionDao;
import com.pan.model.Paper;
import com.pan.model.Question;
import com.pan.util.ResponseUtil;
import com.pan.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * �Ծ�Action��
 * @author Administrator
 *
 */
public class PaperAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PaperDao paperDao=new PaperDao();
	private QuestionDao questionDao=new QuestionDao();
	
	private String mainPage;
	private String paperId;
	
	private List<Paper> paperList=new ArrayList<Paper>();
	private List<Question> squestionList=new ArrayList<Question>();
	private List<Question> mquestionList=new ArrayList<Question>();
	
	private String title; // ����
	

	private Paper paper;
	
	public List<Paper> getPaperList() {
		return paperList;
	}

	public void setPaperList(List<Paper> paperList) {
		this.paperList = paperList;
	}
	
	

	public List<Question> getSquestionList() {
		return squestionList;
	}

	public void setSquestionList(List<Question> squestionList) {
		this.squestionList = squestionList;
	}

	public List<Question> getMquestionList() {
		return mquestionList;
	}

	public void setMquestionList(List<Question> mquestionList) {
		this.mquestionList = mquestionList;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	

	public Paper getPaper() {
		return paper;
	}

	

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * ��ȡ�����Ծ�
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception{
		paperList=paperDao.getPapers();
		mainPage="exam/selectPaper.jsp";
		return SUCCESS;
	}
	
	/**
	 * ��ȡ�����Ծ�����
	 * @return
	 * @throws Exception
	 */
	public String paperList()throws Exception{
		paperList=paperDao.getPapers();
		mainPage="paper/paperList.jsp";
		return SUCCESS;
	}
	
	/**
	 * ͨ��id��ȡ�Ծ�ʵ��
	 * @return
	 * @throws Exception
	 */
	public String getPaperById()throws Exception{
		paper=paperDao.getPaper(paperId);
		mainPage="paper/paperSave.jsp";
		return SUCCESS;
	}
	
	/**
	 * ����Ԥ����
	 * @return
	 * @throws Exception
	 */
	public String preSave()throws Exception{
		if(StringUtil.isNotEmpty(paperId)){
			paper=paperDao.getPaper(paperId);
			title="�޸��Ծ�";
		}else{
			title="����Ծ�";
		}
		mainPage="paper/paperSave.jsp";
		return SUCCESS;
	}
	
	/**
	 * �����Ծ�
	 * @return
	 * @throws Exception
	 */
	public String savePaper()throws Exception{
		if(StringUtil.isNotEmpty(paperId)){
			paper.setId(Integer.parseInt(paperId));
		}else{
			paper.setJoinDate(new Date());			
		}
		paperDao.savePaper(paper);
		return "save";
	}
	
	/**
	 * ɾ���Ծ�
	 * @return
	 * @throws Exception
	 */
	public String deletePaper()throws Exception{
		paper=paperDao.getPaper(paperId);
		JSONObject resultJson=new JSONObject();
		if(questionDao.existQuestionByPaperId(paperId)){
			resultJson.put("error","�Ծ���������Ŀ������ɾ��");
		}else{
			paperDao.paperDelete(paper);
			resultJson.put("success",true);
		}
		ResponseUtil.write(resultJson,ServletActionContext.getResponse());
		return null;
	}
	
	/**
	 * ��ȡָ���Ծ�
	 * @return
	 * @throws Exception
	 */
	public String getDetailPaper()throws Exception{
		paper=paperDao.getPaper(paperId);
		Set<Question> questionList=paper.getQuestions();
		Iterator<Question> it=questionList.iterator();
		while(it.hasNext()){
			Question q=it.next();
			if("1".equals(q.getType())){
				squestionList.add(q);
			}else{
				mquestionList.add(q);
			}
		}
		squestionList=this.getRandomQuestion(squestionList, 3);
		mquestionList=this.getRandomQuestion(mquestionList, 2);
		mainPage="exam/paper.jsp";
		return SUCCESS;
	}
	
	/**
	 * ��ȡ�������
	 * @param questionList
	 * @param num
	 * @return
	 */
	private List<Question> getRandomQuestion(List<Question> questionList,int num){
		List<Question> resultList=new ArrayList<Question>();
		Random random=new Random();
		if(num>0){
			for(int i=1;i<=num;i++){
				int n=random.nextInt(questionList.size());
				Question q=questionList.get(n);
				if(resultList.contains(q)){
					i--;
				}else{
					resultList.add(questionList.get(n));					
				}
			}
		}
		return resultList;
	}

}
