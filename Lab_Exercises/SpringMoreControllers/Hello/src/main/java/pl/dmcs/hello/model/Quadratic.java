package pl.dmcs.hello.model;


public class Quadratic {
    private String rawA, rawB, rawC;

    public void setRawA(String rawA) {
        this.rawA = rawA;
    }

    public void setRawB(String rawB) {
        this.rawB = rawB;
    }

    public void setRawC(String rawC) {
        this.rawC = rawC;
    }

    public String getRawA() {
        return rawA;
    }

    public String getRawB() {
        return rawB;
    }

    public String getRawC() {
        return rawC;
    }

    public String evaluate(){
        double a, b, c;
//        System.out.println("The program evaluates the real roots of quadratic equation if the form ax^2 + bx + c = 0");

        try {
            a = Double.parseDouble(rawA);
            b = Double.parseDouble(rawB);
            c = Double.parseDouble(rawC);        
        } catch (Exception e) {
            return "NaN";
        }

        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    return "Tautology";
                }
                else{
                    return "Contradiction";
                }
            }
            else {
                return "x = " + -c/b;
            }
        }
        else{
            double delta = b*b - 4*a*c;
            if (delta < 0) {
                return "No real solutions";
            }
            if (delta == 0) {
                return "x = " + -b/(2*a);
            }
            if (delta > 0) {
                return "x_1 = " + (-b + Math.sqrt(delta))/(2*a) + "\n" + "x_2 = " + (-b - Math.sqrt(delta))/(2*a);
            }
        }
        return "Bad Error";
    }
    
}