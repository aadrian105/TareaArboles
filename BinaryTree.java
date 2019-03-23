public class BinaryTree<T extends Comparable<T>> {
    private Node<T> groot;
    private int     maxLevel;
    
    public BinaryTree() {
        groot = new Node<>();
        groot.setLevel(0);
        maxLevel = -1;
    }
    
    public void isEmpty() throws isEmptyException {
        if (groot.getRight() == null)
            throw new isEmptyException("Árbol vacío");
    }
    
    private Node<T> getFather(T value) {
        if (groot.getRight() == null)
            return null;
        else if (groot.getRight().getValue().equals(value))
            return null;
        else
            return getFatherPart2(groot.getRight(), value);
    }
    
    private Node<T> getFatherPart2(Node<T> node, T value) {
        if (value.compareTo(node.getValue()) == 1)
            if (node.getRight() == null)
                return null;
            else if (node.getRight().getValue().equals(value))
                return node;
            else
                return getFatherPart2(node.getRight(), value);
        else
            if (node.getLeft() == null)
                return null;
            else if (node.getLeft().getValue().equals(value))
                return node;
            else
                return getFatherPart2(node.getLeft(), value);
    }
    
    private Node<T> getMinorNode(Node<T> node) {
        if (node.getLeft() == null)
            return node;
        else
            return getMinorNode(node.getLeft());
    }
    
    public boolean add(T value) {
        if (value == null)
            return false;
        else {
            if (groot.getRight() == null) {
                Node<T> n = new Node<>(value);
                groot.setRight(n);
                groot.setLevel(0);
                maxLevel = 0;
                return true;
            } else {
                return addPart2(groot.getRight(), value);
            }
        }
    }
    
    private boolean addPart2(Node<T> node, T value) {
        if (node.getValue().equals(value)) {
            node.setCount(node.getCount() + 1);
            return true;
        }
        else
            if (value.compareTo(node.getValue()) == 1)
                if (node.getRight() == null) {
                    Node<T> n = new Node<>(value);
                    node.setRight(n);
                    n.setLevel(node.getLevel() + 1);
                    if (n.getLevel() > maxLevel)
                        maxLevel = n.getLevel();
                    return true;
                } else
                    return addPart2(node.getRight(), value);
            else
                if (node.getLeft() == null) {
                    Node<T> n = new Node<>(value);
                    node.setLeft(n);
                    n.setLevel(node.getLevel() + 1);
                    if (n.getLevel() > maxLevel)
                        maxLevel = n.getLevel();
                    return true;
                } else
                    return addPart2(node.getLeft(), value);
    }
    
    public boolean add(Node<T> node) {
        return add(node.getValue());
    }
    
    public boolean remove(T value) {
        if (value == null || groot.getRight() == null)
            return false;
        else {
            if (groot.getRight().getValue().equals(value)) { // Es el primer valor
                Node<T> rem = groot.getRight();
                if (rem.getCount() > 0) // esta repetido
                    rem.setCount(rem.getCount() - 1);
                else { // no está repetido
                    if (rem.getLeft() == null)
                        if (rem.getRight() == null) //es el único en el arbol
                            groot.setRight(null);
                        else { // tiene un hijo a la derecha
                            groot.setRight(rem.getRight());
                            rem.setRight(null);
                        }
                    else
                        if (rem.getRight() == null) { // tiene un hijo a la izquierda
                            groot.setRight(rem.getLeft());
                            rem.setLeft(null);
                        }
                        else // tiene dos hijos
                            if (rem.getRight().getLeft() == null) {
                                rem.getRight().setLeft(rem.getLeft());
                                groot.setRight(rem.getRight());
                                rem.setLeft(null);
                                rem.setRight(null);
                            } else {
                                Node<T> min = getMinorNode(rem.getRight());
                                Node<T> dad = getFatherPart2(rem.getRight(), min.getValue());
                                if (min.getRight() == null) // el menor no tiene hijos
                                    dad.setLeft(null);
                                else // el menor tiene hijo a la derecha
                                    dad.setLeft(min.getRight());
                                min.setLeft(rem.getLeft());
                                min.setRight(rem.getRight());
                                groot.setRight(min);
                                rem.setRight(null);
                                rem.setLeft(null);
                            }
                    rem = null;
                    System.gc();
                    levelUpdate();
                }
                return true;
            } else { // No es el primer valor
                Node<T> rem = search(value);
                if (rem == null) // no está en la lista
                    return false;
                else { // si está en la lista
                    if (rem.getCount() > 0) // está repetido
                        rem.setCount(rem.getCount() - 1);
                    else { // No está repetido
                        Node<T> remFather = getFatherPart2(groot, value);
                        if (rem.getLeft() == null)
                            if (rem.getRight() == null) { // no tiene hijos
                                if (value.compareTo(remFather.getValue()) == 1)
                                    remFather.setRight(null);
                                else
                                    remFather.setLeft(null);
                            } else { // tiene hijo derecha
                                if (value.compareTo(remFather.getValue()) == 1)
                                    remFather.setRight(rem.getRight());
                                else
                                    remFather.setLeft(rem.getRight());
                                rem.setRight(null);
                            }
                        else
                            if (rem.getRight() == null) { // hijo izquierda
                                if (value.compareTo(remFather.getValue()) == 1)
                                    remFather.setRight(rem.getLeft());
                                else
                                    remFather.setLeft(rem.getLeft());
                                rem.setLeft(null);
                            } else { // dos hijos
                                if (rem.getRight().getLeft() == null) {
                                    rem.getRight().setLeft(rem.getLeft());
                                    if (value.compareTo(remFather.getValue()) == 1)
                                        remFather.setRight(rem.getRight());
                                    else
                                        remFather.setLeft(rem.getRight());
                                    rem.setLeft(null);
                                    rem.setRight(null);
                                } else {
                                    Node<T> min = getMinorNode(rem.getRight());
                                    Node<T> minFather = getFatherPart2(rem.getRight(), min.getValue());
                                    
                                }
                            }
                        rem = null;
                        System.gc();
                        levelUpdate();
                    }
                    return true;
                }
            }
        }
    }
    
    public Node<T> search(T value) {
        if (groot.getRight() == null)
            return null;
        else
            return searchPart2(groot.getRight(), value);
    }
    
    private Node<T> searchPart2(Node<T> node, T value) {
        if (node.getValue().equals(value))
            return node;
        else
            if (value.compareTo(node.getValue()) == 1)
                if (node.getRight() == null)
                    return null;
                else
                    return searchPart2(node.getRight(), value);
            else
                if (node.getLeft() == null)
                    return null;
                else
                    return searchPart2(node.getLeft(), value);
    }
    
    public T depthFirstSearch(T value) {return null;}
    public void balance(){}
    
    public T biggest(){
        if (groot.getRight() == null)
            return null;
        else
            return biggest(groot.getRight());
    }
    
    private T biggest(Node<T> node) {
        if (node.getRight() == null)
            return node.getValue();
        else
            return biggest(node.getRight());
    }
    
    public T minor(){
        if (groot.getRight() == null)
            return null;
        else
            return minor(groot.getRight());
    }
    
    private T minor(Node<T> node) {
        if (node.getLeft() == null)
            return node.getValue();
        else
            return minor(node.getLeft());
    }
    
    public void levelUpdate() {
        if (groot.getRight() != null)
            levelUpdate(groot.getRight(), 0);
    }
    
    private void levelUpdate(Node<T> node, int lvl) {
        if (node.getLevel() != lvl)
            node.setLevel(lvl);
        if (node.getLeft() != null)
            levelUpdate(node.getLeft(), lvl + 1);
        if (node.getRight() != null)
            levelUpdate(node.getRight(), lvl + 1);
    }
    
    public void preOrder() {
        try {
            isEmpty();
            preOrder(groot.getRight());
            System.out.println();
        } catch (isEmptyException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void preOrder(Node<T> node) {
        if (node != null) {
            System.out.print("  [" + node.getValue() + "] {" + node.getLevel() + "," + node.getCount() + "}");
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }
    
    public void inOrder() {
        try {
            isEmpty();
            inOrder(groot.getRight());
            System.out.println();
        } catch (isEmptyException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void inOrder(Node<T> node) {
        if (node != null) {
            inOrder(node.getLeft());
            System.out.print("  [" + node.getValue() + "] {" + node.getLevel() + "," + node.getCount() + "}");
            inOrder(node.getRight());
        }
    }
    
    public void postOrder() {
        try {
            isEmpty();
            postOrder(groot.getRight());
            System.out.println();
        } catch (isEmptyException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void postOrder(Node<T> node) {
        if (node != null) {
            postOrder(node.getLeft());
            postOrder(node.getRight());
            System.out.print("  [" + node.getValue() + "] {" + node.getLevel() + "," + node.getCount() + "}");
        }
    }
    
    public int height() {
        return maxLevel + 1;
    }
    
    public int width() {
        return (int)Math.pow(2, maxLevel + 1) - 1;
    }
    
    public void between(T start, T end) {}
    
}
