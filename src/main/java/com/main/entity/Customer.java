package com.main.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="customer")
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Customer.class)
public class Customer implements Serializable{
	
	/**
	 * @author Amol Suryavanshi
	 */
	
	private static final long serialVersionUID = 5865843731536861674L;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long CustId;
	
	@NotBlank
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@NotBlank
	@Column(name="email_id")
	private String emailId;
	
	@Transient
	private String messageStatus;
	
	public Customer() {
		
	}
	
	public Customer(Long CustId, @NotBlank String firstName, String lastName, @NotBlank String emailId) {
		super();
		this.CustId = CustId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	//	this.messageStatus = messageStatus;
	}

	public Long getCustId() {
		return CustId;
	}

	public void setCustId(Long custId) {
		CustId = custId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CustId == null) ? 0 : CustId.hashCode());
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((messageStatus == null) ? 0 : messageStatus.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (CustId == null) {
			if (other.CustId != null)
				return false;
		} else if (!CustId.equals(other.CustId))
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (messageStatus == null) {
			if (other.messageStatus != null)
				return false;
		} else if (!messageStatus.equals(other.messageStatus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [CustId=" + CustId + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", messageStatus=" + messageStatus + "]";
	}

}