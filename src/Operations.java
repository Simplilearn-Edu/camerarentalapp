import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    private List<Camera> getCameraList(String filename) {
        File cameraFile = new File(filename);
        List<Camera> cameras = new ArrayList<>();
        Scanner file_reader = null;
        try {
            file_reader = new Scanner(cameraFile);
            while (file_reader.hasNextLine()) {
                String row[] = file_reader.nextLine().split(",");
                cameras.add(new Camera(
                        Integer.parseInt(row[0]),
                        row[1],
                        row[2],
                        Integer.parseInt(row[3]),
                        row[4].charAt(0)));
            }
            return cameras;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void getFullList() {
        fullList = getCameraList("src/camera.txt");
        if (fullList.size() > 0) {
            for (Camera c : fullList) {
                System.out.println("Camera ID           - " + c.getId());
                System.out.println("Brand               - " + c.getBrand());
                System.out.println("Model               - " + c.getModel());
                System.out.println("Price(Per Day) INR. - " + c.getPrice_per_day());
                System.out.println("Status              - " + c.getStatus());
                System.out.println("================================================================");
            }
        } else {
            System.out.println("No Data Present At This Moment.");
        }
    }

    private void myListing() {
        myList = getCameraList("src/mycamera.txt");
        if (myList.size() > 0) {
            for (Camera c : myList) {
                System.out.println("Camera ID           - " + c.getId());
                System.out.println("Brand               - " + c.getBrand());
                System.out.println("Model               - " + c.getModel());
                System.out.println("Price(Per Day) INR. - " + c.getPrice_per_day());
                System.out.println("Status              - " + c.getStatus());
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

        File allcameras = new File("src/camera.txt");
        File mycameras = new File("src/mycamera.txt");
        try {
            FileWriter fw1 = new FileWriter(allcameras);
            FileWriter fw2 = new FileWriter(mycameras);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Camera camera = new Camera(fullList.size() + 101, brand, model, per_day_price);
        myList.add(camera);
        fullList.add(camera);
    }

    private void removeCamera() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Camera Name to delete - ");
        sc.nextLine();
    }
}
