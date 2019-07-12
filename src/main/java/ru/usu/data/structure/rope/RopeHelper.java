package ru.usu.data.structure.rope;

import java.io.PrintStream;

final class RopeHelper
{
// --Commented out by Inspection START (2019-07-12 14:15):
//    private static final long[] FIBONACCI = { 0l, 1l, 1l, 2l, 3l, 5l, 8l, 13l, 21l, 34l, 55l, 89l, 144l, 233l, 377l,
//            610l, 987l, 1597l, 2584l, 4181l, 6765l, 10946l, 17711l, 28657l, 46368l, 75025l, 121393l, 196418l, 317811l,
//            514229l, 832040l, 1346269l, 2178309l, 3524578l, 5702887l, 9227465l, 14930352l, 24157817l, 39088169l,
//            63245986l, 102334155l, 165580141l, 267914296l, 433494437l, 701408733l, 1134903170l, 1836311903l,
//            2971215073l, 4807526976l, 7778742049l, 12586269025l, 20365011074l, 32951280099l, 53316291173l,
//            86267571272l, 139583862445l, 225851433717l, 365435296162l, 591286729879l, 956722026041l, 1548008755920l,
//            2504730781961l, 4052739537881l, 6557470319842l, 10610209857723l, 17167680177565l, 27777890035288l,
//            44945570212853l, 72723460248141l, 117669030460994l, 190392490709135l, 308061521170129l, 498454011879264l,
//            806515533049393l, 1304969544928657l, 2111485077978050l, 3416454622906707l, 5527939700884757l,
//            8944394323791464l, 14472334024676221l, 23416728348467685l, 37889062373143906l, 61305790721611591l,
//            99194853094755497l, 160500643816367088l, 259695496911122585l, 420196140727489673l, 679891637638612258l,
//            1100087778366101931l, 1779979416004714189l, 2880067194370816120l, 4660046610375530309l,
//            7540113804746346429l };
// --Commented out by Inspection STOP (2019-07-12 14:15)

    private static final String SPACES = "                        "
            + "                                                " + "                                                "
            + "                                                " + "                                ";

    private static final int MIN_LENGTH = 17;

    private static final RopeNodeFactory factory = new RopeNodeFactory();

    public static Rope concatenate(final Rope left, final Rope right)
    {
        if (null == left || null == right)
        {
            return null == left ? right : left;
        }

        checkArgs(left, right);

        if (MIN_LENGTH > left.length() + right.length())
        {
            return balanceIfNessesary(new Rope(left.toString() + right));
        }

        if (left.isFlat() && !right.isFlat())
        {
            RopeNode rootNode = right.root;
            if (MIN_LENGTH > left.length() + rootNode.left.influence)
            {
                RopeNode node = RopeNodeFactory.createParentNode(RopeNodeFactory.createLeafNode(left + rootNode.left.value),
                        rootNode.right);
                return balanceIfNessesary(new Rope(node));
            }
        }

        if (right.isFlat() && !left.isFlat())
        {
            RopeNode rootNode = left.root;
            if (MIN_LENGTH > rootNode.right.influence + right.length())
            {
                RopeNode node = RopeNodeFactory.createParentNode(rootNode.left,
                        RopeNodeFactory.createLeafNode(rootNode.right.value + right));
                return balanceIfNessesary(new Rope(node));
            }
        }

        return balanceIfNessesary(new Rope(RopeNodeFactory.createParentNode(left.root, right.root)));
    }

    private static Rope balanceIfNessesary(Rope result)
    {
        return new RopeBalancer(result).balanceIfNessesary();
    }

    private static void checkArgs(final Rope left, final Rope right)
    {
        if (Integer.MAX_VALUE < (long) left.length() + right.length())
        {
            throw new IllegalArgumentException("Max rope length can be " + Integer.MAX_VALUE);
        }
    }

// --Commented out by Inspection START (2019-07-12 14:06):
//    public static boolean isBalanced(final Rope r)
//    {
//        final int depth = r.getDeep();
//        if (depth >= FIBONACCI.length - 2)
//            return false;
//        return (FIBONACCI[depth + 2] <= r.length());
//    }
// --Commented out by Inspection STOP (2019-07-12 14:06)

    public static void normalize(RopeNode node)
    {
        if (null == node || node.isLeaf())
        {
            return;
        }

        if (node.isHalf())
        {
            RopeNode child = node.getHalf();
            normalize(child);
            copyNodePropeties(node, child);
        }
        else
        {
            normalize(node.left);
            normalize(node.right);
            node.deep = getIncDeep(node);
        }
        
        if (null != node.right && node.right.isEmpty())
        {
            node.right = null;
        }
        
        if (null != node.left && node.left.isEmpty())
        {
            node.left = null;
        }
    }

    private static void copyNodePropeties(RopeNode dest, RopeNode source)
    {
        dest.left = source.left;
        dest.right = source.right;
        dest.value = source.value;
        dest.deep = source.deep;
    }

    public static int getInfluence(RopeNode node)
    {
        return null == node ? 0 : node.influence;
    }

    private static int getDeep(RopeNode node)
    {
        return null == node ? 0 : node.deep;
    }

    public static int getIncDeep(RopeNode node)
    {
        return Math.max(RopeHelper.getDeep(node.left), RopeHelper.getDeep(node.right)) + 1;
    }

    public static void printRope(Rope rope)
    {
        printRopeNode(rope.root);
    }

    private static void printRopeNode(RopeNode rope)
    {
        visualize(rope, System.out, 0);
    }

    private static void visualize(final RopeNode node, final PrintStream out, final int depth)
    {
        if (null == node)
        {
            return;
        }

        if (0 == node.deep)
        {
            out.print(SPACES.substring(0, depth * 2));
            out.println("\"" + node + "\"");

            return;
        }

        out.print(SPACES.substring(0, depth * 2));
        out.println("concat[left]");
        visualize(node.left, out, depth + 1);
        out.print(SPACES.substring(0, depth * 2));
        out.println("concat[right]");
        visualize(node.right, out, depth + 1);
    }
}
