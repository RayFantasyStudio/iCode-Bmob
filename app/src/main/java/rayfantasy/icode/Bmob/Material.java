package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;

public class Material extends BmobObject
{
	private String Title="Title";
	private String Message="Message";
   	private String UserName="UserName";
	private String HeadColor="-10839344";

    public Material() {
        this.setTableName("Material");
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
}
