package org.useware.kernel.model.mapping;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a node of the Tree<T> class. The Node<T> is also a container, and
 * can be thought of as instrumentation to determine the location of the type T
 * in the Tree<T>.
 */
public class Node<T> {

    private T data;

    private Node<T> parent;
    private List<Node<T>> children;

    public Node(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    /**
     * Finds the parent which satisfies the predicate. Starts with the parent of this node not the node itself.
     * @param predicate
     * @return
     */
    public Node<T> findParent(NodePredicate<T> predicate) {
        return findInternal(this.parent, predicate);
    }

    private Node<T> findInternal(Node<T> parent, NodePredicate<T> predicate) {
        if (parent != null) {
            if (predicate.appliesTo(parent)) {
                return parent;
            }
            else {
                return findInternal(parent.parent, predicate);
            }
        }
        return null;
    }

    public List<Node<T>> collectParents(NodePredicate<T> predicate) {
        List<Node<T>> results = new LinkedList<Node<T>>();
        collectInternal(results, this.parent, predicate);
        return results;
    }

    private void collectInternal(List<Node<T>> results, Node<T> parent, NodePredicate<T> predicate) {
        if (parent != null) {
            if (predicate.appliesTo(parent)) {
                results.add(parent);
            }
            collectInternal(results, parent.parent, predicate);
        }
    }



    /**
     * Return the children of Node<T>. The Tree<T> is represented by a single
     * root Node<T> whose children are represented by a List<Node<T>>. Each of
     * these Node<T> elements in the List can have children. The getChildren()
     * method will return the children of a Node<T>.
     * @return the children of Node<T>
     */
    public List<Node<T>> getChildren() {
        if (this.children == null) {
            return new ArrayList<Node<T>>();
        }
        return this.children;
    }



    /**
     * Returns the number of immediate children of this Node<T>.
     * @return the number of immediate children.
     */
    public int getNumberOfChildren() {
        if (children == null) {
            return 0;
        }
        return children.size();
    }

    public Node<T> addChild(T data) {
        return addChild(new Node<T>(data));
    }


    /**
     * Adds a child to the list of children for this Node<T>. The addition of
     * the first child will create a new List<Node<T>>.
     * @param child a Node<T> object to set.
     */
    public Node<T> addChild(Node<T> child) {
        if (children == null) {
            children = new ArrayList<Node<T>>();
        }
        child.parent = this;
        children.add(child);

        return child;
    }

    /**
     * Inserts a Node<T> at the specified position in the child list. Will     * throw an ArrayIndexOutOfBoundsException if the index does not exist.
     * @param index the position to insert at.
     * @param child the Node<T> object to insert.
     * @throws IndexOutOfBoundsException if thrown.
     */
    public void insertChildAt(int index, Node<T> child) throws IndexOutOfBoundsException {
        if (index == getNumberOfChildren()) {
            // this is really an append
            addChild(child);
            return;
        } else {
            children.get(index); //just to throw the exception, and stop here
            children.add(index, child);
        }
    }

    public boolean removeChild(Node<T> child) {
        return children.remove(child);
    }


    /**
     * Remove the Node<T> element at index index of the List<Node<T>>.
     * @param index the index of the element to delete.
     * @throws IndexOutOfBoundsException if thrown.
     */
    public void removeChildAt(int index) throws IndexOutOfBoundsException {
        Node<T> child = children.get(index);
        child.parent = null;
        children.remove(index);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        /*StringBuilder sb = new StringBuilder();
        String data = getData()!=null ? getData().toString() : "<no data>";
        sb.append("{").append(data).append(",[");

        if(getChildren()!=null){
            int i = 0;
            for (Node<T> e : getChildren()) {
                if (i > 0) {
                    sb.append(",");
                }
                String childData = e.getData()!=null ? e.getData().toString() : "<no data>";
                sb.append(childData);
                i++;
            }
        }
        sb.append("]").append("}");  */

        return getData().toString();
    }
}
