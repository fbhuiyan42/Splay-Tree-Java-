package pkg1005108_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import pkg1005108_3.Main.Node;

public class Main<T extends Comparable>
{
    Node root;  
    class Node 
    {
        T value;                 
        Node left;
        Node right;  

        public Node(T x)
        {
            value = x;
        }
    }

    Node Left_rotation(Node root)
    {
        Node newroot = root.right;
        root.right = newroot.left;
        newroot.left = root;
        return newroot;
    }
    
    Node Right_rotation(Node root)
    {
        Node newroot = root.left;
        root.left = newroot.right;
        newroot.right = root;
        return newroot;
    }
    
    Node Splay(Node root, T value) 
    {
        if (root == null) return null;
        int check1,check2;
        check1 = value.compareTo(root.value);
        if (check1 < 0) 
        {
            if (root.left == null) return root;
            check2 = value.compareTo(root.left.value);
            if (check2 < 0) 
            {
                root.left.left = Splay(root.left.left, value);
                root = Right_rotation(root);
            }
            else if (check2 > 0) 
            {
                root.left.right = Splay(root.left.right, value);
                if (root.left.right != null)
                    root.left = Left_rotation(root.left);
            }
            if (root.left == null) return root;
            else return Right_rotation(root);
        }
        else if (check1 > 0) 
        { 
            if (root.right == null) 
            {
                return root;
            }
            check2 = value.compareTo(root.right.value);
            if (check2 < 0) 
            {
                root.right.left  = Splay(root.right.left, value);
                if (root.right.left != null)
                    root.right = Right_rotation(root.right);
            }
            else if (check2 > 0) 
            {
                root.right.right = Splay(root.right.right, value);
                root = Left_rotation(root);
            }
            
            if (root.right == null) return root;
            else return Left_rotation(root);
        }
        else return root;
    }
    
    int Search(T x)
    {
        Node temp= Splay(root,x);
        int check=0;
        if(x.compareTo(root.value)==0) check=1;
        root=temp;
        return check;
    }
    
    void Insert(T x) 
    {
        if (root == null) 
        {
            root = new Node(x);
            return;
        }
        root = Splay(root, x);
        int check = x.compareTo(root.value);
        
        if (check == 0) return ;
        else if (check < 0) 
        {
            Node newnode = new Node(x);
            newnode.right = root;
            newnode.left = root.left;
            root.left = null;
            root = newnode;
        }
        else if (check > 0) 
        {
            Node  newnode = new Node(x);
            newnode.left = root;
            newnode.right = root.right;
            root.right = null;
            root = newnode;
        }
    }
    
    void Delete(T x) 
    {
        if (root == null) return; 
        root = Splay(root, x);
        int check = x.compareTo(root.value);
        if (check == 0) 
        {
            if (root.left == null) 
            {
                root = root.right;
            } 
            else 
            {
                Node temp = root.right;
                root = root.left;
                Splay(root,x);
                root.right = temp;
            }
        }
    }
    
    public static void main(String[] args)
    {
       Scanner in = new Scanner(System.in);
       Main ST = new Main();
       int n=0;
       while(n!=6)
       {
            System.out.println("Enter 1 to Splay");
            System.out.println("Enter 2 to Search");
            System.out.println("Enter 3 to Insert");
            System.out.println("Enter 4 to Delete");
            System.out.println("Enter 5 to Print");
            System.out.println("Enter 6 to Exit");
            n = in.nextInt();
       
            if(n==1)
            {
                System.out.println("Enter value to Splay");
                n = in.nextInt();
                ST.root=ST.Splay(ST.root,n);
            }
            if(n==2)
            {
                System.out.println("Enter value to Search");
                n = in.nextInt();
                if(ST.Search(n)==1) System.out.println(n+" is found in the tree");
                else System.out.println(n+" is not in the tree");
            }
            if(n==3)
            {
                System.out.println("Enter value to Insert");
                n = in.nextInt();
                ST.Insert(n);
            }
            if(n==4)
            {
                System.out.println("Enter value to Delete");
                n = in.nextInt();
                ST.Delete(n);
            }
            if(n==5)
            {
                System.out.println("Splay Tree ");
                BTreePrinter.printNode(ST.root);
            }
       }
    }

}

class BTreePrinter {

    public static <T extends Comparable<?>> void printNode(Node root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.value);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(Node node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}

