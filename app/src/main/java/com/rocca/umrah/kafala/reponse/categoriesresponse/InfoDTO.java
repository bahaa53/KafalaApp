package com.rocca.umrah.kafala.reponse.categoriesresponse;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class InfoDTO implements Serializable {

	@SerializedName("id")
	private String id;

	@SerializedName("name")
	private String name;

	@SerializedName("status")
	private String status;

	@SerializedName("type")
	private String type;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"InfoDTO{" + 
			"id = '" + id + '\'' + 
			",name = '" + name + '\'' + 
			",status = '" + status + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}