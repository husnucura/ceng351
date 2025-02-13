import java.util.ArrayList;

public class CengTree
{
    public CengTreeNode root;
    // Any extra attributes...

    public CengTree(Integer order)
    {
        CengTreeNode.order = order;
        // TODO: Initialize the class
        root = new CengTreeNodeLeaf(null);

    }

    public void addBook(CengBook book) {
        CengTreeNodeLeaf leafNode = getLeaf(root, book.getBookID());
        ArrayList<CengBook> books = leafNode.getBooks();

        int index = 0;
        while (index < books.size() && books.get(index).getBookID() < book.getBookID()) {
            index++;
        }

        if (index == books.size() || books.get(index).getBookID() != book.getBookID()) {
            books.add(index, book);

            if (books.size() > 2 * CengTreeNode.order) {
                int ind = books.size() / 2;

                CengTreeNodeLeaf newLeafNode = new CengTreeNodeLeaf(leafNode.getParent());
                newLeafNode.setBooks(new ArrayList<>(books.subList(ind, books.size())));
                books.subList(ind, books.size()).clear();

                CengTreeNodeInternal parentNode = (CengTreeNodeInternal) leafNode.getParent();

                if (parentNode == null) {
                    CengTreeNodeInternal tmp = new CengTreeNodeInternal(null);
                    tmp.setKeys(new ArrayList<>());
                    tmp.setChildren(new ArrayList<>());
                    tmp.getKeys().add(newLeafNode.getBooks().get(0).getBookID());
                    tmp.getAllChildren().add(leafNode);
                    tmp.getAllChildren().add(newLeafNode);

                    leafNode.setParent(tmp);
                    newLeafNode.setParent(tmp);

                    this.root = tmp;
                } else {
                    int childindex = parentNode.getChildIndex(leafNode);
                    parentNode.getAllChildren().add(childindex+1, newLeafNode);

                    parentNode.getKeys().add(childindex, newLeafNode.getBooks().get(0).getBookID());

                    if (parentNode.keyCount() > 2 * CengTreeNode.order) {
                        pushup(parentNode);
                    }
                }
            }
        }
    }







    //helper
    public void pushup(CengTreeNodeInternal cur) {
        if (cur.getParent() == null) {
            CengTreeNodeInternal newRoot = new CengTreeNodeInternal(null);

            int mid = cur.keyCount() / 2;

            newRoot.setKeys(new ArrayList<>());
            newRoot.getKeys().add(cur.keyAtIndex(mid));

            newRoot.setChildren(new ArrayList<>());

            CengTreeNodeInternal left = new CengTreeNodeInternal(newRoot);
            left.setKeys(new ArrayList<>(cur.getKeys().subList(0, mid)));
            left.setChildren(new ArrayList<>(cur.getAllChildren().subList(0, mid + 1)));

            CengTreeNodeInternal right = new CengTreeNodeInternal(newRoot);
            right.setKeys(new ArrayList<>(cur.getKeys().subList(mid + 1, cur.keyCount())));
            right.setChildren(new ArrayList<>(cur.getAllChildren().subList(mid + 1, cur.keyCount() + 1)));

            newRoot.getAllChildren().add(left);
            newRoot.getAllChildren().add(right);
            for(CengTreeNode child:right.getAllChildren())
                child.setParent(right);
            for(CengTreeNode child:left.getAllChildren())
                child.setParent(left);

            this.root = newRoot;
        } else {
            CengTreeNodeInternal parentNode = (CengTreeNodeInternal) cur.getParent();
            int childIndex = parentNode.getChildIndex(cur);
            int mid = cur.keyCount() / 2;

            CengTreeNodeInternal left = new CengTreeNodeInternal(parentNode);
            left.setKeys(new ArrayList<>(cur.getKeys().subList(0, mid)));
            left.setChildren(new ArrayList<>(cur.getAllChildren().subList(0, mid + 1)));

            CengTreeNodeInternal right = new CengTreeNodeInternal(parentNode);
            right.setKeys(new ArrayList<>(cur.getKeys().subList(mid + 1, cur.keyCount())));
            right.setChildren(new ArrayList<>(cur.getAllChildren().subList(mid + 1, cur.keyCount() + 1)));

            parentNode.getAllChildren().set(childIndex, left);
            parentNode.getAllChildren().add(childIndex + 1, right);

            parentNode.getKeys().add(childIndex, cur.keyAtIndex(mid));
            for(CengTreeNode child:right.getAllChildren())
                child.setParent(right);
            for(CengTreeNode child:left.getAllChildren())
                child.setParent(left);

            if (parentNode.keyCount() > 2 * CengTreeNode.order) {
                pushup(parentNode);
            }
        }
    }











    //helper to get the  leaf node to add
    private CengTreeNodeLeaf getLeaf(CengTreeNode currentNode, Integer bookID) {
        while (currentNode.getType() != CengNodeType.Leaf) {
            ArrayList<Integer> keys = ((CengTreeNodeInternal) currentNode).getKeys();
            ArrayList<CengTreeNode> children = ((CengTreeNodeInternal) currentNode).getAllChildren();

            int index = 0;

            while (index < keys.size() && bookID >= keys.get(index)) {
                index++;
            }

            currentNode = children.get(index);
        }

        return (CengTreeNodeLeaf) currentNode;
    }


    public ArrayList<CengTreeNode> searchBook(Integer bookID) {
        ArrayList<CengTreeNode> res = new ArrayList<>();
        // TODO: Search within the whole Tree, return visited nodes.
        // Return null if not found.
        int index = searchHelper(res, bookID, root);
        int level = 0;

        if (index != -1) {
            String indent=new String();
            for (int i = 0; i < level; i++) {
                indent=indent+"\t";
            }
            for (CengTreeNode node : res) {
                if (node.getType() == CengNodeType.Internal) {
                    System.out.println(indent+"<index>");
                    CengTreeNodeInternal internal = (CengTreeNodeInternal) node;
                    ArrayList<Integer> keys = internal.getKeys();

                    for (Integer key : keys) {
                        System.out.println(indent+key);
                    }

                    System.out.println(indent+"</index>");
                } else if (node.getType() == CengNodeType.Leaf) {
                    CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) node;
                    ArrayList<CengBook> books = leaf.getBooks();
                    CengBook book = books.get(index);


                    System.out.println(indent+"<record>" + book.fullName() + "</record>");
                }
                indent+="\t";
            }
        } else {
            System.out.println("Could not find " + bookID.toString() + ".");
        }

        return res;
    }




    public int searchHelper(ArrayList<CengTreeNode> res, Integer bookID, CengTreeNode node) {
        if (node == null) {
            return -1;
        }

        res.add(node);

        if (node.getType() == CengNodeType.Internal) {
            CengTreeNodeInternal internal = (CengTreeNodeInternal) node;
            ArrayList<Integer> keys = internal.getKeys();

            int index = 0;
            while (index < keys.size() && bookID >= keys.get(index)) {
                index++;
            }
            return searchHelper(res, bookID, internal.getAllChildren().get(index));
        } else if (node.getType() == CengNodeType.Leaf) {
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) node;
            ArrayList<CengBook> books = leaf.getBooks();

            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getBookID().equals(bookID)) {
                    return i;
                }
            }
        }

        return -1;
    }




    public void printTree()
    {
        // TODO: Print the whole tree to console
        if(root!=null){
            print(root,0);

        }
    }
    private void print(CengTreeNode node, int level) {
        if (node == null) {
            return;
        }
        /*if(node.getParent()!=null)
            level++;*/
        String indent=new String();
        for (int i = 0; i < level; i++) {
            indent=indent+"\t";
        }

        if (node.getType() == CengNodeType.Internal) {
            System.out.println(indent+"<index>");
            CengTreeNodeInternal internal = (CengTreeNodeInternal) node;
            ArrayList<Integer> keys = internal.getKeys();
            for (Integer key : keys) {


                System.out.println(indent+key);
            }

            System.out.println(indent+"</index>");
            for (CengTreeNode child : internal.getAllChildren()) {
                print(child, level+1);
            }


        } else if (node.getType() == CengNodeType.Leaf) {
            System.out.println(indent+"<data>");
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) node;
            ArrayList<CengBook> books = leaf.getBooks();
            for (CengBook book : books) {

                System.out.println(indent+"<record>" + book.fullName() + "</record>");
            }

            System.out.println(indent+"</data>");
        }
    }

    // Any extra functions...
}
