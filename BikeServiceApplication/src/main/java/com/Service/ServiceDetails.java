package com.Service;

import org.springframework.stereotype.Controller;

@Controller
public class ServiceDetails {
	private int service_id;
	private String general_check;
	private String oil_service;
	private String water_wash;
	private String Total;
	private int user_count;
	private String Ailability;
	public int getService_id() {
		return service_id;
	}
	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	public String getGeneral_check() {
		return general_check;
	}
	public void setGeneral_check(String general_check) {
		this.general_check = general_check;
	}
	public String getOil_service() {
		return oil_service;
	}
	public void setOil_service(String oil_service) {
		this.oil_service = oil_service;
	}
	public String getWater_wash() {
		return water_wash;
	}
	public void setWater_wash(String water_wash) {
		this.water_wash = water_wash;
	}
	public String getTotal() {
		return Total;
	}
	public void setTotal(String total) {
		Total = total;
	}
	public int getUser_count() {
		return user_count;
	}
	public void setUser_count(int user_count) {
		this.user_count = user_count;
	}
	public String getAilability() {
		return Ailability;
	}
	public void setAilability(String ailability) {
		Ailability = ailability;
	}
}
