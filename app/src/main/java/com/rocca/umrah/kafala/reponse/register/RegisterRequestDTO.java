package com.rocca.umrah.kafala.reponse.register;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class RegisterRequestDTO implements Serializable {

	@SerializedName("regname")
	private String regname;

	@SerializedName("regcity")
	private String regcity;

	@SerializedName("regphone")
	private String regphone;

	@SerializedName("regpassword")
	private String regpassword;

	public void setRegname(String regname){
		this.regname = regname;
	}

	public String getRegname(){
		return regname;
	}

	public void setRegcity(String regcity){
		this.regcity = regcity;
	}

	public String getRegcity(){
		return regcity;
	}

	public void setRegphone(String regphone){
		this.regphone = regphone;
	}

	public String getRegphone(){
		return regphone;
	}

	public void setRegpassword(String regpassword){
		this.regpassword = regpassword;
	}

	public String getRegpassword(){
		return regpassword;
	}

	@Override
 	public String toString(){
		return 
			"RegisterRequestDTO{" + 
			"regname = '" + regname + '\'' + 
			",regcity = '" + regcity + '\'' + 
			",regphone = '" + regphone + '\'' + 
			",regpassword = '" + regpassword + '\'' + 
			"}";
		}
}