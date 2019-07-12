package ru.usu.data.structure.rope


import java.util.Arrays

/**
 * Rope implementation
 *
 * @author astarovoyt
 */
class Rope : CharSequence {
    override val length: Int
        get() =root.influence//  TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun get(index: Int): Char {
        if (index >= length) {
            throw IndexOutOfBoundsException("max index can be " + (root.influence - 1))
        }

        return charAt(index, root) //    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val factory = RopeNodeFactory

    internal var root: RopeNode

    val deep: Int
        get() = root.deep

    val isFlat: Boolean
        get() = 0 == root.deep

    constructor(wrapped: String) : super() {
        root = RopeNodeFactory.createLeafNode(wrapped)
    }

    constructor(node: RopeNode) : super() {
        root = node
    }


    private fun charAt(index: Int, currentNode: RopeNode): Char {
        if (currentNode.isLeaf) {
            return currentNode.value!![index]
        }

        return if (index < currentNode.left!!.influence) charAt(index, currentNode.left!!) else charAt(index - currentNode.left!!.influence, currentNode.right!!)
    }

    override fun subSequence(start: Int, end: Int): Rope {
        if (0 > start || end > length)
            throw IllegalArgumentException("Illegal subsequence ($start,$end)")

        if (0 == start) {
            return this.split(end)[0]
        }
        return if (end == length) {
            this.split(start)[1]
        } else this.split(start)[1].split(end - start)[0]

    }

    fun split(index: Int): List<Rope> {
        return split(this, index)
    }

    // --Commented out by Inspection START (2019-07-12 14:15):
    //    public void append(String appendix)
    //    {
    //        root = ((Rope)append(new Rope(appendix))).root;
    //    }
    // --Commented out by Inspection STOP (2019-07-12 14:15)

    fun append(rope: Rope): Rope? {
        return if (rope is Rope) {
            RopeHelper.concatenate(this, rope)
        } else RopeHelper.concatenate(this, Rope(rope.toString()))
    }

    private fun split(rope: Rope, index: Int): List<Rope> {
        if (index >= rope.length) {
            throw IndexOutOfBoundsException("max index can be " + (rope.length - 1))
        }

        val leftSplitted = RopeNodeFactory.createNode()
        val rightSplitted = RopeNodeFactory.createNode()
        split(leftSplitted, rightSplitted, rope.root, index)
        RopeHelper.normalize(leftSplitted)
        RopeHelper.normalize(rightSplitted)

        return Arrays.asList(Rope(leftSplitted), Rope(rightSplitted))
    }

    private fun split(leftParent: RopeNode, rightParent: RopeNode, parent: RopeNode, index: Int) {
        if (parent.isLeaf) {
            val parentValue = parent.value

            if (0 != index) {
                leftParent.value = parentValue!!.substring(0, index)
                leftParent.influence = leftParent.value!!.length
            }

            leftParent.deep = 0

            if (parentValue!!.length != index) {
                rightParent.value = parentValue.substring(index)
                rightParent.influence = rightParent.value!!.length
            }

            rightParent.deep = 0
            return
        }

        leftParent.influence = index
        rightParent.influence = parent.influence - index

        if (index < parent.left!!.influence) {
            rightParent.right = parent.right
            val leftChildOfRightParent = RopeNodeFactory.createNode()
            rightParent.left = leftChildOfRightParent

            split(leftParent, leftChildOfRightParent, parent.left!!, index)
            rightParent.deep = RopeHelper.getIncDeep(rightParent)
        } else {
            leftParent.left = parent.left
            val rightChildOfleftParent = RopeNodeFactory.createNode()
            leftParent.right = rightChildOfleftParent

            split(rightChildOfleftParent, rightParent, parent.right!!, index - parent.left!!.influence)
            leftParent.deep = RopeHelper.getIncDeep(leftParent)
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        toString(root, builder)
        return builder.toString()
    }

    private fun toString(node: RopeNode?, builder: StringBuilder) {
        if (null == node) {
            return
        }

        if (node.isLeaf && !node.isEmpty) {
            builder.append(node.value)
            return
        }

        toString(node.left, builder)
        toString(node.right, builder)
    }
}
