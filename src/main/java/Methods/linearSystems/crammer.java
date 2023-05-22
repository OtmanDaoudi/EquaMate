package Methods.linearSystems;

import Methods.utilities.matrixUtilities.MatrixUtilities;

public class crammer 
{
    public static double[] crammerMethod(double[][] A, double[] B) throws Exception
    {
        if(MatrixUtilities.determinant(A) == 0) throw new Exception("Determinant error");
        else
        {
            double[] res = new double[A.length]; 
            for(int i=0; i<res.length; i++)
            {
                //construct Ai
                double[][] Ai = new double[A.length][A.length]; 
                for(int line=0; line < A.length; line++)
                    for(int column=0; column < A.length ; column++)
                        Ai[line][column] = (column == i) ?  B[line] : A[line][column];
                res[i] = MatrixUtilities.determinant(Ai) / MatrixUtilities.determinant(A);
            }
            return res; 
        }
    }
}
