package Modele;

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

        //constructeur de l'enum
        Formes(int[][] type_,int codeCouleur_) {
            this.type = type_;
            codeCouleur=codeCouleur_;
        }
        public int getCodeCouleur() {
            return codeCouleur;
    }

    }

