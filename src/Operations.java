import java.io.*;
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

        fullList = getCameraList("src/camera.txt");
        myList = getCameraList("src/mycamera.txt");
        int id = 0;
        if (fullList.size() > 0)
            id = fullList.get(fullList.size() - 1).getId();
        Camera camera = new Camera(id + 1, brand, model, per_day_price);
        myList.add(camera);
        fullList.add(camera);

        File allcameras = new File("src/camera.txt");
        File mycameras = new File("src/mycamera.txt");
        try {
            FileWriter fw1 = new FileWriter(allcameras, true);
            FileWriter fw2 = new FileWriter(mycameras, true);
            if ((fullList.size() > 1) && (myList.size() > 1)) {
                fw1.write(System.lineSeparator() + camera.toString());
                fw2.write(System.lineSeparator() + camera.toString());
            } else if ((fullList.size() > 1) && (myList.size() == 1)) {
                fw1.write(System.lineSeparator() + camera.toString());
                fw2.write(camera.toString());
            } else if ((fullList.size() == 1) && (myList.size() > 1)) {
                fw1.write(camera.toString());
                fw2.write(System.lineSeparator() + camera.toString());
            } else {
                fw1.write(camera.toString());
                fw2.write(camera.toString());
            }
            fw1.close();
            fw2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void removeCamera() {
        try {
            Scanner sc = new Scanner(System.in);
            fullList = getCameraList("src/camera.txt");
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
                System.out.print("Enter the Camera ID to delete - ");
                int camera_id = sc.nextInt();
                if (myList.removeIf(n -> (n.getId() == camera_id))) {
                    fullList.removeIf(n -> (n.getId() == camera_id));

                    File allcameras = new File("src/camera.txt");
                    File mycameras = new File("src/mycamera.txt");
                    FileWriter fw1 = new FileWriter(allcameras);
                    FileWriter fw2 = new FileWriter(mycameras);

                    String my_cameras = "";
                    String all_cameras = "";

                    for (Camera c : myList) {
                        my_cameras += (c.toString() + System.lineSeparator());
                    }
                    fw2.write(my_cameras);
                    for (Camera c : fullList) {
                        all_cameras += (c.toString() + System.lineSeparator());

                    }
                    fw1.write(all_cameras);

                    fw1.close();
                    fw2.close();
                    System.out.println("Camera successfully removed from the list.");
                } else {
                    System.out.println("Incorrect Camera ID.");
                }
            } else {
                System.out.println("No Camera Found In Your List.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void myWallet() {
        try {
            Scanner sc = new Scanner(System.in);
            File file = new File("src/wallet.txt");
            Scanner reader = new Scanner(file);

            int wallet_balance = 0;
            if (reader.hasNext()) {
                wallet_balance = Integer.parseInt(reader.next());
            }
            reader.close();
            System.out.println("Your Current Wallet Balance is - INR." + wallet_balance);
            System.out.print("Do you want to add more amount to your wallet?(1.Yes 2.No) - ");
            switch (sc.nextInt()) {
                case 1:
                    System.out.print("Enter the amount - ");
                    int amount = sc.nextInt();
                    if (amount > 0) {
                        wallet_balance += amount;
                        FileWriter fw = new FileWriter(file);
                        fw.write(wallet_balance+"");
                        fw.close();
                        System.out.println("Your wallet balance updated successfully. Current Wallet Balance - INR."+wallet_balance);
                    } else {
                        System.out.println("Invalid Amount - " + amount);
                    }
                case 2:
                    return;
                default:
                    System.out.println("Invalid Operation");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.getMessage();
        }
    }
}


