package com.rocca.umrah.kafala.reponse.about;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class AboutResponseDTO implements Serializable {

	@SerializedName("success")
	private String success;

	@SerializedName("data")
	private DataDTO data;

	public void setSuccess(String success){
		this.success = success;
	}

	public String getSuccess(){
		return success;
	}

	public void setData(DataDTO data){
		this.data = data;
	}

	public DataDTO getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"AboutResponseDTO{" + 
			"success = '" + success + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}