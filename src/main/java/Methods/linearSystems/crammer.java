package Methods.linearSystems;

import Methods.utilities.matrixUtilities.MatrixUtilities;

public class crammer {
    public static double[] crammerMethod(double[][] A, double[] B) throws Exception {
        if (MatrixUtilities.determinant(A) == 0)
            throw new Exception("Determinant error");
        else {
            double[] res = new double[A.length];
            for (int i = 0; i < res.length; i++) {
                // construct Ai
                double[][] Ai = new double[A.length][A.length];
                for (int line = 0; line < A.length; line++)
                    for (int column = 0; column < A.length; column++)
                        Ai[line][column] = (column == i) ? B[line] : A[line][column];

                for (int k = 0; k < Ai.length; k++) {
                    for (int j = 0; j < Ai[i].length; j++) {
                        System.out.print(Ai[k][j] + " ");
                    }
                    System.out.println();
                }
                System.out.println(MatrixUtilities.determinant(Ai));
                System.out.println();

                res[i] = MatrixUtilities.determinant(Ai) / MatrixUtilities.determinant(A);
            }
            return res;
        }
    }

    // public static void main(String...args) throws Exception
    // {
    // double[][] A = {{-1, 4, 3}, {0, 2, 2}, {1, -3, 5}};
    // double[] B = {2, 1, 0};
    // double[] res = crammerMethod(A, B);

    // double[][] A1 = {{2, 1, 0}, {0, 2, 2}, {1, -3, 5}};
    // double[][] A2 = {{-1, 4, 3},{2, 1, 0}, {1, -3, 5}};
    // double[][] A3 = {{-1, 4, 3}, {0, 2, 2}, {2, 1, 0}};
    // System.out.println(MatrixUtilities.determinant(A1));
    // System.out.println(MatrixUtilities.determinant(A2));
    // System.out.println(MatrixUtilities.determinant(A3));
    // }
}
