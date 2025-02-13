import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;



public class CengTreeParser
{
    public static ArrayList<CengBook> parseBooksFromFile(String filename)
    {
        ArrayList<CengBook> bookList = new ArrayList<CengBook>();

        // You need to parse the input file in order to use GUI tables.
        // TODO: Parse the input file, and convert them into CengBooks
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] args = line.split("\\|");

                if (args.length == 4) {
                    int bookID = Integer.parseInt(args[0].trim());
                    String bookTitle = args[1].trim();
                    String author = args[2].trim();
                    String genre = args[3].trim();

                    CengBook newBook = new CengBook(bookID, bookTitle, author, genre);
                    bookList.add(newBook);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookList;

    }

    public static void startParsingCommandLine() throws IOException
    {
        // TODO: Start listening and parsing command line -System.in-.
        // There are 4 commands:
        // 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
        // 2) add : Parse and create the book, and call CengBookRunner.addBook(newlyCreatedBook).
        // 3) search : Parse the bookID, and call CengBookRunner.searchBook(bookID).
        // 4) print : Print the whole tree, call CengBookRunner.printTree().

        // Commands (quit, add, search, print) are case-insensitive.
        Scanner s = new Scanner(System.in);

        while (true) {
            String input = s.nextLine();

            String[] args = input.trim().toLowerCase().split("\\|");

            if (args.length > 0) {
                String command = args[0].trim();

                switch (command) {
                    case "quit":
                        return;
                    case "add":
                        if (args.length == 5) {
                            try {
                                int bookID = Integer.parseInt(args[1].trim());
                                String bookTitle = args[2].trim();
                                String author = args[3].trim();
                                String genre = args[4].trim();

                                CengBook newBook = new CengBook(bookID, bookTitle, author, genre);
                                CengBookRunner.addBook(newBook);
                            }
                            catch(Exception e){
                                System.out.println("add|<bookID>|<bookTitle>|<author>|<genre>");
                            }
                        } else {
                            System.out.println("add|<bookID>|<bookTitle>|<author>|<genre>");
                        }
                        break;
                    case "search":
                        if (args.length == 2) {

                            try {
                                int searchKey = Integer.parseInt(args[1].trim());
                                CengBookRunner.searchBook(searchKey);
                            }
                            catch(Exception e){
                                System.out.println("search|<searchKey>");
                            }

                        } else {
                            System.out.println("search|<searchKey>");
                        }
                        break;
                    case "print":
                        CengBookRunner.printTree();
                        break;
                    default:
                        System.out.println("quit, add, search, print");
                }
            }
        }

    }
}
