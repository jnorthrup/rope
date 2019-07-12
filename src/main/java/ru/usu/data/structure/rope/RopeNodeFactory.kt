package ru.usu.data.structure.rope

object RopeNodeFactory {
    fun createNode(): RopeNode {
        return RopeNode()
    }

    /**
     * @return leaf node with string as value
     */
    fun createLeafNode(str: String): RopeNode {
        val node = createNode()

        node.influence = str.length
        node.value = str

        return node
    }

    /**
     * @return rope node childs left and right
     */
    fun createParentNode(left: RopeNode?, right: RopeNode?): RopeNode {
        val node = createNode()

        node.influence = RopeHelper.getInfluence(left) + RopeHelper.getInfluence(right)
        node.left = left
        node.right = right
        node.deep = RopeHelper.getIncDeep(node)
        return node
    }
}
