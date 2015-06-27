import java.io.Console;
public class MemoUI{
	public static Memo[] memos = new Memo[100];
	public static User[] user = new User[100];
	public static Console cons = System.console();
	public static void main(String[] args){

		int c = 0;
		do{
			System.out.println("-------MENU-------");
			System.out.println("1: Create User");
			System.out.println("2: Create Memo");
			System.out.println("3: Read Memo");
			System.out.println("0: Exit"); 
			c = Terminal.askInt("Your Choice: ");

			switch(c){
				case 1:
					if(createUser()) System.out.println("Success.");
					else System.out.println("Not possible to create user.");
					break;
				case 2:
					if(createMemo()) System.out.println("Success.");
					else System.out.println("Not possible to create memo.");
					break;
				case 3:
					readMemo();
					break;
				case 0:
			}
		}while(c!=0);
	}

/*Durchsucht das User-Array nach einem freien Platz und fügt dann an dieser Stelle einen neuen User ein.
  Um ein Passwort einzulesen, benutzt bitte nicht Terminal.askString(), sondern new String(cons.readPassword("Enter password: "))
  Gibt true zurück, wenn das Erstellen funktioniert hat und false, wenn nicht.*/

	public static boolean createUser(){
		for(int i=0; i<user.length; i++){
			if ( user[i] == null ) {
				String name = Terminal.askString("Enter username: ");
				String password = new String(cons.readPassword("Enter password: "));
				user[i] = new User(name, password);
				return true;
			}
		}
		
		return false;
	}

/*Durchsucht das User-Array nach einem User, der den übergebenen Namen trägt und gibt diesen zurück.
  Wird keiner gefunden, wird null zurückgegeben.*/

	public static User findUser(String name){
		for(int i=0; i<user.length; i++){
			if ( user[i]!=null && name.equals(user[i].getName()) ){
				return user[i];
			}
		}
		
		return null;
	}

/*Durchsucht das Memo-Array nach einem freien Platz und fügt dann an dieser Stelle ein neues Memo ein.
  Benötigt die Methode findUser um den zu einem Namen gehörigen User zu finden und dem Memo-Konstruktor zu übergeben.
  findUser kann null zurückgeben. In diesem Fall wird eine Exception ausgelöst, die behandelt werden muss.*/

	public static boolean createMemo(){	
		for(int i=0; i<memos.length; i++){
			if ( memos[i] == null ){
				User user = findUser( Terminal.askString("Enter username: ") );
				
				if ( user == null ){
					Terminal.println("Could not find requested user.");
					return false;
				}
				
				memos[i] = new Memo(Terminal.askString("Enter text for memo: "), user);
				return true;
			}
		}
		
		Terminal.println("No empty memo space found.");
		return false;
	}

/*Gibt zunächst alle bereits erstellten Memos mit einer Nummer und dem User-Namen aus.
  Der Benutzer kann sich dann über die Nummer ein Memo aussuchen.
  Dieses wird ausgegeben, falls der Benutzer das richtige Passwort eingibt.
  Um ein Passwort einzulesen, benutzt bitte nicht Terminal.askString(), sondern new String(cons.readPassword("Enter password: "))
  Möglicherweise trifft der Benutzer eine ungültige Wahl.
  In diesem Fall wird eine Exception ausgelöst, die behandelt werden muss.*/

	public static void readMemo(){
		Terminal.println("Memo list (index & username):");
		
		for(int i=0; i<memos.length; i++){
			if( memos[i] != null ){
				Terminal.println(i + "\t" + memos[i].getUser().getName());
			}
		}
		
		int i = Terminal.askInt("Index of memo you want to read: ");
		String password = new String(cons.readPassword("Enter password: "));
		
		try{
			Terminal.println( "Memo content:\n" + memos[i].getText(password) );
		} catch(NullPointerException e1){
			Terminal.println("Could not read memo: Index does not exist yet!");
		} catch(ArrayIndexOutOfBoundsException e2){
			Terminal.println("Could not read memo: Index out of bounds!");
		}
	}
}
