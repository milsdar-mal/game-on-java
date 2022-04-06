public class Pole {
    public static int[][][] lab = new int[25][25][2];
    /*public static void autoTest () {
        lab[2][4] = 2;
        lab[2][5] = 2;
        lab[2][6] = 2;
        lab[2][7] = 2;
        lab[2][8] = 2;
        lab[14][4] = 1;
        lab[14][5] = 1;
        lab[14][6] = 1;
        lab[14][7] = 1;
        lab[14][8] = 1;
        lab[5][11] = 3;
        lab[6][11] = 3;
        lab[7][11] = 3;
        lab[8][11] = 3;
        lab[9][11] = 3;
    }*/
    public static void Clearing () {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                lab[i][j][0] = 0;
            }
        }
    }
}
