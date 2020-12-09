package Main;

import Controllers.LoginHandler;

public class Main {
	public Main() {
		LoginHandler.getInstance().viewLogin();
	}

	public static void main(String[] args) {
		new Main();
	}

}
