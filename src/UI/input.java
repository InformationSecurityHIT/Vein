package UI;

import java.util.*;
import java.util.List;

import DataBase.DBBean;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamUtils;
import com.github.sarxos.webcam.util.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class input {

    private static String PATH = "D:\\Lab\\Info_Security\\DZH\\";
    private JButton cut2 = new JButton("\u622A\u56FE");
    private DBBean db;
    private Webcam webcam;
    private ImageIcon imagetoshow;
    private JLabel TipsPicture_label;
    private JLabel Tips_text;
    private WebcamPanel panel;

    /**
     * 构造函数
     *
     * @param webcam 摄像头
     * @param db     数据库
     * @param panel  摄像头的Panel
     */
    public input(Webcam webcam, DBBean db, WebcamPanel panel) {
        this.webcam = webcam;
        this.db = db;
        this.panel = panel;
    }

    /**
     * 录入静脉界面
     *
     * @param p2 界面的JPanel
     */
    public void inpanel(JPanel p2) {
        p2.updateUI();
        p2.removeAll();
        p2.add(panel);
        cut2.setBackground(new Color(0, 153, 153));
        cut2.setForeground(Color.WHITE);
        cut2.setFont(new Font("截图", Font.PLAIN, 16));
        cut2.setBounds(63, 610, 640, 50);
        p2.add(cut2);
    }

    /**
     * 拍照并存储静脉图像
     *
     * @param p2      界面JPanel
     * @param finalId 当前用户的ID
     */
    public void take_picture(JPanel p2, String finalId) {
        imagetoshow = new ImageIcon("gesture\\normal.jpg");
        TipsPicture_label = new JLabel(imagetoshow);
        TipsPicture_label.setBackground(Color.WHITE);
        TipsPicture_label.setBounds(800, 150, 400, 400);

        Tips_text = new JLabel("开始静脉录入");
        Tips_text.setFont(new Font("宋体", Font.PLAIN, 16));
        Tips_text.setBackground(Color.WHITE);
        Tips_text.setBounds(800, 100, 400, 23);

        p2.add(TipsPicture_label);
        p2.add(Tips_text);

        cut2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                List<String> gesture = Arrays.asList("手摆正", "向左倾斜30°", "向右倾斜30°", "手腕向下倾斜30°", "手腕向上倾斜30°", "顺时针旋转30°", "逆时针旋转30°");
                List<String> gesture2 = Arrays.asList("请将左手", "请将右手");
                cut2.setEnabled(false);
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File file1 = new File("data_before\\" + finalId);
                        File file2 = new File("data_after\\" + finalId);
                        if (!file1.exists()) file1.mkdir();
                        if (!file2.exists()) file2.mkdir();
                        for (int j = 0; j < 14; j++) {

                            Tips_text.setText(gesture2.get(j / 7) + gesture.get(j % 7));
                            p2.remove(TipsPicture_label);
                            imagetoshow = new ImageIcon("gesture\\" + (j + 1) + ".png");
                            TipsPicture_label = new JLabel(imagetoshow);
                            TipsPicture_label.setBackground(Color.WHITE);
                            TipsPicture_label.setBounds(800, 150, 400, 400);
                            p2.add(TipsPicture_label);
                            p2.updateUI();
                            if (j == 7) {
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
                            }
                            try {
                                Thread.sleep(1000);
                                Tips_text.setText(gesture2.get(j / 7) + gesture.get(j % 7) + "，开始拍照");
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            for (int i = 0; i < 2; i++) {//å·¦æ‰‹
//                                try {
//                                    Thread.sleep(1000);
//                                } catch (InterruptedException interruptedException) {
//                                    interruptedException.printStackTrace();
//                                }
                                String fileName = "data_before\\" + finalId + "\\" + finalId + "_" + j * i;
                                WebcamUtils.capture(webcam, fileName, ImageUtils.FORMAT_PNG);
                            }
                        }
                        Tips_text.setText("录入结束");
                        File f = new File(PATH + "data_before\\" + finalId);
                        Process proc;
                        try {
                            String[] args = new String[]{"python",
                                    PATH + "process\\process_all_imgs_for_register.py",
                                    PATH + "data_before\\" + finalId,
                                    PATH + "data_after\\" + finalId};
                            File fa[] = f.listFiles();
                            for (int i = 0; i < fa.length; i++) {//循环遍历
                                File fs = fa[i];//获取数组中的第i
                                System.out.println(fs.getName());//否则直接输出
                            }
                            System.out.print("fa_length:" + fa.length);
                            proc = Runtime.getRuntime().exec(args);
                            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                            String line = null;
                            while ((line = in.readLine()) != null) {
                                System.out.println(line);
                            }
                            in.close();
                            int res = proc.waitFor();
                            System.out.println(res);
                        } catch (IOException ev) {
                            ev.printStackTrace();
                        } catch (InterruptedException ev) {
                            ev.printStackTrace();
                        }
                    }
                });
                th.start();

                cut2.setEnabled(true);
            }
        });
    }
}
