package com.rocca.umrah.kafala.reponse.register;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class InfoDTO implements Serializable {

	@SerializedName("id")
	private String id;

	@SerializedName("name")
	private String name;

	@SerializedName("city")
	private String city;

	@SerializedName("phone")
	private String phone;

	@SerializedName("password")
	private String password;

	@SerializedName("date")
	private String date;

	@SerializedName("activity")
	private String activity;

	@SerializedName("activecode")
	private String activecode;

	@SerializedName("noti_last_seen")
	private String notiLastSeen;

	@SerializedName("noti_delete")
	private String notiDelete;

	@SerializedName("agree")
	private String agree;

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

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

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

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setActivity(String activity){
		this.activity = activity;
	}

	public String getActivity(){
		return activity;
	}

	public void setActivecode(String activecode){
		this.activecode = activecode;
	}

	public String getActivecode(){
		return activecode;
	}

	public void setNotiLastSeen(String notiLastSeen){
		this.notiLastSeen = notiLastSeen;
	}

	public String getNotiLastSeen(){
		return notiLastSeen;
	}

	public void setNotiDelete(String notiDelete){
		this.notiDelete = notiDelete;
	}

	public String getNotiDelete(){
		return notiDelete;
	}

	public void setAgree(String agree){
		this.agree = agree;
	}

	public String getAgree(){
		return agree;
	}

	@Override
 	public String toString(){
		return 
			"InfoDTO{" + 
			"id = '" + id + '\'' + 
			",name = '" + name + '\'' + 
			",city = '" + city + '\'' + 
			",phone = '" + phone + '\'' + 
			",password = '" + password + '\'' + 
			",date = '" + date + '\'' + 
			",activity = '" + activity + '\'' + 
			",activecode = '" + activecode + '\'' + 
			",noti_last_seen = '" + notiLastSeen + '\'' + 
			",noti_delete = '" + notiDelete + '\'' + 
			",agree = '" + agree + '\'' + 
			"}";
		}
}