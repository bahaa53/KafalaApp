package com.rocca.umrah.kafala.reponse.myadvresponse;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class InfoDTO implements Serializable {

	@SerializedName("contact")
	private String contact;

	@SerializedName("info")
	private String info;

	@SerializedName("lastupdate")
	private String lastupdate;

	@SerializedName("city_name")
	private String cityName;

	@SerializedName("category_name")
	private String categoryName;

	@SerializedName("nationality_name")
	private String nationalityName;

	@SerializedName("postID")
	private String postID;

	@SerializedName("member_id")
	private String memberId;

	@SerializedName("member_name")
	private String memberName;

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getContact(){
		return contact;
	}

	public void setInfo(String info){
		this.info = info;
	}

	public String getInfo(){
		return info;
	}

	public void setLastupdate(String lastupdate){
		this.lastupdate = lastupdate;
	}

	public String getLastupdate(){
		return lastupdate;
	}

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setNationalityName(String nationalityName){
		this.nationalityName = nationalityName;
	}

	public String getNationalityName(){
		return nationalityName;
	}

	public void setPostID(String postID){
		this.postID = postID;
	}

	public String getPostID(){
		return postID;
	}

	public void setMemberId(String memberId){
		this.memberId = memberId;
	}

	public String getMemberId(){
		return memberId;
	}

	public void setMemberName(String memberName){
		this.memberName = memberName;
	}

	public String getMemberName(){
		return memberName;
	}

	@Override
 	public String toString(){
		return 
			"InfoDTO{" + 
			"contact = '" + contact + '\'' + 
			",info = '" + info + '\'' + 
			",lastupdate = '" + lastupdate + '\'' + 
			",city_name = '" + cityName + '\'' + 
			",category_name = '" + categoryName + '\'' + 
			",nationality_name = '" + nationalityName + '\'' + 
			",postID = '" + postID + '\'' + 
			",member_id = '" + memberId + '\'' + 
			",member_name = '" + memberName + '\'' + 
			"}";
		}
}