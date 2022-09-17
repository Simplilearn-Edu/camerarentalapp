import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Operations {
    private static final String WALLET = "src/wallet.txt";
    private static final String CREDENTIAL = "src/credentials.txt";
    private static final String MY_CAMERA = "src/mycamera.txt";
    private static final String ALL_CAMERAS = "src/camera.txt";
//    private List<Camera> fullList = new ArrayList<>();
//    private List<Camera> myList = new ArrayList<>();

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

    public void displayCameraList(List<Camera> cameraList) {
        if (cameraList.size() > 0) {
            System.out.println("===========================================================================");
            System.out.printf("%5s %10s %12s %20s %12s", "CAMERA ID", "BRAND", "MODEL", "PRICE(PER DAY)", "STATUS");
            System.out.println();
            System.out.println("===========================================================================");
            for (Camera c : cameraList) {
                System.out.format("%5s %14s %11s %12s %22s", c.getId(), c.getBrand(), c.getModel(), c.getPrice_per_day(), c.getStatus() == 'a' ? "Available" : "Rented");
                System.out.println();
            }
            System.out.println("===========================================================================");
        } else {
            System.out.println("No Data Present At This Moment.");
        }
    }

    public void getFullList() {
//        fullList = getCameraList(ALL_CAMERAS);
        displayCameraList(getCameraList(ALL_CAMERAS));
    }

    private void myListing() {
        displayCameraList(getCameraList(MY_CAMERA));
        /*myList = getCameraList(MY_CAMERA);
        if (myList.size() > 0) {
            System.out.println("===========================================================================");
            System.out.printf("%5s %10s %12s %20s %12s", "CAMERA ID", "BRAND", "MODEL", "PRICE(PER DAY)", "STATUS");
            System.out.println();
            System.out.println("===========================================================================");
            for (Camera c : myList) {
                System.out.format("%5s %14s %11s %12s %22s", c.getId(), c.getBrand(), c.getModel(), c.getPrice_per_day(), c.getStatus() == 'a' ? "Available" : "Rented");
                System.out.println();
            }
            System.out.println("===========================================================================");
        } else {
            System.out.println("No Data Present At This Moment.");
        }*/
    }

    private void addCamera() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Brand - ");
        String brand = sc.nextLine();
        System.out.print("Enter the Model - ");
        String model = sc.nextLine();
        System.out.print("Enter the Per Day Price - ");
        int per_day_price = sc.nextInt();

        List<Camera> fullList = getCameraList(ALL_CAMERAS);
        List<Camera> myList = getCameraList(MY_CAMERA);
        int id = 0;
        if (fullList.size() > 0)
            id = fullList.get(fullList.size() - 1).getId();
        Camera camera = new Camera(id + 1, brand, model, per_day_price);
        myList.add(camera);
        fullList.add(camera);

        File all_cameras = new File(ALL_CAMERAS);
        File my_cameras = new File(MY_CAMERA);
        try {
            FileWriter fw1 = new FileWriter(all_cameras, true);
            FileWriter fw2 = new FileWriter(my_cameras, true);
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
            List<Camera> fullList = getCameraList(ALL_CAMERAS);
            List<Camera> myList = getCameraList(MY_CAMERA);
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

                    File allcameras = new File(ALL_CAMERAS);
                    File mycameras = new File(MY_CAMERA);
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

    public int getWalletBalance() {
        int wallet_balance = 0;
        try {
            File file = new File(WALLET);
            Scanner reader = new Scanner(file);

            if (reader.hasNext()) {
                wallet_balance = Integer.parseInt(reader.next());
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return wallet_balance;
    }

    public void myWallet() {
        try {
            Scanner sc = new Scanner(System.in);
            int wallet_balance = getWalletBalance();
            System.out.println("Your Current Wallet Balance is - INR." + wallet_balance);
            System.out.print("Do you want to add more amount to your wallet?(1.Yes 2.No) - ");
            switch (sc.nextInt()) {
                case 1:
                    System.out.print("Enter the amount - ");
                    int amount = sc.nextInt();
                    if (amount > 0) {
                        wallet_balance += amount;
                        FileWriter fw = new FileWriter(WALLET);
                        fw.write(wallet_balance + "");
                        fw.close();
                        System.out.println("Your wallet balance updated successfully. Current Wallet Balance - INR." + wallet_balance);
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

    public boolean login() {
        boolean status = false;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("USERNAME - ");
            String username = sc.next();
            System.out.print("PASSWORD - ");
            String password = sc.next();
            File file = new File(CREDENTIAL);
            Scanner reader = new Scanner(file);
            if (reader.hasNext()) {
                String[] credentials = reader.nextLine().split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    status = true;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    public void rent() {
        Scanner sc = new Scanner(System.in);
        int balance = getWalletBalance();
        List<Camera> availableList = getCameraList(ALL_CAMERAS);
        availableList.removeIf(n -> (n.getStatus() == 'r'));
        if (availableList.size() > 0) {
            System.out.println("FOLLOWING IS THE LIST OF AVAILABLE CAMERA(S) - ");
            displayCameraList(availableList);
            System.out.println("ENTER THE CAMERA ID YOU WANT TO RENT - ");
            int camera_id = sc.nextInt();
            String brand = "";
            String model = "";
            int price_per_day = 0;
            boolean flag = false;
            for (Camera c : availableList) {
                if (c.getId() == camera_id) {
                    flag = true;
                    brand = c.getBrand();
                    model = c.getModel();
                    price_per_day = c.getPrice_per_day();
                    break;
                }
            }
            if (flag){
                if(balance>price_per_day){
                    System.out.println("YOUR TRANSACTION FOR CAMERA - "+brand+" "+model+" with rent INR."+price_per_day+" HAS SUCCESSFULLY COMPLETED.");
                }else {
                    System.out.println("TRANSACTION FAILED DUE TO INSUFFICIENT WALLET BALANCE. PLEASE DEPOSIT THE AMOUNT TO YOUR WALLET.");
                }
            }else{
                System.out.println("INVALID ID");
            }
        } else {
            System.out.println("CURRENTLY NO CAMERA(S) AVAILABLE FOR RENT.");
        }
    }
}


