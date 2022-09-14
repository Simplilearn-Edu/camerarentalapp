import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Operations {
    private List<Camera> fullList = new ArrayList<>();
    private List<Camera> myList = new ArrayList<>();

    public void add_remove_camera() {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("1. Add\n2. Remove\n3. My Cameras\n4. Go to previous menu");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addCamera();
                    break;
                case 2:
                    removeCamera();
                    break;
                case 3:
                    myListing();
                    break;
                case 4:
                    return;
            }
        } while (true);
    }

    public void getFullList() {
        if (fullList.size() > 0) {
            for (Camera c : fullList) {
                System.out.println("Camera ID           - "+c.getId());
                System.out.println("Brand               - "+c.getBrand());
                System.out.println("Model               - "+c.getModel());
                System.out.println("Price(Per Day) INR. - "+c.getPrice_per_day());
                System.out.println("Status              - "+c.getStatus());
                System.out.println("================================================================");
            }
        } else {
            System.out.println("No Data Present At This Moment.");
        }
    }
    private void myListing() {
        if (myList.size() > 0) {
            for (Camera c : myList) {
                System.out.println("Camera ID           - "+c.getId());
                System.out.println("Brand               - "+c.getBrand());
                System.out.println("Model               - "+c.getModel());
                System.out.println("Price(Per Day) INR. - "+c.getPrice_per_day());
                System.out.println("Status              - "+c.getStatus());
                System.out.println("================================================================");
            }
        } else {
            System.out.println("No Data Present At This Moment.");
        }
    }

    private void addCamera() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Brand - ");
        String brand = sc.nextLine();
        System.out.print("Enter the Model - ");
        String model = sc.nextLine();
        System.out.print("Enter the Per Day Price - ");
        int per_day_price = sc.nextInt();

        Camera camera = new Camera(myList.size() + 101, brand, model, per_day_price);
        myList.add(camera);
        fullList.add(camera);
    }

    private void removeCamera() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Camera Name to delete - ");
        sc.nextLine();


    }
}
