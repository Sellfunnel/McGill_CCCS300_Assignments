import java.util.Scanner;

public class RandomNumberGenerator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Input for min and max values
        System.out.print("Enter the minimum value: ");
        int min = Integer.parseInt(args[0]);
        System.out.print("Enter the maximum value: ");
        int max = Integer.parseInt(args[1]);;

        // Generate and display a random integer between min and max
        int randomValue = (int) (Math.random() * (max - min + 1) + min);
        System.out.println("Random Integer between " + min + " and " + max + ": " + randomValue);

        // Close the scanner
        input.close();
    }
}
