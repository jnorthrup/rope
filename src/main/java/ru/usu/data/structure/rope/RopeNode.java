package ru.usu.data.structure.rope;

/**
 * RopeNode is a node of the Rope's tree
 * 
 * @author astarovoyt
 *
 */
public class RopeNode implements Cloneable
{
    public RopeNode left;
    public RopeNode right;
    public String value;
    
    /**
     * length
     */
    int influence;
    
    /**
     * length of max chain of childs
     */
    public int deep;

    /**
     * @return is node a leaf?
     */
    public boolean isLeaf()
    {
        return null == left && null == right;
    }

    /**
     * @return has node one child only?
     */
    public boolean isHalf()
    {
        return (null == left) ^ (null == right);
    }

    /**
     * @return not empty child
     * @throw IllegalStateException if node is not half
     */
    public RopeNode getHalf()
    {
        if (!isHalf())
        {
            throw new IllegalStateException();
        }
        
        return null == left ? right : left;
    }

    /**
     * Recursive cloning of the node 
     * 
     */
    @Override
    public RopeNode clone() {
        RopeNode newRope = new RopeNode();
        newRope.influence = influence;
        newRope.deep = deep;
        newRope.value = value;
        
        if (null != left)
        {
            newRope.left = left.clone();
        }
        
        if (null != right)
        {
            newRope.right = right.clone();
        }
        
        return newRope;
    }
    
    /**
     * @return is node empty leaf?
     */
    public boolean isEmpty()
    {
        return isLeaf() && null == value;
    }

    @Override
    public String toString()
    {
        return value + " deep: " + deep;
    }
}