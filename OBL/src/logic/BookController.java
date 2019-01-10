package logic;

import java.util.ArrayList;

public class BookController  {


	public static void searchBook(String searchPick,String data)
	{
		ArrayList<String> searchBook = new ArrayList<>();
		searchBook.add("Search book");
		searchBook.add(searchPick);
		searchBook.add(data);
		Main.client.handleMessageFromClientUI(searchBook);
	}

}
