package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;

public class Comment extends BmobObject
{
	private String content;
	private User user;
	private Data data;

	public void setData(Data data)
	{
		this.data=data;
	}
	
	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}
	
	public void setUser(User user){
		this.user=user;
	}
	public User getUser(){
		return user;
	}
	
	
}
