package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;
import java.io.*;

public class User extends BmobUser
{

	private String About;
	private Integer Head_Color;
	private File Head;
	
	
	public void setAbout(String About){
		this.About=About;
	}
	
	
	public String getAbout(){
		return About;
	}
	
	public void setHead_Color(Integer Head_Color){
		this.Head_Color=Head_Color;
	}


	public Integer getHead_Color(){
		return Head_Color;
	}
}
