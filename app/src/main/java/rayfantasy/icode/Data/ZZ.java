package rayfantasy.icode.Data;

public class ZZ
{
	public static final String GREEN = "//(.*)|/\\*(.|[\r\n])*?\\*/|=|==";
	public static final String RED = "true|false| [0-9]*$|\".+?\"|\\d+(\\.\\d+)?";
	public static final String BLUE = "class|import|extends|package|implements|switch|while|break|case|private|public|protected|void|super|Bundle|this|static|final|if|else|return|new|catch|try";
	public static final String OTHER= "\\;|\\(|\\)|\\{|\\}|R\\..+?\\..+?|([\t|\n| ][A-Z].+? )|String |int |boolean |float |double |char |long |Override ";
	public static final int COLOR_GREEN = 0xff007c1e;
	public static final int COLOR_RED = 0xffff0000;
	public static final int COLOR_BLUE = 0xff2c82c8;
	public static final int COLOR_OTHER = 0xff0096ff;
}
