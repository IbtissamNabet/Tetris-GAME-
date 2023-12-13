package Modele;

/**
 * L'énumération Formes représente les différentes formes de pièces dans le jeu Tetris.
 * Chaque forme est définie par un tableau bidimensionnel représentant sa configuration,
 * ainsi qu'un code couleur associé.
 */
public enum Formes {
    I(new int[][]{{0,1}, {0,1}, {0,1}, {0,1}},1),
    L(new int[][]{{0, 0, 1}, {1, 1, 1}},2),
    J(new int[][]{{1, 0, 0}, {1, 1, 1}},3),
    O(new int[][]{{1, 1}, {1, 1}},4),
    S(new int[][]{{0, 1, 1}, {1, 1, 0}},5),
    T(new int[][]{{0, 1, 0}, {1, 1, 1}},6),
    Z(new int[][]{{1, 1, 0}, {0, 1, 1}},7);
    public int[][] type;
    private  int codeCouleur;

    /**
     * Constructeur de l'énumération Formes.
     * @param type_ Le tableau bidimensionnel représentant la configuration de la forme.
     * @param codeCouleur_ Le code couleur associé à la forme.
     */
    Formes(int[][] type_,int codeCouleur_) {
        this.type = type_;
        codeCouleur=codeCouleur_;
    }

     /**
     * Obtient le code couleur de la forme.
     * @return Le code couleur de la forme.
     */  
    public int getCodeCouleur() {
        return codeCouleur;
    }

}

