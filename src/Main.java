import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Operations o = new Operations();
        int ch;
        do {
            System.out.println("1. Rent A Camera");
            System.out.println("2. Add / Remove Camera");
            System.out.println("3. Rent A Camera");
            System.out.println("4. My Wallet");
            System.out.println("5. Exit");
            switch (sc.nextInt()) {
                case 1:

                    break;
                case 2:
                    o.getFullList();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
            System.out.println("Press 1 to continue");
            ch = sc.nextInt();
        } while (ch == 1);

    }
}
