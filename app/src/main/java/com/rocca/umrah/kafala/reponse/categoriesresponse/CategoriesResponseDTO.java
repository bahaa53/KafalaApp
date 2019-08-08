package com.rocca.umrah.kafala.reponse.categoriesresponse;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class CategoriesResponseDTO implements Serializable {

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
			"CategoriesResponseDTO{" + 
			"success = '" + success + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}