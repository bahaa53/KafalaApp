package com.rocca.umrah.kafala.reponse.adverismentresponse;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class SearchResultResponseDTO implements Serializable {

	@SerializedName("date")
	private String date;

	@SerializedName("contact")
	private String contact;

	@SerializedName("cname")
	private String cname;

	@SerializedName("nname")
	private String nname;

	@SerializedName("tname")
	private String tname;

	@SerializedName("id")
	private String id;

	@SerializedName("mname")
	private String mname;

	@SerializedName("lastupdate")
	private String lastupdate;

	@SerializedName("info")
	private String info;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getContact(){
		return contact;
	}

	public void setCname(String cname){
		this.cname = cname;
	}

	public String getCname(){
		return cname;
	}

	public void setNname(String nname){
		this.nname = nname;
	}

	public String getNname(){
		return nname;
	}

	public void setTname(String tname){
		this.tname = tname;
	}

	public String getTname(){
		return tname;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setMname(String mname){
		this.mname = mname;
	}

	public String getMname(){
		return mname;
	}

	public void setLastupdate(String lastupdate){
		this.lastupdate = lastupdate;
	}

	public String getLastupdate(){
		return lastupdate;
	}

	public void setInfo(String info){
		this.info = info;
	}

	public String getInfo(){
		return info;
	}

	@Override
 	public String toString(){
		return 
			"SearchResultResponseDTO{" + 
			"date = '" + date + '\'' + 
			",contact = '" + contact + '\'' + 
			",cname = '" + cname + '\'' + 
			",nname = '" + nname + '\'' + 
			",tname = '" + tname + '\'' + 
			",id = '" + id + '\'' + 
			",mname = '" + mname + '\'' + 
			",lastupdate = '" + lastupdate + '\'' + 
			",info = '" + info + '\'' + 
			"}";
		}
}