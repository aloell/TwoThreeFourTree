//package TwoThreeFourTree;

import java.util.*;

public class treeTest3 
{
	public static void main(String[] args)
	{
		int treeT=0;
		while(treeT<5)
		{
		System.out.println("raw data for tree: T"+treeT);
		System.out.println();
		System.out.println();
		int i=0;
		HashSet<Integer> pool=new HashSet();
		Random r=new Random();
		while(pool.size()!=1000)
		{
			pool.add(r.nextInt(2000));
		}
		Iterator<Integer> itr=pool.iterator();
		LinkedList<Integer> poolL=new LinkedList();
		TwoThreeFourTree tree=new TwoThreeFourTree();
		while(itr.hasNext())
		{
			i=itr.next();
			poolL.add(i);
		}
		Collections.shuffle(poolL);
		i=0;
		while(i<1000)
		{
			i++;
			tree.insert(poolL.poll());
		}
		System.out.println("before: total number of node access for insertion: "+tree.nodeAccessI);
		System.out.println("before: total number of overflow: "+tree.overflow);
		System.out.println("before: total number of split: "+tree.overflow);
		System.out.println("before: tree height: "+tree.height);
		System.out.println();
		pool.clear();
		while(pool.size()!=100)
		{
			pool.add(r.nextInt(2000));
		}
		itr=pool.iterator();
		poolL.clear();
		i=0;
		while(i<100)
		{
			i++;
			poolL.add(itr.next());
		}
		Collections.shuffle(poolL);
		int k=0;
		i=0;
		int j=0;
		int x=0;
		int y=0;
		System.out.println("lookup sequence as follows: ");
		for(i=0;i<100;i++)
		{
			k=poolL.poll();
			System.out.print(k+"  ");
			if(tree.find(k,tree.root))
			{
				x++;
				tree.delete(k);
			}
			else
			{
				y++;
				tree.insert(k);
			}
		}
		System.out.println();
		System.out.println("total number of insertions: "+y);
		System.out.println("total number of node access for insertion: "+tree.nodeAccessI);
		System.out.println("total number of overflow: "+tree.overflow);
		System.out.println("total number of split: "+tree.overflow);
		System.out.println("total number of deletions: "+x);
		System.out.println("total number of node access for deletion: "+tree.nodeAccessD);
		System.out.println("total number of underflow: "+tree.underflow);
		System.out.println("total number of merge: "+tree.merge);
		System.out.println("total number of node access for lookup: "+tree.nodeAccessF);
		System.out.println("tree height: "+tree.height);
		System.out.println();
		System.out.println();
		treeT++;
		}
	}
}
