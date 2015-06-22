public class Memo{
	private String text;
	private User user;
	
	public Memo(String text, User user){
		this.text = text;
		this.user = user;
	}
	
	public User getUser(){
		return user;
	}
	
	public String getText(String password){
		if( password.equals(user.getPassword()) ){
			return text;
		} else {
			return "Wrong password!";
		}
	}
}
