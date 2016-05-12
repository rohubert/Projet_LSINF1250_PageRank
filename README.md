# Projet_LSINF1250_PageRank

Algorithme et fonction main fonctionne.
Un cas particulier à traiter lorsqu'une ligne de la matrice de base est uniquement composée de 0 (exemple de Coleman.txt).
dans ce cas là, lors de la création de la Matrice LinkProbabilities, remplacer les valeurs de cette ou ces lignes par 1/length

--> To do: implémenter la prise en charge de ce cas particulier et faire un petit nettoyage du code pour l'aérer.
--> To do (bis): prendre en compte le fait de passer en paramètre de la fonction main: le paramètre de téléportation compris entre 0,8 et 0,9 s'il est précisé. Sinon valeur par défaut: 0,9. 
--> To do (bis2): si le fichier texte n'est pas précisé en paramètre de Main, lui en fournir un par défaut.

J'ai importé la librairie JAMA pour utiliser des fonctions permettant d'implémenter des objets Matrix qui représentent des matrices.
J'ai commenté le code et donné des noms de variables assez explicite en suivant les explications de cette adresse:
http://introcs.cs.princeton.edu/java/16pagerank/

Actuellement, pour lancer le code, il faut lancer la Classe Main avec pour paramètre le nom du fichier à lire.
ex avec BlueJ: {"Bott.txt"}
