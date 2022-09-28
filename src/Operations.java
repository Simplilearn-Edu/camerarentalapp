import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Operations {
    private static final String WALLET = "src/wallet.txt";
    private static final String CREDENTIALS = "src/credentials.txt";
    private static final String MY_CAMERAS = "src/mycamera.txt";
    private static final String ALL_CAMERAS = "src/camera.txt";

    private Validator v = new Validator();

    public void add_remove_camera() {
        Scanner sc = new Scanner(System.in);
        do {
            String choice = "";
            boolean choice_validation;

            do {
                System.out.println("1. ADD\n2. REMOVE\n3. MY CAMERAS\n4. GO TO PREVIOUS MENU");
                choice = sc.next();
                choice_validation = Validator.isValidChoice(choice);
                if (!choice_validation)
                    System.out.println("ERROR : INVALID CHOICE.");
            } while (!choice_validation);

            switch (Integer.parseInt(choice)) {
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
        Scanner file_reader;
        try {
            file_reader = new Scanner(cameraFile);
            while (file_reader.hasNext()) {
                String[] lines = file_reader.next().split(",");
                for (int i = 0; i < lines.length; i++) {
                    String[] content = lines[i].split(":");
                    cameras.add(new Camera(
                            Integer.parseInt(content[0]),
                            content[1],
                            content[2],
                            Double.parseDouble(content[3]),
                            content[4].charAt(0)));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
        return cameras;
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
            System.out.println("ERROR : NO DATA PRESENT AT THIS MOMENT.");
        }
    }

    public void getFullList() {
        displayCameraList(getCameraList(ALL_CAMERAS));
    }

    private void myListing() {
        displayCameraList(getCameraList(MY_CAMERAS));
    }

    private void addCamera() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ENTER THE CAMERA BRAND - ");
        String brand = sc.nextLine();
        System.out.print("ENTER THE MODEL - ");
        String model = sc.nextLine();
        String per_day_price;

        do {
            System.out.print("ENTER THE PER DAY PRICE (INR) - ");
            per_day_price = sc.next();
        } while (!v.isValidPrice(per_day_price));

        List<Camera> fullList = getCameraList(ALL_CAMERAS);
        List<Camera> myList = getCameraList(MY_CAMERAS);
        int id = 0;
        if (fullList.size() > 0)
            id = fullList.get(fullList.size() - 1).getId();
        Camera camera = new Camera(id + 1, brand, model, Double.parseDouble(per_day_price));
        myList.add(camera);
        fullList.add(camera);

        File all_cameras = new File(ALL_CAMERAS);
        File my_cameras = new File(MY_CAMERAS);
        try {
            FileWriter fw1 = new FileWriter(all_cameras, true);
            FileWriter fw2 = new FileWriter(my_cameras, true);
            fw1.write(camera.toString());
            fw2.write(camera.toString());
            fw1.close();
            fw2.close();
            System.out.println("YOUR CAMERA HAS BEEN SUCCESSFULLY ADDED TO THE LIST.");
        } catch (IOException e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    private void removeCamera() {
        try {
            Scanner sc = new Scanner(System.in);
            List<Camera> fullList = getCameraList(ALL_CAMERAS);
            List<Camera> myList = getCameraList(MY_CAMERAS);
            if (myList.size() > 0) {
                displayCameraList(myList);
                System.out.print("ENTER THE CAMERA ID TO REMOVE - ");
                int camera_id = sc.nextInt();
                if (isOnRent(myList, camera_id)) {
                    System.out.println("ERROR : THIS CAMERA CANNOT BE REMOVED AS IT IS GIVEN ON RENT.");
                } else {
                    if (myList.removeIf(n -> (n.getId() == camera_id))) {
                        fullList.removeIf(n -> (n.getId() == camera_id));

                        File allcameras = new File(ALL_CAMERAS);
                        File mycameras = new File(MY_CAMERAS);
                        FileWriter fw1 = new FileWriter(allcameras);
                        FileWriter fw2 = new FileWriter(mycameras);

                        for (Camera c : myList) {
                            fw2.write(c.toString());
                        }
                        for (Camera c : fullList) {
                            fw1.write(c.toString());
                        }

                        fw1.close();
                        fw2.close();
                        System.out.println("CAMERA SUCCESSFULLY REMOVED FROM THE LIST.");
                    } else {
                        System.out.println("ERROR : INCORRECT CAMERA ID.");
                    }
                }
            } else {
                System.out.println("ERROR : NO CAMERA FOUND IN YOUR LIST.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isOnRent(List<Camera> myList, int camera_id) {
        boolean rent_status = false;
        for (Camera c : myList) {
            if (c.getId() == camera_id && c.getStatus() == 'r') {
                rent_status = true;
                break;
            }
        }
        return rent_status;
    }

    public double getWalletBalance() {
        double wallet_balance = 0;
        try {
            File file = new File(WALLET);
            Scanner reader = new Scanner(file);

            if (reader.hasNext()) {
                wallet_balance = Double.parseDouble(reader.next());
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
            double wallet_balance = getWalletBalance();
            System.out.println("YOUR CURRENT WALLET BALANCE IS - INR." + wallet_balance);
            System.out.print("DO YOU WANT TO DEPOSIT MORE AMOUNT TO YOUR WALLET?(1.YES 2.NO) - ");
            switch (sc.nextInt()) {
                case 1:
                    System.out.print("ENTER THE AMOUNT (INR) - ");
                    double amount = sc.nextDouble();
                    if (amount > 0) {
                        wallet_balance += amount;
                        FileWriter fw = new FileWriter(WALLET);
                        fw.write(wallet_balance + "");
                        fw.close();
                        System.out.println("YOUR WALLET BALANCE UPDATED SUCCESSFULLY. CURRENT WALLET BALANCE - INR." + wallet_balance);
                    } else {
                        System.out.println("ERROR : INVALID AMOUNT - INR." + amount);
                    }
                case 2:
                    return;
                default:
                    System.out.println("ERROR : INVALID OPERATION");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            File file = new File(CREDENTIALS);
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
        try {
            Scanner sc = new Scanner(System.in);
            double balance = getWalletBalance();
            List<Camera> availableList = getCameraList(ALL_CAMERAS);
            List<Camera> fullList = getCameraList(ALL_CAMERAS);

            availableList.removeIf(n -> (n.getStatus() == 'r'));
            if (availableList.size() > 0) {
                System.out.println("FOLLOWING IS THE LIST OF AVAILABLE CAMERA(S) - ");
                displayCameraList(availableList);
                System.out.print("ENTER THE CAMERA ID YOU WANT TO RENT - ");
                int camera_id = sc.nextInt();

                String brand = "";
                String model = "";
                double price_per_day = 0;
                int full_list_index = -1;
                boolean flag = false;

                for (int i = 0; i < fullList.size(); i++) {
                    if (fullList.get(i).getId() == camera_id) {
                        if (fullList.get(i).getStatus() == 'a') {
                            flag = true;
                            brand = fullList.get(i).getBrand();
                            model = fullList.get(i).getModel();
                            price_per_day = fullList.get(i).getPrice_per_day();
                            full_list_index = i;
                            break;
                        }
                    }
                }
                if (flag) {
                    if (balance > price_per_day) {
                        File wallet = new File(WALLET);
                        File all_cameras = new File(ALL_CAMERAS);

                        FileWriter balanceWriter = new FileWriter(wallet);
                        FileWriter f1 = new FileWriter(all_cameras);

                        fullList.get(full_list_index).setStatus('r');
                        for (Camera c : fullList) {
                            f1.write(c.toString());
                        }
                        f1.close();

                        balance = balance - price_per_day;
                        balanceWriter.write(balance + "");
                        balanceWriter.close();
                        System.out.println("YOUR TRANSACTION FOR CAMERA - " + brand + " " + model + " with rent INR." + price_per_day + " HAS SUCCESSFULLY COMPLETED.");
                    } else {
                        System.out.println("ERROR : TRANSACTION FAILED DUE TO INSUFFICIENT WALLET BALANCE. PLEASE DEPOSIT THE AMOUNT TO YOUR WALLET.");
                    }
                } else {
                    System.out.println("ERROR : INVALID ID");
                }
            } else {
                System.out.println("ERROR : CURRENTLY NO CAMERA(S) AVAILABLE FOR RENT.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


