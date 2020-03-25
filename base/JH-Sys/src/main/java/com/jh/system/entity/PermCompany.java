package com.jh.system.entity;

import com.jh.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 公司实体类
 * @version <1> 2018-01-23 djh： Created.
 */
public class PermCompany extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long companyId;
	private String companyName;
	private String companyAddress;
	private String companyLegal;
	private String companyLinkman;
	private String linkmanMobile;
	private String telephone;
	private Integer accountNum;
	private List<PermPerson> personList;

	// @Override
	// public Serializable getPk() {
	// return this.getCompanyId();
	// }

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyLegal() {
		return companyLegal;
	}

	public void setCompanyLegal(String companyLegal) {
		this.companyLegal = companyLegal;
	}

	public String getCompanyLinkman() {
		return companyLinkman;
	}

	public void setCompanyLinkman(String companyLinkman) {
		this.companyLinkman = companyLinkman;
	}

	public String getLinkmanMobile() {
		return linkmanMobile;
	}

	public void setLinkmanMobile(String linkmanMobile) {
		this.linkmanMobile = linkmanMobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(Integer accountNum) {
		this.accountNum = accountNum;
	}

	public List<PermPerson> getPersonList() {
		return personList;
	}

	public void setPersonList(List<PermPerson> personList) {
		this.personList = personList;
	}

	// @Override
	// public List<ResultMessage> validate() {
	// super.validate();
	//
	// if(StringUtils.isBlank(this.getCompanyName())) {
	// String msg = SpringUtil.getMessage("company_name");
	// addError(msg);
	// }
	//
	// if(StringUtils.isBlank(this.getCompanyAddress())) {
	// String msg = SpringUtil.getMessage("company_address");
	// addError(msg);
	// }
	//
	// if(StringUtils.isBlank(this.getCompanyLegal())) {
	// String msg = SpringUtil.getMessage("company_legal");
	// addError(msg);
	// }
	//
	// if(StringUtils.isBlank(this.getCompanyLinkman())) {
	// String msg = SpringUtil.getMessage("company_linkman");
	// addError(msg);
	// }
	//
	// if(StringUtils.isBlank(this.getLinkmanMobile())) {
	// String msg = SpringUtil.getMessage("linkman_mobile");
	// addError(msg);
	// }
	//
	// if(StringUtils.isBlank(this.getTelephone())) {
	// String msg = SpringUtil.getMessage("telephone");
	// addError(msg);
	// }
	//
	// if(this.getAccountNum() == null) {
	// String msg = SpringUtil.getMessage("account_num");
	// addError(msg);
	// }
	//
	// return super.getErrorList();
	// }
}