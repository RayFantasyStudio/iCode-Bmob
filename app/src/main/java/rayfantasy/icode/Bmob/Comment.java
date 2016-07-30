package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;

public class Comment extends BmobObject
{
	//评论内容
	private String content;
	//评论用户
	private User user;
	
	private Data data;

	public void setData(Data post)
	{
		this.data=post;
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
