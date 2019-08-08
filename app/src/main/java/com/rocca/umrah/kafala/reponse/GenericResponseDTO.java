package com.rocca.umrah.kafala.reponse;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class GenericResponseDTO implements Serializable {

	@SerializedName("success")
	private String success;

	@SerializedName("data")
	private String data;

	public void setSuccess(String success){
		this.success = success;
	}

	public String getSuccess(){
		return success;
	}

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"GenericResponseDTO{" +
			"success = '" + success + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}