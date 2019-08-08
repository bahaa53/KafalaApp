package com.rocca.umrah.kafala.reponse;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class InfoDTO implements Serializable {

	@SerializedName("id")
	private String id;

	@SerializedName("name")
	private String name;

	@SerializedName("priority")
	private String priority;

	@SerializedName("status")
	private String status;

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

	public void setPriority(String priority){
		this.priority = priority;
	}

	public String getPriority(){
		return priority;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"InfoDTO{" + 
			"id = '" + id + '\'' + 
			",name = '" + name + '\'' + 
			",priority = '" + priority + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}