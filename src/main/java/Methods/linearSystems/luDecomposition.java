package Methods.linearSystems;

import Methods.utilities.matrixUtilities;

import java.util.HashMap;

public class luDecomposition {
    public static HashMap<String, Object> LU(double[][] A, double[] B) {
        double[][] L = new double[A.length][A.length];
        double[][] U = new double[A.length][A.length];

        // initialise L
        for (int l = 0; l < A.length; l++) {
            for (int c = 0; c < A.length; c++) {
                if (l == c)
                    L[l][c] = 1;
                else
                    L[l][c] = 0;
            }
        }

        // copy A into U
        for (int l = 0; l < A.length; l++)
            for (int c = 0; c < A.length; c++)
                U[l][c] = A[l][c];

        // construct U & L
        for (int column = 0; column < A.length; column++) {
            for (int line = column + 1; line < A.length; line++)
                if (A[line][column] != 0)
                    L[line][column] = -1 * matrixUtilities.MatrixUtilities.eliminateEntry(U, null, line, column);
        }

        double[] c = matrixUtilities.MatrixUtilities.backSolveReversed(L, B);

        double[] result = matrixUtilities.MatrixUtilities.backSolve(U, c);
        HashMap<String, Object> res = new HashMap<>();
        res.put("result", result);
        res.put("L", L);
        res.put("U", U);
        return res;
    }

    public static void main(String... args) throws Exception {
        double[][] A = { { 2, 4, 3 }, { 1, 2, 2 }, { 0, -3, 5 } };
        double[] B = { 2, 1, 0 };
        double[][] res = (double[][]) LU(A, B).get("L");
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
    }
}