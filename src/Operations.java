import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Operations {
    private List<Camera> fullList = new ArrayList<>();
    private List<Camera> myList = new ArrayList<>();

    public void getFullList() {
        if (fullList.size() > 0) {
            for (Camera c : fullList) {
                System.out.println(c.getBrand());
                System.out.println(c.getModel());
                System.out.println(c.getPrice_per_day());
            }
        } else {
            System.out.println("No Data Present At This Moment.");
        }
    }

    public void add_remove_camera() {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("1. Add\n2. Remove\n3. Go to previous menu");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addCamera();
                    break;
                case 2:
                    removeCamera();
                    break;
                case 3:
                    return;
            }
//            sc.close();
        } while (true);
    }

    private void addCamera() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Brand - ");
        String brand = sc.nextLine();
        System.out.print("Enter the Model - ");
        String model = sc.nextLine();
        System.out.print("Enter the Per Day Price - ");
        int per_day_price = sc.nextInt();

        Camera camera = new Camera(brand, model, per_day_price);
        myList.add(camera);
        fullList.add(camera);
        sc.close();
    }

    private void removeCamera() {
    }
}
