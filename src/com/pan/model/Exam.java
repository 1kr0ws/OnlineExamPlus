package com.pan.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ���Ա�
 * @author Administrator
 *
 */
@Entity
@Table(name="t_exam")
public class Exam {

	private int id;  // ���Ա��
	private Student student; // ѧ��
	private Paper paper; // �Ծ�
	private int singleScore; // ��ѡ��÷�
	private int moreScore; // ��ѡ��÷�
	private int score;  // �ܵ÷�
	private Date examDate; // ����ʱ��
	
	@Id
	@GeneratedValue(generator="_native")
	@GenericGenerator(name="_native",strategy="native")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="studentId")
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	
	@ManyToOne
	@JoinColumn(name="paperId")
	public Paper getPaper() {
		return paper;
	}
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public int getSingleScore() {
		return singleScore;
	}
	public void setSingleScore(int singleScore) {
		this.singleScore = singleScore;
	}
	public int getMoreScore() {
		return moreScore;
	}
	public void setMoreScore(int moreScore) {
		this.moreScore = moreScore;
	}
	
	
}
