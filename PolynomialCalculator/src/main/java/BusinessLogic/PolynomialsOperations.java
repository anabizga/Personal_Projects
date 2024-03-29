package BusinessLogic;

import DataModels.Monomial;
import DataModels.Polynomial;

public class PolynomialsOperations {

    private Polynomial polynom1;
    private Polynomial polynom2;
    private Polynomial result;

    public PolynomialsOperations(Polynomial polynom1, Polynomial polynom2) {
        this.polynom1 = polynom1;
        this.polynom2 = polynom2;
        this.result = new Polynomial();
    }

    public void add() {
        if (this.polynom1 == null)
            throw new IllegalArgumentException("The first polynomial is null.");
        if (this.polynom2 == null)
            throw new IllegalArgumentException("The second polynomial is null.");
        result.setMonomials(polynom1.getMonomials());

        for (Monomial monom2 : this.polynom2.getMonomials().values()) {
            if (this.result.getMonomials().containsKey(monom2.getDegree())) {
                Monomial monom1 = this.result.getMonomials().get(monom2.getDegree());
                Monomial resultPart = MonomialsOperations.add(monom1, monom2);
                this.result.getMonomials().put(resultPart.getDegree(), resultPart);
            } else this.result.getMonomials().put(monom2.getDegree(), monom2);
        }
    }

    public void substract() {
        if (this.polynom1 == null)
            throw new IllegalArgumentException("The first polynomial is null.");
        if (this.polynom2 == null)
            throw new IllegalArgumentException("The second polynomial is null.");
        result.setMonomials(polynom1.getMonomials());

        for (Monomial monom2 : this.polynom2.getMonomials().values()) {
            if (this.result.getMonomials().containsKey(monom2.getDegree())) {
                Monomial monom1 = this.result.getMonomials().get(monom2.getDegree());
                Monomial resultPart = MonomialsOperations.substract(monom1, monom2);
                this.result.getMonomials().put(resultPart.getDegree(), resultPart);
            } else {
                monom2.setCoefficient((-1) * monom2.getCoefficient());
                this.result.getMonomials().put(monom2.getDegree(), monom2);
            }
        }
    }

    public void multiply() {
        if (this.polynom1 == null)
            throw new IllegalArgumentException("The first polynomial is null.");
        if (this.polynom2 == null)
            throw new IllegalArgumentException("The second polynomial is null.");

        this.polynom1.deleteZeros();
        this.polynom1.deleteZeros();
        if (this.polynom1.getMonomials().isEmpty() || this.polynom2.getMonomials().isEmpty()) {
            this.result.getMonomials().put(0, new Monomial());
            return;
        }

        for (Monomial term1 : this.polynom1.getMonomials().values()) {
            for (Monomial term2 : this.polynom2.getMonomials().values()) {
                Monomial monom1 = MonomialsOperations.multiply(term1, term2);
                if (this.result.getMonomials().containsKey(monom1.getDegree())) {
                    Monomial monom2 = this.result.getMonomials().get(monom1.getDegree());
                    Monomial resultPart = MonomialsOperations.add(monom1, monom2);
                    this.result.getMonomials().put(resultPart.getDegree(), resultPart);
                } else this.result.getMonomials().put(monom1.getDegree(), monom1);
            }
        }
    }

    public Polynomial[] divide() {
        if (this.polynom1 == null)
            throw new IllegalArgumentException("The first polynomial is null.");
        if (this.polynom2 == null)
            throw new IllegalArgumentException("The second polynomial is null.");

        this.polynom1.deleteZeros();
        if (this.polynom1.getMonomials().isEmpty()) {
            Polynomial[] result = new Polynomial[2];
            result[0] = new Polynomial();
            result[1] = new Polynomial();
            return result;
        }

        this.polynom2.deleteZeros();
        if (this.polynom2.getMonomials().isEmpty())
            throw new IllegalArgumentException("Division by zero.");

        Polynomial q = new Polynomial();
        Polynomial r = new Polynomial(polynom1.getMonomials());

        while (!r.getMonomials().isEmpty() && r.getDegree() >= this.polynom2.getDegree()) {
            Monomial lead1 = r.getMonomials().get(r.getDegree());
            Monomial lead2 = this.polynom2.getMonomials().get(this.polynom2.getDegree());
            Monomial t = MonomialsOperations.divide(lead1, lead2);

            if (q.getMonomials().containsKey(t.getDegree())) {
                Monomial monomial = q.getMonomials().get(t.getDegree());
                Monomial resultPart = MonomialsOperations.add(monomial, t);
                q.getMonomials().put(resultPart.getDegree(), resultPart);
            } else q.getMonomials().put(t.getDegree(), t);

            PolynomialsOperations opMultiply = new PolynomialsOperations(new Polynomial(t), this.polynom2);
            opMultiply.multiply();
            PolynomialsOperations opSubstract = new PolynomialsOperations(r, opMultiply.getResult());
            opSubstract.substract();
            r = opSubstract.getResult();

            r.deleteZeros();
        }

        Polynomial[] result = new Polynomial[2];
        result[0] = q;
        result[1] = r;
        return result;
    }

    public void differentiateFirstPolynomial() {
        if (this.polynom1 == null)
            throw new IllegalArgumentException("The first polynomial is null.");

        for (Monomial monom : this.polynom1.getMonomials().values()) {
            Monomial resultPart = MonomialsOperations.differentiate(monom);
            this.result.getMonomials().put(resultPart.getDegree(), resultPart);
        }

    }

    public void differentiateSecondPolynomial() {
        if (this.polynom2 == null)
            throw new IllegalArgumentException("The first polynomial is null.");

        for (Monomial monom : this.polynom2.getMonomials().values()) {
            Monomial resultPart = MonomialsOperations.differentiate(monom);
            this.result.getMonomials().put(resultPart.getDegree(), resultPart);
        }
    }

    public void integrateFirstPolynomial() {
        if (this.polynom1 == null)
            throw new IllegalArgumentException("The first polynomial is null.");

        for (Monomial monom : this.polynom1.getMonomials().values()) {
            Monomial resultPart = MonomialsOperations.integrate(monom);
            this.result.getMonomials().put(resultPart.getDegree(), resultPart);
        }
    }

    public void integrateSecondPolynomial() {
        if (this.polynom2 == null)
            throw new IllegalArgumentException("The first polynomial is null.");

        for (Monomial monom : this.polynom2.getMonomials().values()) {
            Monomial resultPart = MonomialsOperations.integrate(monom);
            this.result.getMonomials().put(resultPart.getDegree(), resultPart);
        }
    }

    public Polynomial getResult() {
        return result;
    }
}
