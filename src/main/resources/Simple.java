import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        String args1;
        args1 = "a";

        int a;
        a = 2;

        int b = 3;

        if (a < b)
            System.out.println("Menor");


        while (a < 5) {
            System.out.println("a");
            a++;
        }

        Scanner sc = new Scanner(System.in);
    }
}
