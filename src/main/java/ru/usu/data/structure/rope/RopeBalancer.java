package ru.usu.data.structure.rope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author astarovoyt
 *
 */
class RopeBalancer
{
    private static final RopeNodeFactory factory = new RopeNodeFactory();
    private static final short MAX_ROPE_DEPTH = 96;
    private final Rope rope;

    RopeBalancer(final Rope rope) {
        super();
        this.rope = rope;
    }
    
    public Rope  balanceIfNessesary()
    {
        if (RopeBalancer.MAX_ROPE_DEPTH < this.rope.getDeep())
        {
            this.balance();
        }
        
        return this.rope;
    }

    private void balance()
    {
        this.rope.root = RopeBalancer.merge(this.getLeafNodes());
    }
    

    private List<RopeNode> getLeafNodes()
    {
        final List<RopeNode> leafs = new ArrayList<>();
        RopeBalancer.putLeafs(this.rope.root, leafs);
        return leafs;
    }

    private static void putLeafs(final RopeNode node, final List<RopeNode> leafs)
    {
        if (null == node)
        {
            return;
        }

        if (node.isLeaf())
        {
            leafs.add(node);
            return;
        }

        RopeBalancer.putLeafs(node.left, leafs);
        RopeBalancer.putLeafs(node.right, leafs);
    }

    private static RopeNode merge(final List<RopeNode> leafNodes)
    {
        return RopeBalancer.merge(leafNodes, 0, leafNodes.size());
    }

    private static RopeNode merge(final List<RopeNode> leafNodes, final int start, final int end)
    {
        final int range = end - start;
        if (1 == range) {
            return leafNodes.get(start);
        } else if (2 == range) {
            return RopeNodeFactory.createParentNode(leafNodes.get(start), leafNodes.get(start + 1));
        }
        final int middle = start + (range / 2);
        return RopeNodeFactory.createParentNode(RopeBalancer.merge(leafNodes, start, middle), RopeBalancer.merge(leafNodes, middle, end));
    }

    @Override
    public String toString() {
        return "RopeBalancer{" +
                "rope=" + this.rope +
                '}';
    }
}
