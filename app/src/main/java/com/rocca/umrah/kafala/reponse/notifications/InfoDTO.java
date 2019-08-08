package com.rocca.umrah.kafala.reponse.notifications;

import java.io.Serializable;

public class InfoDTO implements Serializable {
	private String id;
	private String text;
	private String date;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	@Override
 	public String toString(){
		return 
			"InfoDTO{" + 
			"id = '" + id + '\'' + 
			",text = '" + text + '\'' + 
			",date = '" + date + '\'' + 
			"}";
		}
}