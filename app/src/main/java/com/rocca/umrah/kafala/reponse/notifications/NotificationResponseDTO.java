package com.rocca.umrah.kafala.reponse.notifications;

import java.io.Serializable;

public class NotificationResponseDTO implements Serializable {
	private String success;
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
			"NotificationResponseDTO{" + 
			"success = '" + success + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}