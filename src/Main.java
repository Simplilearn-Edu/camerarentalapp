import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("+--------------------------------+");
        System.out.println("|  WELCOME TO CAMERA RENTAL APP  |");
        System.out.println("+--------------------------------+");

        System.out.println("PLEASE LOGIN TO CONTINUE - ");
        Operations o = new Operations();
        try {
            if (o.login()) {
                do {
                    System.out.println("1. ADD / REMOVE CAMERA");
                    System.out.println("2. RENT A CAMERA");
                    System.out.println("3. VIEW ALL CAMERAS");
                    System.out.println("4. MY WALLET");
                    System.out.println("5. EXIT");
                    try {
                        switch (sc.nextInt()) {
                            case 1:
                                o.add_remove_camera();
                                break;
                            case 2:
                                o.rent();
                                break;
                            case 3:
                                o.getFullList();
                                break;
                            case 4:
                                o.myWallet();
                                break;
                            case 5:
                                System.out.println("THANKS VISIT AGAIN.");
                                return;
                            default:
                                System.out.println("ERROR : INVALID CHOICE. PLEASE SELECT THE OPTION BETWEEN 1 TO 5.");
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR : INVALID INPUT.");
                        break;
                    }
                } while (true);
            } else {
                System.out.println("ERROR : LOGIN FAILURE! INVALID CREDENTIALS.");
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }

    }
}
