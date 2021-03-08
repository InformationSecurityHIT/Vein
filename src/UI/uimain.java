package UI;

import DataBase.DBBean;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import gnu.io.SerialPort;
import tem.SerialTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class uimain {
    public JFrame frame;
    public Webcam webcam;
    public WebcamPanel panel;//1&2
    public DBBean db = new DBBean();
    public JButton btnNewButton = new JButton("\u8BC6\u522B\u9759\u8109");
    public JButton btnNewButton_1 = new JButton("\u5F55\u5165\u9759\u8109");
    public JButton btnNewButton_2 = new JButton("\u67E5\u770B\u8BB0\u5F55");
    public JButton btnNewButton_3 = new JButton("\u9690\u79C1\u4FDD\u62A4");
    JPanel p1 = new JPanel();//左侧
    JPanel p2 = new JPanel();//右侧
    JPanel p3 = new JPanel();

    /**
     * 初始化左侧菜单栏
     */
    public void begin() {
        frame = new JFrame();
        frame.getContentPane().setFont(new Font("宋体", Font.PLAIN, 16));
        frame.setBackground(Color.WHITE);
        frame.setTitle("\u624B\u80CC\u9759\u8109\u8BC6\u522B\u4E0E\u6D4B\u6E29\u8F6F\u4EF6");
        frame.getContentPane().setBackground(SystemColor.menu);
        frame.setBounds(100, 100, 1500, 900);
        p1.setBounds(0, 0, 257, 855);
        p1.setBackground(new Color(0, 153, 153));
        p1.setLayout(null);

        p2.setBounds(257, 0, 1243, 855);
        p2.setBackground(Color.WHITE);
        p2.setLayout(null);
        p3.setBounds(0, 0, 1243, 855);
        p3.setBackground(Color.WHITE);
        p3.setLayout(null);
        p2.add(p3);

        btnNewButton.setFont(new Font("宋体", Font.PLAIN, 16));
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBackground(new Color(0, 153, 153));
        btnNewButton.setBounds(0, 130, 257, 50);
        p1.add(btnNewButton);
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 16));
        btnNewButton_1.setBackground(new Color(0, 153, 153));
        btnNewButton_1.setBounds(0, 180, 257, 50);
        p1.add(btnNewButton_1);
        btnNewButton_2.setForeground(Color.WHITE);
        btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 16));
        btnNewButton_2.setBackground(new Color(0, 153, 153));
        btnNewButton_2.setBounds(0, 230, 257, 50);
        p1.add(btnNewButton_2);
        btnNewButton_3.setForeground(Color.WHITE);
        btnNewButton_3.setFont(new Font("宋体", Font.PLAIN, 16));
        btnNewButton_3.setBackground(new Color(0, 153, 153));
        btnNewButton_3.setBounds(0, 280, 257, 50);
        p1.add(btnNewButton_3);

        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        p3.add(panel);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p1, p2);
        sp.setDividerLocation(257);
        frame.add(sp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        p3.updateUI();
                        panel.setBounds(63, 100, 640, 480);//摄像头
                        p3.removeAll();
                        new dis().dispanel(webcam, p3, db);
                        p3.add(panel);
                    }
                });
            }
        });
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        p3.updateUI();
                        p3.removeAll();
                        panel.setBounds(63, 100, 640, 480);//摄像头
                        new inf().initialize_in(webcam, p3, db, panel);
                    }
                });
            }
        });
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        p3.updateUI();
                        p3.removeAll();
                        new search(db).searpanel(p3);
                    }
                });
            }
        });
    }
}
