package com.rocca.umrah.kafala.request;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class LoginRequestDTO implements Serializable {

	@SerializedName("phone")
	private String phone;

	@SerializedName("password")
	private String password;

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	@Override
 	public String toString(){
		return 
			"LoginRequestDTO{" + 
			"phone = '" + phone + '\'' + 
			",password = '" + password + '\'' + 
			"}";
		}
}