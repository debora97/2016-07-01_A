package it.polito.tdp.formulaone.model;

import java.time.LocalDate;

public class Driver {

	private int driverId;
	private String driverRef;
	private int number;
	private String code;
	private String forename;
	private String surname;
	private LocalDate dob;
	private String nationality;
	private String url;

	public Driver(int driverId, String forename, String surname) {
	
		this.driverId = driverId;
		
		this.forename = forename;
		this.surname = surname;
	
	}

	@Override
	public String toString() {
		return String.format("Driver [driverId=%s, forename=%s, surname=%s]\n", driverId, forename, surname);
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public String getDriverRef() {
		return driverRef;
	}

	public void setDriverRef(String driverRef) {
		this.driverRef = driverRef;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
