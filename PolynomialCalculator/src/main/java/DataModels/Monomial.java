package DataModels;

import java.text.DecimalFormat;

public class Monomial implements Comparable<Monomial> {
    private int degree;
    private double coefficient;

    public Monomial() {
        this.coefficient = 0;
        this.degree = 0;
    }

    public Monomial(String degree, String coefficient) {
        if (coefficient == null) {
            this.coefficient = 1;
        } else {
            this.coefficient = Double.parseDouble(coefficient);
        }
        if (degree == null) {
            this.degree = 0;
        } else {
            this.degree = Integer.parseInt(degree);
        }
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getDegree() {
        return degree;
    }

    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public String toString() {
        if (this.degree == 0 && this.coefficient == 0)
            return String.valueOf(0);

        DecimalFormat format = new DecimalFormat("#.##");
        String coefficientString = format.format(this.coefficient);
        if (this.degree == 0)
            return coefficientString;
        else {
            if (this.degree == 1) {
                if (this.coefficient == 1)
                    return "x";
                else if (this.coefficient == -1)
                    return "-x";
                else
                    return coefficientString + "*x";

            } else {
                if (this.coefficient == 1)
                    return "x^" + this.degree;
                else if (this.coefficient == -1)
                    return "-x^" + this.degree;
                else
                    return coefficientString + "*x^" + this.degree;
            }
        }
    }

    @Override
    public int compareTo(Monomial o) {
        return this.degree - o.getDegree();
    }
}