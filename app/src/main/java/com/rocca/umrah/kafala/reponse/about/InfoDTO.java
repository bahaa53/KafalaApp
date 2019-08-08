package com.rocca.umrah.kafala.reponse.about;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class InfoDTO implements Serializable {

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("body")
	private String body;

	@SerializedName("time")
	private String time;

	@SerializedName("date")
	private String date;

	@SerializedName("showInMenu")
	private String showInMenu;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setShowInMenu(String showInMenu){
		this.showInMenu = showInMenu;
	}

	public String getShowInMenu(){
		return showInMenu;
	}

	@Override
 	public String toString(){
		return 
			"InfoDTO{" + 
			"id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",body = '" + body + '\'' + 
			",time = '" + time + '\'' + 
			",date = '" + date + '\'' + 
			",showInMenu = '" + showInMenu + '\'' + 
			"}";
		}
}