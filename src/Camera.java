public class Camera {
    private int id;
    private String brand;
    private String model;
    private int price_per_day;
    private char status;

    public Camera() {
    }

    public Camera(int id, String brand, String model, int price_per_day) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price_per_day = price_per_day;
        this.status = 'a';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(int price_per_day) {
        this.price_per_day = price_per_day;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price_per_day=" + price_per_day +
                ", status=" + status +
                '}';
    }
}
