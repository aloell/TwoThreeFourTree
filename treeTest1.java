//package TwoThreeFourTree;

public class treeTest1 {
	public static void main(String[] args)
	{
		TwoThreeFourTree tree=new TwoThreeFourTree();
		tree.insert(1);
		tree.insert(6);
		tree.insert(17);
		tree.insert(18);
		tree.insert(2);
		tree.insert(3);
		tree.insert(5);
		tree.insert(19);
		tree.levelPrint();
		tree.insert(20);
		tree.insert(21);
		tree.insert(10);
		tree.insert(12);
		tree.levelPrint();
		tree.insert(14);
		tree.levelPrint();
		tree.insert(8);
		tree.insert(13);
		tree.insert(9);
		tree.insert(16);
		tree.insert(22);
		tree.insert(26);
		tree.insert(24);
		tree.levelPrint();
		tree.insert(4);
		tree.insert(7);
		tree.insert(11);
		tree.levelPrint();
		tree.delete(12);
		tree.delete(16);
		tree.levelPrint();
		tree.find(19,tree.root);
		tree.find(14,tree.root);
		tree.insert(23);
		tree.insert(25);
		tree.levelPrint();
		tree.delete(20);
		tree.levelPrint();
		tree.find(3,tree.root);
		tree.find(10,tree.root);
		tree.find(12,tree.root);
		System.out.println("total number of node access for insertion: "+tree.nodeAccessI+" average number:"+(double)tree.nodeAccessI/25);
		System.out.println("total number of overflow: "+tree.overflow+" average number:"+(double)tree.overflow/25);
		System.out.println("total number of split: "+tree.overflow+" average number:"+(double)tree.overflow/25);
		System.out.println("total number of node access for deletion: "+tree.nodeAccessD+" average number:"+(double)tree.nodeAccessD/3);
		System.out.println("total number of underflow: "+tree.underflow+" average number:"+(double)tree.underflow/3);
		System.out.println("total number of merge: "+tree.merge+" average number:"+(double)tree.merge/3);
		System.out.println("total number of node access for lookup: "+tree.nodeAccessF+" average number:"+(double)tree.nodeAccessF/5);
		System.out.println("tree height: "+tree.height);
	}

}
