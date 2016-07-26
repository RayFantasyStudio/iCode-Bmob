package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;

public class Material extends BmobObject
{
	private String Title;
	private String Message;
   	private String UserName;
	private String HeadColor;

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
