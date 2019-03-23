public class Main<T> {
    
    public static void main(String[] args) {
        BinaryTree<Integer> prueba = new BinaryTree<>();
        //BinaryTree<Double> prueba = new BinaryTree<>();
        
        /*
        prueba.add(5);
        prueba.add(4);
        prueba.add(6);
        prueba.add(1);
        prueba.add(8);
        */
        
//Aquí llenare el árbol con 8 número aleatorios entre 10 y 99
        int menor = 10, mayor = 99, cantidad = 8;
        for (int i = 0; i < cantidad; i++)
            prueba.add((int)(Math.random() * (mayor - menor)) + menor);
        
        System.out.println("Menor: " + prueba.minor());
        System.out.println("Mayor: " + prueba.biggest());
        System.out.println("Altura: " + prueba.height());
        System.out.println("Anchura: " + prueba.width());
        
        //prueba.preOrder();
        prueba.inOrder();
        //prueba.postOrder();
    }
    
}
