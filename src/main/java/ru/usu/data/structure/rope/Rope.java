package ru.usu.data.structure.rope;


import java.util.Arrays;
import java.util.List;

/**
 * Rope implementation
 * 
 * @author astarovoyt
 * 
 */
public class Rope implements CharSequence {
    private final RopeNodeFactory factory = new RopeNodeFactory();

    RopeNode root;

    public Rope(String wrapped) {
        super();
        root = RopeNodeFactory.createLeafNode(wrapped);
    }

    public Rope(RopeNode node) {
        super();
        root = node;
    }

    @Override
    public int length()
    {
        return root.influence;
    }

    @Override
    public char charAt(int index)
    {
        if (index >= length())
        {
            throw new IndexOutOfBoundsException("max index can be " + (root.influence - 1));
        }

        return charAt(index, root);
    }

    private static char charAt(int index, RopeNode currentNode)
    {
        if (currentNode.isLeaf())
        {
            return currentNode.value.charAt(index);
        }

        return index < currentNode.left.influence ? charAt(index, currentNode.left) : charAt(index - currentNode.left.influence, currentNode.right);
    }

    @Override
    public Rope subSequence(int start, int end)
    {
        if (0 > start || end > length())
            throw new IllegalArgumentException("Illegal subsequence (" + start + "," + end + ")");

        if (0 == start)
        {
            return this.split(end).get(0);
        }
        if (end == length())
        {
            return this.split(start).get(1);
        }

        return this.split(start).get(1).split(end - start).get(0);
    }

    public List<Rope> split(int index)
    {
        return split(this, index);
    }

// --Commented out by Inspection START (2019-07-12 14:15):
//    public void append(String appendix)
//    {
//        root = ((Rope)append(new Rope(appendix))).root;
//    }
// --Commented out by Inspection STOP (2019-07-12 14:15)

    public Rope append(Rope rope)
    {
        if (rope instanceof Rope)
        {
            return RopeHelper.concatenate(this, rope);
        }
        return RopeHelper.concatenate(this, new Rope(rope.toString()));
    }

    private List<Rope> split(Rope rope, int index)
    {
        if (index >= rope.length())
        {
            throw new IndexOutOfBoundsException("max index can be " + (rope.length() - 1));
        }

        RopeNode leftSplitted = RopeNodeFactory.createNode();
        RopeNode rightSplitted = RopeNodeFactory.createNode();
        split(leftSplitted, rightSplitted, rope.root, index);
        RopeHelper.normalize(leftSplitted);
        RopeHelper.normalize(rightSplitted);

        return Arrays.asList(new Rope(leftSplitted), new Rope(rightSplitted));
    }

    private static void split(RopeNode leftParent, RopeNode rightParent, RopeNode parent, int index) {
        if (parent.isLeaf())
        {
            String parentValue = parent.value;

            if (0 != index)
            {
                leftParent.value = parentValue.substring(0, index);
                leftParent.influence = leftParent.value.length();
            }

            leftParent.deep = 0;

            if (parentValue.length() != index)
            {
                rightParent.value = parentValue.substring(index);
                rightParent.influence = rightParent.value.length();
            }

            rightParent.deep = 0;
            return;
        }

        leftParent.influence = index;
        rightParent.influence = parent.influence - index;

        if (index < parent.left.influence)
        {
            rightParent.right = parent.right;
            RopeNode leftChildOfRightParent = RopeNodeFactory.createNode();
            rightParent.left = leftChildOfRightParent;

            split(leftParent, leftChildOfRightParent, parent.left, index);
            rightParent.deep = RopeHelper.getIncDeep(rightParent);
        }
        else
        {
            leftParent.left = parent.left;
            RopeNode rightChildOfleftParent = RopeNodeFactory.createNode();
            leftParent.right = rightChildOfleftParent;

            split(rightChildOfleftParent, rightParent, parent.right, index - parent.left.influence);
            leftParent.deep = RopeHelper.getIncDeep(leftParent);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        toString(root, builder);
        return builder.toString();
    }

    private static void toString(RopeNode node, StringBuilder builder)
    {
        if (null == node)
        {
            return;
        }

        if (node.isLeaf() && !node.isEmpty())
        {
            builder.append(node.value);
            return;
        }

        toString(node.left, builder);
        toString(node.right, builder);
    }

    public int getDeep()
    {
        return root.deep;
    }

    public boolean isFlat()
    {
        return 0 == root.deep;
    }
}
