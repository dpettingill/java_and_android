package spell;

public class Node implements INode {
    public Node() {}
    private Node children[] = new Node[26];
    private int value = 0;

    /**
     * Returns the frequency count for the word represented by the node.
     *
     * @return the frequency count for the word represented by the node.
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Increments the frequency count for the word represented by the node.
     */
    public void incrementValue()
    {
        value++;
    }

    /**
     * Returns the child nodes of this node.
     *
     * @return the child nodes.
     */
    public Node[] getChildren()
    {
        return children;
    }
}
