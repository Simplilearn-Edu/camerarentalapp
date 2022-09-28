public class Validator {
    public static boolean isValidChoice(String choice) {
        try {
            Integer.parseInt(choice);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isValidPrice(String per_day_price) {
        try {
            double price = Double.parseDouble(per_day_price);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
