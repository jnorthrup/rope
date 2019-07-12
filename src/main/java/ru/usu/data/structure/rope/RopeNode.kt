package ru.usu.data.structure.rope

/**
 * RopeNode is a node of the Rope's tree
 *
 * @author astarovoyt
 */
class RopeNode : Cloneable {
    var left: RopeNode? = null
    var right: RopeNode? = null
    var value: String? = null

    /**
     * length
     */
    internal var influence: Int = 0

    /**
     * length of max chain of childs
     */
    var deep: Int = 0

    /**
     * @return is node a leaf?
     */
    val isLeaf: Boolean
        get() = null == left && null == right

    /**
     * @return has node one child only?
     */
    val isHalf: Boolean
        get() = (null == left) xor (null == right)

    /**
     * @return not empty child
     * @throw IllegalStateException if node is not half
     */
    val half: RopeNode?
        get() =
            when {
                !isHalf -> throw IllegalStateException()
                else -> left ?: right
            }


    /**
     * @return is node empty leaf?
     */
    val isEmpty: Boolean
        get() = isLeaf && null == value

    /**
     * Recursive cloning of the node
     *
     */
    public override fun clone(): RopeNode {
        val newRope = RopeNode()
        newRope.influence = influence
        newRope.deep = deep
        newRope.value = value
        newRope.left = left?.let(RopeNode::clone)
        newRope.right = right?.let(RopeNode::clone)
        return newRope
    }

    override fun toString(): String {
        return "$value deep: $deep"
    }
}