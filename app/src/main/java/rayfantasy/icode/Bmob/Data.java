package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;
import cn.bmob.v3.datatype.*;

public class Data extends BmobObject
{
	private String Title;
	private String Message;
	private User Author;
	private BmobRelation Likes;
	private Integer CommentSize;
	private BmobFile fj;
	
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
	
	public void setAuthor(User Author){
		this.Author=Author;
	}

	public User getAuthor(){
		return Author;
	}
	
	public void setLikes(BmobRelation Likes){
		this.Likes=Likes;
	}

	public BmobRelation getLikes(){
		return Likes;
	}
	
	public void setCommentSize(Integer CommentSize){
		this.CommentSize=CommentSize;
	}
	
	public Integer getCommentSize(){
		return CommentSize;
	}
	
	public BmobFile getFj(){
		return fj;
	}

	public void setFj(BmobFile fj){
		this.fj = fj;
	}
}
