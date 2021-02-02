package io.userinterface.user;

import io.userinterface.creditcard.CreditCard;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class User {
	private String name;
	private String address;
	private String contactInfo;
	private String birthdate;
	private CreditCard creditCard;
	private long driverLicence;

	public User() {}

	public User(String n, String add, String ci, String bd, long dl, CreditCard cc) {
		name = n;
		address = add;
		contactInfo = ci;
		birthdate = bd;
		driverLicence = dl;
		creditCard = cc;
	}
	public String getName() {
		return name;
	}
	public String getAddress() { return address; }
	public String getContactInfo() {
		return contactInfo;
	}
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public long getDriverLicence() {
		return driverLicence;
	}

	private SplitDate splitDate = (s, n) -> Integer.parseInt(s.split("/")[n]);;

	public String getAge() {
		LocalDate now = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int sub = splitDate.fun(birthdate, 1) > now.getMonthValue() ? 1 : 0;

		return String.valueOf(now.getYear() - splitDate.fun(birthdate, 2) - sub);
	}

	public String getLocation() {
		return address.split(",")[0].replaceAll(" ", "-");
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	public void setDriverLicence(long driverLicence) {
		this.driverLicence = driverLicence;
	}

	public void print() {
		System.out.print(name + ", " + address + ", " + contactInfo + ", " + birthdate + ", " + driverLicence);
		System.out.println();
	}
}

interface SplitDate {
	int fun(String s, int n);
}