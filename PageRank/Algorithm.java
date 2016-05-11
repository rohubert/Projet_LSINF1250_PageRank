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
    public static double[] rank(Matrix a, double alpha, Matrix q){
        int moveCount=0;
        while(moveCount!=20){
            q = q.times(a);
            moveCount++;
        }
        return q.getColumnPackedCopy();
    }
}
