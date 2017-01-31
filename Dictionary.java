//Dictionary.java
//Michael Shen
//mjshen
//cs12b
//lab7

public class Dictionary implements DictionaryInterface{

   private class Node{
      Pair n;
      Node left;
      Node right;

      Node(Pair m){
         n = m;
         left = null;
         right = null;
      }
   }
   private class Pair{
      String key;
      String value;

      Pair(String k, String v){
         key = k;
         value = v;
      }
   }

   private Node head;     
   private int numItems;  

   public Dictionary(){
      head = null;
      numItems = 0;
   }


   private Node findKey(Node R, String k){
      if(R==null || R.n.key.equals(k))
         return R;
      if( k.compareTo(R.n.key)<0)
         return findKey(R.left, k);
      else //(k.compareTo(R.key)>0)
         return findKey(R.right, k);
      }

   Node findParent(Node N, Node R){
      Node P = null;
      if( N != R ){
         P = R;
         while(P.left != N && P.right != N){
            if(N.n.key.compareTo(P.n.key)<0)
               P = P.left;
            else
               P = P.right;
         }
      }
      return P;
   }

   Node findLeftmost(Node R){
      Node L = R;
      if( L!=null )
         for ( ; L.left != null; L = L.left);
            return L;
   }

   void printInOrder(Node R){
      if( R != null){
         printInOrder(R.left);
         System.out.println(R.n.key + " " + R.n.value);
         printInOrder(R.right);
      }
   }

   void deleteAll(Node N){
      if(N != null){
         deleteAll(N.left);
         deleteAll(N.right);
      }
   }

   public boolean isEmpty(){
      return(numItems == 0);
   }

   public int size() {
      return numItems;
   }

   public String lookup(String key){
      Node N;
      N = findKey(head, key);
      return( N == null? null : N.n.value);
   }

   public void insert(String key, String value) throws DuplicateKeyException{
      Node N, A, B;
      if( findKey(head, key) != null ){
         throw new DuplicateKeyException("cannot insert duplicate keys");
      }
      N = new Node(new Pair (key, value));
      B = null;
      A = head;
      while(A != null){
         B = A;
         if( key.compareTo(A.n.key)<0 )
            A = A.left;
         else
            A = A.right;
      }
      if( B == null) head = N;
      else if( key.compareTo(B.n.key) < 0 ) 
         B.left = N;
      else B.right = N;
      numItems++;
      
   }

   public void delete(String key) throws KeyNotFoundException{
      Node N, P, S;
      if( findKey(head, key) == null )
         throw new KeyNotFoundException("cannot delete non-existent key");
      N = findKey(head, key);
      if(N.left == null && N.right == null){
         if(N==head)
            head = null;
         else{
            P = findParent(N, head);
            if(P.right == N)
               P.right = null;
            else
               P.left = null;
         }
      }
      else if(N.right == null){
         if(N == head)
            head = N.left;
         else{
            P = findParent(N, head);
            if(P.right == N)
               P.right = N.left;
            else P.left = N.left;
         }
      }
      else if( N.left == null ){
         if( N == head )
            head = N.right;
         else{
            P = findParent(N, head);
            if( P.right == N )
               P.right = N.right;
            else
               P.left = N.left;
         }
      }
      else{
         S = findLeftmost(N.right);
         N.n.key = S.n.key;
         N.n.value = S.n.value;
         P = findParent(S, N);
         if(P.right == S)
            P.right = S.right;
         else
            P.left = S.right;
      }
      numItems--;
   }

   public void makeEmpty(){
      deleteAll(head);
      head = null;
      numItems = 0;
   }

   public String toString(){
      printInOrder(head);
      return "";
   }
}
# BST_Dictionary
