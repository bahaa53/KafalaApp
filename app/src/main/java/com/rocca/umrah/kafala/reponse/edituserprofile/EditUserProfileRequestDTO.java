package com.rocca.umrah.kafala.reponse.edituserprofile;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class EditUserProfileRequestDTO implements Serializable {

	@SerializedName("userId")
	private String userId;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("city")
	private String city;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	@Override
 	public String toString(){
		return 
			"EditUserProfileRequestDTO{" + 
			"userId = '" + userId + '\'' + 
			",phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			",city = '" + city + '\'' + 
			"}";
		}
}