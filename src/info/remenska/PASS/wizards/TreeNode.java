package info.remenska.PASS.wizards;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> implements Iterable<TreeNode<T>>, Comparable<T> {

	public T data;
	public TreeNode<T> parent;
	public List<TreeNode<T>> children;
	public boolean isQuestion;
	public List<TreeNode<T>> toDelete;
	public String scope, behavior;
	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}

	private List<TreeNode<T>> elementsIndex;

	public TreeNode(T data, boolean isQuestion) {
		this.data = data;
		this.children = new LinkedList<TreeNode<T>>();
		this.elementsIndex = new LinkedList<TreeNode<T>>();
		this.elementsIndex.add(this);
		this.isQuestion = isQuestion;
	}

	public TreeNode<T> addChild(T child, boolean isQuestion) {
		TreeNode<T> childNode = new TreeNode<T>(child, isQuestion);
		childNode.parent = this;
		this.children.add(childNode);
		this.registerChildForSearch(childNode);
		return childNode;
	}

	public void setScope(String scope){
	this.scope = scope;	
	}
	
	public String getScope(){
		return this.scope;
	}
	
	public void setBehavior(String behavior){
		this.behavior = behavior;
	}
	
	public String getBehavior(){
		return behavior;
	}
	
	public void removeChild(T child){
		this.elementsIndex.remove(child);
		this.children.remove(child);
	}
	
	public void removeChildren() {
		toDelete = new LinkedList<TreeNode<T>>(this.elementsIndex);
		this.children.clear();
		this.elementsIndex.clear();
		this.elementsIndex.add(this);
		toDelete.remove(this);
		if(parent!=null)
			parent.removeHigher(toDelete);

	}
	
	public void removeHigher(List<TreeNode<T>> toDelete){
		this.elementsIndex.removeAll(toDelete);
		if (parent!=null)
			parent.removeHigher(toDelete);
	}
	
	public int getLevel() {
		if (this.isRoot())
			return 0;
		else
			return parent.getLevel() + 1;
	}

	private void registerChildForSearch(TreeNode<T> node) {
		elementsIndex.add(node);
		if (parent != null)
			parent.registerChildForSearch(node);
	}

	public TreeNode<T> findTreeNode(Comparable<T> cmp) {
		for (TreeNode<T> element : this.elementsIndex) {
			T elData = element.data;
			if (cmp.compareTo(elData) == 0)
				return element;
		}

		return null;
	}

	public TreeNode<T> findTreeNodeViaObject(T cmp) {
		for (TreeNode<T> element : this.elementsIndex) {
			T elData = element.data;
			if (cmp.equals(elData))
				return element;
		}

		return null;
	}
	
	@Override
	public String toString() {
		return data != null ? data.toString() : "[data null]";
	}

	@Override
	public Iterator<TreeNode<T>> iterator() {
		TreeNodeIter<T> iter = new TreeNodeIter<T>(this);
		return iter;
	}

	@Override
	public int compareTo(Object arg0) {
		if(this.data.equals(((TreeNode<T>)arg0).data))
			return 0;
		else return -1;
	}

}