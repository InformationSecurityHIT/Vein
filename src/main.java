import UI.uimain;

import java.awt.*;


public class main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    uimain ui = new uimain();
                    ui.begin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
