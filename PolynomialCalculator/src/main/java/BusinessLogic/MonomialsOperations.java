package BusinessLogic;

import DataModels.Monomial;

public class MonomialsOperations {

    public static Monomial add(Monomial monomial1, Monomial monomial2) {
        if (monomial1 == null)
            throw new IllegalArgumentException("The first monomial is null.");
        if (monomial2 == null)
            throw new IllegalArgumentException("The second monomial is null.");

        if (monomial1.getDegree() != monomial2.getDegree())
            throw new IllegalArgumentException("The monomials do not have the same degree");

        monomial1.setCoefficient(monomial1.getCoefficient() + monomial2.getCoefficient());
        return monomial1;
    }

    public static Monomial substract(Monomial monomial1, Monomial monomial2) {
        if (monomial1 == null)
            throw new IllegalArgumentException("The first monomial is null.");
        if (monomial2 == null)
            throw new IllegalArgumentException("The second monomial is null.");

        if (monomial1.getDegree() != monomial2.getDegree())
            throw new IllegalArgumentException("The monomials do not have the same degree");

        monomial1.setCoefficient(monomial1.getCoefficient() - monomial2.getCoefficient());
        return monomial1;
    }

    public static Monomial multiply(Monomial monomial1, Monomial monomial2) {
        if (monomial1 == null)
            throw new IllegalArgumentException("The first monomial is null.");
        if (monomial2 == null)
            throw new IllegalArgumentException("The second monomial is null.");

        Monomial result = new Monomial();
        result.setCoefficient(monomial1.getCoefficient() * monomial2.getCoefficient());
        result.setDegree(monomial1.getDegree() + monomial2.getDegree());
        return result;
    }

    public static Monomial divide(Monomial monomial1, Monomial monomial2) {
        if (monomial1 == null)
            throw new IllegalArgumentException("The first monomial is null.");
        if (monomial2 == null)
            throw new IllegalArgumentException("The second monomial is null.");

        if (monomial2.getCoefficient() == 0)
            throw new IllegalArgumentException("Division by zero.");

        Monomial result = new Monomial();
        if (monomial1.getCoefficient() == 0) {
            result.setCoefficient(0);
            result.setDegree(0);
        } else {
            result.setCoefficient(monomial1.getCoefficient() / monomial2.getCoefficient());
            result.setDegree(monomial1.getDegree() - monomial2.getDegree());
        }
        return result;
    }

    public static Monomial differentiate(Monomial monom) {

        if (monom.getDegree() >= 1) {
            monom.setCoefficient(monom.getCoefficient() * monom.getDegree());
            monom.setDegree(monom.getDegree() - 1);
        } else {
            monom.setCoefficient(0);
        }
        return monom;

    }

    public static Monomial integrate(Monomial monom) {
        if (monom.getDegree() == 0 && monom.getCoefficient() == 0) {
            return monom;
        }
        monom.setDegree(monom.getDegree() + 1);
        monom.setCoefficient(monom.getCoefficient() / monom.getDegree());
        return monom;
    }

}
