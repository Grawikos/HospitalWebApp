import java.util.Scanner;

public class QuadraticEvaluator {
    public static void main(String[] Args){
        String rawA, rawB, rawC;
        double a, b, c;
        Scanner scanner = new Scanner(System.in);
        System.out.println("The program evaluates the real roots of quadratic equation if the form ax^2 + bx + c = 0");
        System.out.println("a: ");
        rawA = scanner.nextLine();
        System.out.println("b: ");
        rawB = scanner.nextLine();
        System.out.println("c: ");
        rawC = scanner.nextLine();
        scanner.close();

        try {
            a = Double.parseDouble(rawA);
            b = Double.parseDouble(rawB);
            c = Double.parseDouble(rawC);        
        } catch (Exception e) {
            System.out.println("NaN");
            return;
        }
        System.out.println(b);

        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    System.out.println("Tautology");
                }
                else{
                    System.out.println("Contradiction");
                }
            }
            else {
                System.out.println("x = " + -c/b);
            }
        }
        else{
            double delta = b*b - 4*a*c;
            if (delta < 0) {
                System.out.println("No real solutions");
            }
            if (delta == 0) {
                System.out.println("x = " + -b/(2*a));
            }
            if (delta > 0) {
                System.out.println("x_1 = " + (-b + Math.sqrt(delta))/(2*a));
                System.out.println("x_2 = " + (-b - Math.sqrt(delta))/(2*a));
            }
        }
    }
    
}