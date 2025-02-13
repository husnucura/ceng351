import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
    private ArrayList<CengBook> books;
    // TODO: Any extra attributes

    public CengTreeNodeLeaf(CengTreeNode parent)
    {
        super(parent);
        this.type=CengNodeType.Leaf;
        books=new ArrayList<CengBook>();

        // TODO: Extra initializations
    }

    // GUI Methods - Do not modify
    public int bookCount()
    {
        return books.size();
    }
    public Integer bookKeyAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return -1;
        } else {
            CengBook book = this.books.get(index);

            return book.getBookID();
        }
    }
    protected ArrayList<CengBook> getBooks(){
        return this.books;
    }
    //setter for books

    protected void setBooks(ArrayList<CengBook> books) {
        this.books = books;
    }


    // Extra Functions
}
