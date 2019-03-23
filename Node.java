public class Node<T> {
    private T       value;
    private Node<T> right;
    private Node<T> left;
    private int     count;
    private int     level;
    
    public Node() {
        this(null);
    }
    
    public Node(T value) {
        this.value = value;
        this.right = null;
        this.left  = null;
        this.count = 0;
    }
    
    public T getValue() {
        return value;
    }
    
    public void setValue(T value) {
        this.value = value;
    }
    
    public Node<T> getRight() {
        return right;
    }
    
    public void setRight(Node<T> right) {
        this.right = right;
    }
    
    public Node<T> getLeft() {
        return left;
    }
    
    public void setLeft(Node<T> left) {
        this.left = left;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
}
