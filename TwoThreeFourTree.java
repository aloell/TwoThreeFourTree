//package TwoThreeFourTree;
import java.util.Stack;
import java.util.LinkedList;
public class TwoThreeFourTree {
	treeNode root;
	int height=0;
	int overflow=0;
	//node access of insertion
	int nodeAccessI=0;
	//node access of lookup
	int nodeAccessF=0;
	//node access of deletion
	int nodeAccessD=0;
	int underflow=0;
	int merge=0;
	public TwoThreeFourTree()
	{
		root=new treeNode(false);
		root.child[0]=new treeNode(true);
		root.csize++;
		height++;
	}
	
	public boolean find(int target, treeNode t)
	{
		if(t.leaf==true)
		{
			System.out.println("The key: "+target+" is not found.");
			nodeAccessF++;
			return false;
		}
		int i=0;
		for(i=0;i<t.ksize;i++)
		{
			if(target==t.key[i])
			{
				System.out.println("The key: "+target+" is found.");
				nodeAccessF++;
				return true;
			}
			if(target<t.key[i])
			{
				nodeAccessF++;
				return find(target,t.child[i]);
			}
			if(i==(t.ksize-1))
			{
				nodeAccessF++;
				return find(target,t.child[i+1]);
			}
		}
		//when root is empty
		System.out.println("The key: "+target+" is not found.");
		nodeAccessF++;
		return false;
	}
	
	public boolean insert(int newKey)
	{
		System.out.println("The key: "+newKey+" is inserted.");
		int i=0;
		int j=0;
		treeNode currentNode=root;
		Stack<parent> parents=new Stack();
		while(currentNode.child[0].leaf==false)
		{
			for(i=0;i<currentNode.ksize;i++)
			{
				if(newKey==currentNode.key[i])
				{
					return false;
				}
				if(newKey<currentNode.key[i])
				{
					parents.push(new parent(currentNode,i));
					currentNode=currentNode.child[i];
					break;
				}
				if(i==(currentNode.ksize-1))
				{
					parents.push(new parent(currentNode,i+1));
					currentNode=currentNode.child[i+1];
					break;
				}
			}
		}
		nodeAccessI=nodeAccessI+height+1;
		if(currentNode.ksize<3)
		{
			return insertIntoNode(newKey, currentNode);
		}
		else
		{
			if(!insertIntoNode(newKey,currentNode))
				return false;
			while(!parents.empty())
			{
				overflow++;
				nodeAccessI=nodeAccessI+2;
				parent upperOne=parents.pop();
				treeNode upperNode=upperOne.node;
				int upperIndex=upperOne.index;
				//System.out.println(upperNode);
				//System.out.println(upperIndex);
				splitNode(upperNode, upperIndex);
				if(upperNode.ksize<4)
					return true;
			}
			nodeAccessI=nodeAccessI+2;
			overflow++;
			height++;
			treeNode temp=new treeNode(false);
			temp.child[0]=root;
			temp.csize=1;
			splitNode(temp,0);
			root=temp;
			return true;
		}
	}
	
	public boolean delete(int target)
	{
		System.out.println("The key: "+target+" is deleted.");
		int i=0;
		int j=0;
		treeNode currentNode=root;
		if(root.ksize==0)
			return false;
		Stack<parent> parents=new Stack();
		outerloop:
		while(currentNode.leaf!=true)
		{
			for(i=0;i<currentNode.ksize;i++)
			{
				if(target==currentNode.key[i])
				{
					break outerloop;
				}
				if(target<currentNode.key[i])
				{
					parents.push(new parent(currentNode,i));
					currentNode=currentNode.child[i];
					break;
				}
				if(i==currentNode.ksize-1)
				{
					parents.push(new parent(currentNode,i+1));
					currentNode=currentNode.child[i+1];
					break;
				}
			}	
		}
		//cannot find the key to delete
		if(currentNode.leaf==true)
		{
			nodeAccessD=nodeAccessD+height+1;
			return false;
		}
		//find the key at the bottom level
		if(currentNode.child[0].leaf==true)
		{
			nodeAccessD=nodeAccessD+height+1;
			for(j=i+1;j<currentNode.ksize;j++)
			{
				currentNode.key[j-1]=currentNode.key[j];
			}
			currentNode.ksize--;
			currentNode.csize--;
			//bottom level has more than 1 key, underflow didn't happen
			if(currentNode.ksize>0)
			{
				return true;
			}
			//handle situation when root is empty
			if(root.ksize==0)
				return true;
			//otherwise bottom level underflows
			parent upperOne;
			treeNode upperNode;
			int upperIndex;
			while(!parents.empty())
			{
				nodeAccessD+=3;
				underflow++;
				merge++;
				upperOne=parents.pop();
				upperNode=upperOne.node;
				upperIndex=upperOne.index;
				mergeNode(upperNode,upperIndex);
				if(upperNode.ksize>0)
				{
					return true;
				}
			}
			underflow++;
			height--;
			root=root.child[0];
			return true;
		}
		//target at an internal level
		else
		{
			//the internal node will be accessed twice
			nodeAccessD=nodeAccessD+height+2;
			treeNode preNode;
			preNode=currentNode.child[i];
			parents.push(new parent(currentNode,i));
			while(preNode.child[0].leaf!=true)
			{
				parents.push(new parent(preNode,preNode.csize-1));
				preNode=preNode.child[preNode.csize-1];
			}
			currentNode.key[i]=preNode.key[preNode.ksize-1];
			preNode.ksize--;
			preNode.csize--;
			if(preNode.ksize>0)
			{
				return true;
			}
			currentNode=preNode;
			//otherwise bottom level underflows
			parent upperOne;
			treeNode upperNode;
			int upperIndex;
			while(!parents.empty())
			{
				nodeAccessD+=3;
				underflow++;
				merge++;
				upperOne=parents.pop();
				upperNode=upperOne.node;
				upperIndex=upperOne.index;
				mergeNode(upperNode,upperIndex);
				if(upperNode.ksize>0)
				{
					return true;
				}
			}
			underflow++;
			height--;
			root=root.child[0];
			return true;
		}
	}
	
	public void levelPrint()
	{
		printedNode currentNode;
		LinkedList<printedNode> bfsList=new LinkedList();
		int i=0;
		int level=0;
		int lastNodeLevel=-1;
		bfsList.add(new printedNode(root,level));
		while(bfsList.peek()!=null)
		{
			currentNode=bfsList.poll();
			for(i=0;i<currentNode.t.csize;i++)
			{
				if(currentNode.t.child[i].leaf!=true)
				{
					level=currentNode.level+1;
					bfsList.add(new printedNode(currentNode.t.child[i],level));
				}
			}
			if(currentNode.level>lastNodeLevel)
			{
				System.out.print("\n"+currentNode.t);
			}
			else
			{
				System.out.print("   "+currentNode.t);
			}
			lastNodeLevel=currentNode.level;
		}
		System.out.println();
		System.out.println();
	}
	
	//merge and optional split
	private void mergeNode(treeNode upperNode,int upperIndex)
	{
		treeNode sibling;
		int i=0;
		//the first child has to merge with right sibling
		if(upperIndex==0)
		{
			sibling=upperNode.child[1];
			//keys in sibling move towards right to give position 0 space
			for(i=sibling.ksize-1;i>=0;i--)
			{
				sibling.key[i+1]=sibling.key[i];
				sibling.child[i+2]=sibling.child[i+1];
			}
			sibling.child[1]=sibling.child[0];
			sibling.child[0]=upperNode.child[upperIndex].child[0];
			sibling.key[0]=upperNode.key[upperIndex];
			sibling.ksize++;
			sibling.csize++;
			//keys in upperNodes move toward left to occupy the position 0
			for(i=upperIndex+1;i<upperNode.ksize;i++)
			{
				upperNode.key[i-1]=upperNode.key[i];
				upperNode.child[i-1]=upperNode.child[i];
			}
			upperNode.child[upperNode.csize-2]=upperNode.child[upperNode.csize-1];
			upperNode.ksize--;
			upperNode.csize--;
			if(sibling.ksize>3)
			{
				splitNode(upperNode,0);
			}
		}
		//merge with left sibling
		else
		{
			treeNode leftSibling=upperNode.child[upperIndex-1];
			//give left sibling a new key and child at the end
			leftSibling.key[leftSibling.ksize]=upperNode.key[upperIndex-1];
			leftSibling.child[leftSibling.csize]=upperNode.child[upperIndex].child[0];
			leftSibling.ksize++;
			leftSibling.csize++;
			//keys and children in upperNode move toward left
			for(i=upperIndex;i<upperNode.ksize;i++)
			{
				upperNode.key[i-1]=upperNode.key[i];
				upperNode.child[i]=upperNode.child[i+1];
			}
			upperNode.ksize--;
			upperNode.csize--;
			if(leftSibling.ksize>3)
			{
				splitNode(upperNode,upperIndex-1);
			}
		}
	}
	
	private void splitNode(treeNode upperNode, int upperIndex)
	{
		int i=0;
		treeNode targetNode=upperNode.child[upperIndex];
		for(i=upperNode.ksize-1;i>=upperIndex;i--)
		{
			upperNode.key[i+1]=upperNode.key[i];
			upperNode.child[i+2]=upperNode.child[i+1];
		}
		treeNode splitRight=new treeNode(false);
		splitRight.child[0]=targetNode.child[2];
		splitRight.child[1]=targetNode.child[3];
		splitRight.child[2]=targetNode.child[4];
		splitRight.key[0]=targetNode.key[2];
		splitRight.key[1]=targetNode.key[3];
		upperNode.key[upperIndex]=targetNode.key[1];
		upperNode.child[upperIndex+1]=splitRight;
		upperNode.ksize++;
		upperNode.csize++;
		targetNode.ksize=1;
		targetNode.csize=2;
		splitRight.ksize=2;
		splitRight.csize=3;
	}
	
	
	private boolean insertIntoNode(int newKey, treeNode currentNode)
	{
		int i=0;
		int j=0;
		for(i=0;i<currentNode.ksize;i++)
		{
			if(newKey==currentNode.key[i])
			{
				return false;
			}
			if(newKey<currentNode.key[i])
			{
				for(j=currentNode.ksize-1;j>=i;j--)
				{
					currentNode.key[j+1]=currentNode.key[j];
					currentNode.child[j+2]=currentNode.child[j+1];
				}
				currentNode.ksize++;
				currentNode.csize++;
				currentNode.key[i]=newKey;
				//currentNode.child[i+1]=new treeNode(true);
				currentNode.child[currentNode.csize-1]=new treeNode(true);
				return true;
			}
		}
		currentNode.ksize++;
		currentNode.csize++;
		currentNode.key[i]=newKey;
		currentNode.child[i+1]=new treeNode(true);
		return true;
	}
	
	
	private class treeNode
	{
		int[] key;
		treeNode[] child;
		int ksize;
		int csize;
		boolean leaf;
		public treeNode(boolean leaf)
		{
			key=new int[5];
			child=new treeNode[5];
			ksize=0;
			csize=0;
			this.leaf=leaf;
		}
		public String toString()
		{
			String specKeys=new String();
			int i=0;
			for(i=0;i<ksize-1;i++)
			{
				specKeys=specKeys+(new Integer(key[i]).toString())+", ";
			}
			if(ksize>0)
				specKeys=specKeys+(new Integer(key[ksize-1]).toString());
			specKeys="("+specKeys+")";
			return specKeys;
		}
	}
	private class parent
	{
		treeNode node;
		int index;
		public parent(treeNode t, int i)
		{
			node=t;
			index=i;
		}
	}
	private class printedNode
	{
		treeNode t;
		int level;
		public printedNode(treeNode t, int level)
		{
			this.t=t;
			this.level=level;
		}
	}
	
	
}
