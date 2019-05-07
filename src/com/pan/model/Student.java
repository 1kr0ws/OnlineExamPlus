package com.pan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="t_student")
public class Student {

	private String id; // 编号、准考证
	private String name; // 姓名
	private String password; // 密码
	private String sex; // 性别
	private String prefession; // 专业
	private String cardNo; // 身份证
	
	private String flag="2"; // 用户类型  1：管理员  2：考生
	
	@Id
	@Column(name = "id", unique = true, nullable = false,length=40)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "name",length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "password",length=20)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "sex",length=5)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name = "prefession",length=40)
	public String getPrefession() {
		return prefession;
	}
	public void setPrefession(String prefession) {
		this.prefession = prefession;
	}
	
	@Column(name = "cardNo",length=50)
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	@Transient
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
