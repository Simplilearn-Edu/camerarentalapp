import java.util.ArrayList;
import java.util.List;

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
}
