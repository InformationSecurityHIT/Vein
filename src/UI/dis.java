package UI;

import DataBase.DBBean;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamUtils;
import com.github.sarxos.webcam.util.ImageUtils;
import gnu.io.SerialPort;
import serialException.*;
import tem.SerialTool;
import tem.gettem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dis {
    Webcam webcam;
    private static String PATH = "D:\\Lab\\Info_Security\\DZH\\";
    public SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    public JButton cut = new JButton("begin");
    public JLabel label_cut = new JLabel("\u59D3\u540D");
    public JLabel label_cut1 = new JLabel("\u4F53\u6E29");
    public JLabel label_cut2 = new JLabel("\u5065\u5EB7\u72B6\u51B5");
    public JLabel label_cut3 = new JLabel("\u65F6\u95F4");
    public ImageIcon imagetoshow = new ImageIcon("data_before\\0.png");
    public JLabel picture_cut = new JLabel(imagetoshow);
    public JLabel name_cut = new JLabel("XXX");
    public JLabel tem_cut = new JLabel("36.5");
    public JLabel health_cut = new JLabel("正常");
    public JLabel location_cut = new JLabel("");
    public JLabel nowtime_cut = new JLabel(df.format(new Date()));
    gettem gt = new gettem();
    Float tem;

    /**
     * 识别静脉并进行匹配
     * @param webcam0 摄像头
     * @param p2      识别界面的JPanel
     * @param db      数据库
     */
    public void dispanel(Webcam webcam0, JPanel p2, DBBean db) {
        webcam = webcam0;
        label_cut.setBackground(Color.WHITE);
        label_cut.setFont(new Font("宋体", Font.PLAIN, 16));
        label_cut.setBounds(908, 340, 74, 23);
        p2.add(label_cut);
        label_cut1.setFont(new Font("宋体", Font.PLAIN, 16));
        label_cut1.setBackground(Color.WHITE);
        label_cut1.setBounds(908, 400, 74, 23);
        p2.add(label_cut1);
        label_cut2.setFont(new Font("宋体", Font.PLAIN, 16));
        label_cut2.setBackground(Color.WHITE);
        label_cut2.setBounds(908, 460, 74, 23);
        p2.add(label_cut2);
        label_cut3.setFont(new Font("宋体", Font.PLAIN, 16));
        label_cut3.setBackground(Color.WHITE);
        label_cut3.setBounds(908, 520, 74, 19);
        p2.add(label_cut3);
        picture_cut.setBackground(Color.WHITE);
        picture_cut.setBounds(908, 100, 200, 200);
        p2.add(picture_cut);
        name_cut.setFont(new Font("宋体", Font.PLAIN, 16));
        name_cut.setBackground(Color.WHITE);
        name_cut.setBounds(805, 340, 103, 23);
        p2.add(name_cut);
        tem_cut.setBackground(Color.WHITE);
        tem_cut.setFont(new Font("宋体", Font.PLAIN, 16));
        tem_cut.setBounds(805, 400, 103, 23);
        p2.add(tem_cut);
        health_cut.setFont(new Font("宋体", Font.PLAIN, 16));
        health_cut.setBackground(Color.WHITE);
        health_cut.setBounds(1005, 460, 103, 23);
        p2.add(health_cut);
        location_cut.setBackground(Color.WHITE);
        location_cut.setFont(new Font("宋体", Font.PLAIN, 16));
        location_cut.setBounds(573, 520, 103, 19);
        p2.add(location_cut);
        cut.setBackground(new Color(0, 153, 153));
        cut.setForeground(Color.WHITE);
        cut.setFont(new Font("宋体", Font.PLAIN, 16));
        cut.setBounds(63, 624, 640, 50);
        p2.add(cut);
        nowtime_cut.setBackground(Color.WHITE);
        nowtime_cut.setFont(new Font("宋体", Font.PLAIN, 16));
        nowtime_cut.setBounds(805, 520, 216, 23);
        p2.add(nowtime_cut);
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cut.setEnabled(false);
                String fileName1 = PATH + "data_before\\match\\0";
                try {
                    SerialPort sp = SerialTool.openPort("COM4", 9600);
                    byte[] data;
                    while (true) {
                        data = SerialTool.readFromPort(sp);
                        if (data != null) {
                            tem = (float) gt.byte2temperature(data) / 100;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            WebcamUtils.capture(webcam, fileName1, ImageUtils.FORMAT_PNG);
                            cut.setEnabled(true);
                            //把匹配的代码加进
                            Process proc;
                            try {
                                long start_time = System.currentTimeMillis();
                                String[] args = new String[]{"python", PATH + "process\\process_one_img_for_match.py",
                                        PATH + "data_before\\match\\0.png",//图片路径
                                        PATH + "data_after\\match"};
                                proc = Runtime.getRuntime().exec(args);
                                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                                String line = null;
                                String tmp;
                                while ((tmp = in.readLine()) != null) {
                                    line = tmp;
                                    //line这个参数里面有它匹配到的信息，如果需要的话，
                                    //那个脚本没有办法用类似return这样的语句向java返回参数
                                    //只能通过这样的数据流捕获
                                    System.out.println(line);
                                    //break;
                                }
                                in.close();
                                proc.waitFor();
                                proc.destroy();
                                long end_time = System.currentTimeMillis();
                                System.out.println("Match time:" + (end_time - start_time) / 1000);
                                String name = null;
                                ResultSet re = db.executeFind(line, "person", "id");
                                try {
                                    while (re.next()) {
                                        name = re.getString("name");
                                    }
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                String finalName = name;
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
                                        name_cut.setText(finalName);
                                        tem_cut.setText(String.valueOf(tem));
                                        nowtime_cut.setText(df.format(new Date()));
                                        p2.remove(picture_cut);
                                        imagetoshow = new ImageIcon("data_before\\match\\0.png");
                                        JLabel picture_cut = new JLabel(imagetoshow);
                                        picture_cut.setBackground(Color.WHITE);
                                        picture_cut.setBounds(908, 100, 200, 200);
                                        p2.add(picture_cut);
                                        p2.updateUI();
//                                    }
//                                }).start();
//                                in.close();
//                                proc.waitFor();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                    //SerialTool.closePort(sp);
                } catch (SerialPortParameterFailure serialPortParameterFailure) {
                    //serialPortParameterFailure.printStackTrace();
                } catch (NoSuchPort noSuchPort) {
                    //noSuchPort.printStackTrace();
                } catch (PortInUse portInUse) {
                    //portInUse.printStackTrace();
                } catch (NotASerialPort notASerialPort) {
                    //notASerialPort.printStackTrace();
                } catch (ReadDataFromSerialPortFailure readDataFromSerialPortFailure) {
                    //readDataFromSerialPortFailure.printStackTrace();
                } catch (SerialPortInputStreamCloseFailure serialPortInputStreamCloseFailure) {
                    //serialPortInputStreamCloseFailure.printStackTrace();
                }
                return;
            }
        });
    }
}
