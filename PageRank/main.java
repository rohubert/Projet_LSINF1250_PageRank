import Jama.Matrix;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.lang.Math;

/**
 * Cette classe charge le fichier de données contenant la matrice d'adjacence. 
 * fichier texte. On suppose que les matrices sont carrées
 * Appelle la procédure calculant les scores PageRank et les imprime à l'écran
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Main
{
    public static void main(String args[]){
        if(args.length==1){
            double[][] adjacencyMatrice = fileToTable(args[0]); //implementation of the adjency Matrice by reading the textfile
            Matrix matrix;
            if(adjacencyMatrice != null){
                int m = adjacencyMatrice.length; //size of the matrice
                matrix = new Matrix(adjacencyMatrice);
                //System.out.println("The adjency matrix is: ");
                //afficheMatrix(matrix);
                
                double[] degreeVector = getDegreeVector(adjacencyMatrice);
                System.out.println("The degree vector is: ");
                for(int i = 0;i<degreeVector.length;i++){
                    System.out.print(Double.toString(degreeVector[i])+", ");
                }
                System.out.println();
                //double teleportationParameter = Math.random();
                double teleportationParameter = 0.9;
                Matrix leapProbabilities = getLeapProbabilities(m,teleportationParameter);
                //System.out.println("The leapProbabilities matrix is: ");
                //afficheMatrix(leapProbabilities);
                
                Matrix linkProbabilities = getLinkProbabilities(adjacencyMatrice, degreeVector, teleportationParameter); 
                //System.out.println("The linkProbabilities matrix is: ");
                //afficheMatrix(linkProbabilities);
                
                Matrix transitionMatrix = leapProbabilities.plus(linkProbabilities);
                //System.out.println("The transition matrix is: ");
                //afficheMatrix(transitionMatrix);
                //System.out.println(verify(transitionMatrix));
                
                Matrix personalisationVector = new Matrix(1,m);
                personalisationVector.set(0,0,1.0); //value of personalisationVector is now: {1 , 0 , ... , 0 , 0}

                //Appel de l'algorithme
                double[] rankingVector = Algorithm.rank(transitionMatrix, teleportationParameter, personalisationVector);
                
                //Print the final vector to System.out
                System.out.println("The ranking Vector is: ");
                double sum =0;
                for(int i=0;i<rankingVector.length-1;i++){
                    System.out.print(Double.toString(rankingVector[i])+", ");
                    sum+=rankingVector[i];
                }
                System.out.println(Double.toString(rankingVector[rankingVector.length-1]));
                sum+=rankingVector[rankingVector.length-1];
                System.out.println("The sum is equal to :" + Double.toString(sum));
                
            }
            else{
                System.out.println("Erreur: le fichier "+args[0]+" n'a pas put être traduit en matrice.");
            }
        }
        else{
            System.out.println("Attention: ");
            System.out.println("ce programme necessite qu'on lui fournisse le nom du fichier texte contenant les données");
        }
    }
    public static double[][] fileToTable(String filename){
        try{
            Scanner scan = new Scanner(new FileReader(filename));
            ArrayList array = new ArrayList<String>();
            
            int i;
            for(i=0; scan.hasNextLine(); i++){
                array.add(scan.nextLine());
            }
            
            scan.close();
            
            return arrayToTable(array, i);
        }
        catch(Exception e){
            System.out.println("Erreur: "+e);
            return null;
        }
    }
    
    public static double[][] arrayToTable(ArrayList<String> array, int length){
        double[][] table = new double[length][length];
        
        String tmp;
        for(int i = 0; i<length ; i++){
            tmp = array.get(i);
            String[] tmp2 = tmp.split(",");
            if(containsJustZero(tmp2)){
                tmp2 = replaceValues(tmp2);
            }
            for(int j=0;j<tmp2.length;j++){
                table[i][j] = Double.valueOf(tmp2[j]);
            }
        }
        
        return table;
    }
    
    public static double[] getDegreeVector(double[][] adjacencyMatrice){
        double[] dV = new double[adjacencyMatrice.length]; //vector of the same size of the adjacency Matrice
        
        for(int i=0; i<adjacencyMatrice.length ; i++){
            double sum = 0;
            for(int j=0; j<adjacencyMatrice[i].length ; j++){
                sum += adjacencyMatrice[i][j];
            }
            dV[i] = sum;
        }
        return dV;
    }
    
    public static Matrix getLeapProbabilities(int length, double teleportaionParameter){
        Matrix tmp = new Matrix(length,length,1.0); //create a new matrice of size = length filled with 1
        tmp = tmp.times((1.0 - teleportaionParameter)/length); //scalar multiplication 
        return tmp;
    }
    
    public  static Matrix getLinkProbabilities(double[][] adjacencyMatrice, double[] degreeVector, double teleportationParameter){
        double value = teleportationParameter;
        for(int i=0; i< adjacencyMatrice.length;i++){
            for(int j=0; j<adjacencyMatrice[i].length;j++){
                adjacencyMatrice[i][j] = (adjacencyMatrice[i][j]*value)/degreeVector[i];
            }
        }
        //vérifier si une ligne est composée uniquement de 0 dans ce cas remplacer les valeurs par 1/length -> correction de l'erreur dans Coleman
        return new Matrix(adjacencyMatrice);
    }
    
    public static boolean containsJustZero(String[] table){
        for(int i=0; i<table.length;i++){
            //System.out.print(table[i]+", ");
            if(!table[i].equals("0")){
                //System.out.println();
                return false;
            }
        }
        //System.out.println();
        return true;
    }
    
    //remplace les valeurs d'un vecteur par 1/length ou length est la longueur du vecteur
    public static String[] replaceValues(String[] table){
        double value = 1/((double)table.length);
        String s = Double.toString(value);
        //System.out.print(s);
        for(int i = 0;i<table.length;i++){
            table[i]=s;
        }
        return table;
    }
    
    public static void afficheMatrix(Matrix m){
        System.out.println("");
        for(int i=0; i<m.getRowDimension();i++){
            for(int j=0; j<m.getColumnDimension();j++){
                System.out.print(Double.toString(m.get(i,j))+" ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    public static boolean verify(Matrix m){
        double sum2 = 0;
        for(int i=0; i<m.getRowDimension();i++){
            double sum = 0;
            for(int j=0; j<m.getColumnDimension();j++){
                sum +=m.get(i,j);
                if(j==m.getColumnDimension()-1 && sum !=1.0){
                    System.out.println(sum+" ");
                }
            }
            if(i==m.getRowDimension() && sum2 != 1.0){
                System.out.print(sum2+" ");
            }
        }
        return true;
    }
}
