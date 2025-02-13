import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
    private ArrayList<Integer> keys;
    private ArrayList<CengTreeNode> children;

    public CengTreeNodeInternal(CengTreeNode parent)
    {
        super(parent);
        this.type=CengNodeType.Internal;
        keys=new ArrayList<Integer>();
        children=new ArrayList<CengTreeNode>();
        // TODO: Extra initializations, if necessary.
    }

    // GUI Methods - Do not modify
    public ArrayList<CengTreeNode> getAllChildren()
    {
        return this.children;
    }
    public Integer keyCount()
    {
        return this.keys.size();
    }
    public Integer keyAtIndex(Integer index)
    {
        if(index >= this.keyCount() || index < 0)
        {
            return -1;
        }
        else
        {
            return this.keys.get(index);
        }
    }
    // Setter for keys and children
    protected void setKeys(ArrayList<Integer> keys) {
        this.keys = keys;
    }
    protected void setChildren(ArrayList<CengTreeNode> children) {
        this.children = children;
    }
    protected int getChildIndex(CengTreeNode childNode) {
        for (int i = 0; i < getAllChildren().size(); i++) {
            if (getAllChildren().get(i) == childNode) {
                return i;
            }
        }
        return -1;
    }

    protected ArrayList<Integer> getKeys() {
        return this.keys;
    }

    // Extra Functions
}
