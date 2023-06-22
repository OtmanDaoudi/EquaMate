package Methods.rootFinding;

import net.objecthunter.exp4j.Expression;

public class secant {
    public static double Secant(Expression f, double x0, double x1, int iteration, double error) throws Exception {
        if (iteration == 0)
            return x1;

        double x2 = x1 - (f.setVariable("x", x1).evaluate() * (x1 - x0))
                / (f.setVariable("x", x1).evaluate() - f.setVariable("x", x0).evaluate());
        if (Math.abs(x1 - x2) <= error)
            return x2;
        else if (iteration == 1)
            throw new Exception("Secant doesn't converge");
        else
            return Secant(f, x1, x2, iteration - 1, error);
    }
}