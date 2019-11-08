
public class TestMain {

    public static void main(String[] args) {
        DataReader reader = new DataReader();
       // System.out.println(reader.toString());
        for (String s : reader.getPairs()) {
            System.out.println(s);

        }
    }
}
