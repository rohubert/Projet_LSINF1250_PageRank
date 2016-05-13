import Jama.Matrix;
/**
 * Décrivez votre classe Algorithm ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Algorithm
{
    /**
     *Input : Une matrice d’adjacence A d’un graphe dirigé, 
     *pondéré et régulier G ainsi qu’un paramètre de téléportation α entre 0 et 1 
     *(inutile de le vériﬁer) et enﬁn un vecteur de personnalisation q. 
     *
     *Output : Un vecteur x contenant les scores d’importance des noeuds 
     *ordonnés dans le même ordre que la matrice d’adjacence
     *
     *Utilise la "Power Methode"
     */
    public static double[] rank(Matrix a,  Matrix q){
        int moveCount=0;
        Matrix newVector = new Matrix(1,q.getColumnDimension());
        while(true){
            
            newVector = power(q,a);
            if(equalV(newVector,q)){
                return newVector.getColumnPackedCopy();
            }
            q = newVector;
        }
    }
    
    public static Matrix power(Matrix personnalisationVector, Matrix a){
        return personnalisationVector.times(a);
    }
    
    public static boolean equalV(Matrix a, Matrix b){
        if((a.getRowDimension() != b.getRowDimension()) || (a.getColumnDimension() != b.getColumnDimension())){
            return false;
        }
        for(int i=0; i<a.getRowDimension();i++){
            for(int j=0; j<a.getColumnDimension();j++){
                if(!((a.get(i,j)-b.get(i,j) < 0.0000005) && (b.get(i,j)-a.get(i,j) < 0.0000005))){
                    return false;
                }
            }
        }
        return true;
    }
}
