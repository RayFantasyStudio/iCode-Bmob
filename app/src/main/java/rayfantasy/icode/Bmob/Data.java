package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;

public class Data extends BmobObject
{
	private String Title;
	private String Message;
   	private String UserName;
	private String HeadColor;
	private String HeadUri;
	private String Email;
	private Integer HeadVersion;
	
    public Data() {
        this.setTableName("Data");
    }

    public String getTitle() {
        return Title;
    }

	public String getMessage(){
		return Message;
	}

	public void setMessage(String Message){
		this.Message=Message;
	}

	public void setTitle(String Title){
		this.Title=Title;
	}

	public void setUser(String User){
		this.UserName=User;
	}
	public String getUser(){
		return UserName;
	}
	
	public void setHeadColor(String HeadColor){
		this.HeadColor=HeadColor;
	}


	public String getHeadColor(){
		return HeadColor;
	}
	
	public void setHeadUri(String HeadUri){
		this.HeadUri=HeadUri;
	}
	
	public String getHeadUri(){
		return HeadUri;
	}
	
	public void setEmail(String Email){
		this.Email=Email;
	}

	public String getEmail(){
		return Email;
	}
	
	public void setHeadVersion(Integer HeadVersion){
		this.HeadVersion=HeadVersion;
	}

	public Integer getHeadVersion(){
		return HeadVersion;
	}
}
