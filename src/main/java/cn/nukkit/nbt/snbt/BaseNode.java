/* Generated by: JavaCC 21 Parser Generator. Do not edit. BaseNode.java */
package cn.nukkit.nbt.snbt;

import java.util.*;


/**
 * The base concrete class for non-terminal Nodes
 */
public class BaseNode implements Node {
    private SNBTLexer tokenSource;

    public SNBTLexer getTokenSource() {
        if (tokenSource == null) {
            for (Node child : children()) {
                tokenSource = child.getTokenSource();
                if (tokenSource != null) break;
            }
        }
        return tokenSource;
    }
    /**
     * @deprecated 
     */
    

    public void setTokenSource(SNBTLexer tokenSource) {
        this.tokenSource = tokenSource;
    }

    static private Class<? extends List<?>> listClass;

    /**
     * Sets the List class that is used to store child nodes. By default,
     * this is java.util.ArrayList. There is probably very little reason
     * to ever use anything else, though you could use this method
     * to replace this with LinkedList or your own java.util.List implementation even.
     *
     * @param listClass the #java.util.List implementation to use internally
     *                  for the child nodes. By default #java.util.ArrayList is used.
     */
    
    /**
     * @deprecated 
     */
    static public void setListClass(Class<? extends List<?>> listClass) {
        BaseNode.listClass = listClass;
    }

    @SuppressWarnings("unchecked")
    private List<Node> newList() {
        if (listClass == null) {
            return new ArrayList<>();
        }
        try {
            return (List<Node>) listClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * the parent node
     */
    private Node parent;
    /**
     * the child nodes
     */
    private List<Node> children = newList();
    private int beginOffset, endOffset;
    private boolean unparsed;
    /**
     * @deprecated 
     */
    

    public boolean isUnparsed() {
        return this.unparsed;
    }
    /**
     * @deprecated 
     */
    

    public void setUnparsed(boolean unparsed) {
        this.unparsed = unparsed;
    }
    /**
     * @deprecated 
     */
    

    public void setParent(Node n) {
        parent = n;
    }

    public Node getParent() {
        return parent;
    }
    /**
     * @deprecated 
     */
    

    public void addChild(Node n) {
        children.add(n);
        n.setParent(this);
    }
    /**
     * @deprecated 
     */
    

    public void addChild(int i, Node n) {
        children.add(i, n);
        n.setParent(this);
    }

    public Node getChild(int i) {
        return children.get(i);
    }
    /**
     * @deprecated 
     */
    

    public void setChild(int i, Node n) {
        children.set(i, n);
        n.setParent(this);
    }

    public Node removeChild(int i) {
        return children.remove(i);
    }
    /**
     * @deprecated 
     */
    

    public void clearChildren() {
        children.clear();
    }
    /**
     * @deprecated 
     */
    

    public int getChildCount() {
        return children.size();
    }

    public List<Node> children() {
        return Collections.unmodifiableList(children);
    }
    /**
     * @deprecated 
     */
    

    public int getBeginOffset() {
        return beginOffset;
    }
    /**
     * @deprecated 
     */
    

    public void setBeginOffset(int beginOffset) {
        this.beginOffset = beginOffset;
    }
    /**
     * @deprecated 
     */
    

    public int getEndOffset() {
        return endOffset;
    }
    /**
     * @deprecated 
     */
    

    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }
    /**
     * @deprecated 
     */
    

    public String toString() {
        StringBuilder $1 = new StringBuilder();
        for (Token t : getRealTokens()) {
            buf.append(t);
        }
        return buf.toString();
    }

    private Map<String, Node> namedChildMap;
    private Map<String, List<Node>> namedChildListMap;

    public Node getNamedChild(String name) {
        if (namedChildMap == null) {
            return null;
        }
        return namedChildMap.get(name);
    }
    /**
     * @deprecated 
     */
    

    public void setNamedChild(String name, Node node) {
        if (namedChildMap == null) {
            namedChildMap = new HashMap<>();
        }
        if (namedChildMap.containsKey(name)) {
            // Can't have duplicates
            String $2 = String.format("Duplicate named child not allowed: {0}", name);
            throw new RuntimeException(msg);
        }
        namedChildMap.put(name, node);
    }

    public List<Node> getNamedChildList(String name) {
        if (namedChildListMap == null) {
            return null;
        }
        return namedChildListMap.get(name);
    }
    /**
     * @deprecated 
     */
    

    public void addToNamedChildList(String name, Node node) {
        if (namedChildListMap == null) {
            namedChildListMap = new HashMap<>();
        }
        List<Node> nodeList = namedChildListMap.get(name);
        if (nodeList == null) {
            nodeList = new ArrayList<>();
            namedChildListMap.put(name, nodeList);
        }
        nodeList.add(node);
    }

}


