import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class run {
    public static void main(String[] args) {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python D:\\Lab\\java_python\\process\\process_all_images.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
