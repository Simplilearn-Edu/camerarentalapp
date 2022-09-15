import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Operations o = new Operations();
        int ch;
        do {
            System.out.println("1. Add / Remove Camera");
            System.out.println("2. Rent A Camera");
            System.out.println("3. View All Cameras");
            System.out.println("4. My Wallet");
            System.out.println("5. Exit");
            switch (sc.nextInt()) {
                case 1:
                    o.add_remove_camera();
                    break;
                case 2:

                    break;
                case 3:
                    o.getFullList();
                    break;
                case 4:
                    o.myWallet();
                    break;
                case 5:
                    System.out.println("Thanks Visit again");
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
//            System.out.println("Press 1 to continue");
//            ch = sc.nextInt();
        } while (true);
    }
}
