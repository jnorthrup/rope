package ru.usu.data.structure.rope;
public
class RopeNodeFactory
{
    public static RopeNode createNode()
    {
        return new RopeNode();
    }

    /**
     * @return leaf node with string as value
     */
    public static RopeNode createLeafNode(String str)
    {
        RopeNode node = createNode();

        node.influence = str.length();
        node.value = str;

        return node;
    }

    /**
     * @return rope node childs left and right
     */
    public static RopeNode createParentNode(RopeNode left, RopeNode right)
    {
        RopeNode node = createNode();

        node.influence = RopeHelper.getInfluence(left) + RopeHelper.getInfluence(right);
        node.left = left;
        node.right = right;
        node.deep = RopeHelper.getIncDeep(node);
        return node;
    }
}
