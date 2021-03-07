package UI;

import DataBase.DBBean;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class inf {
    public JButton save = new JButton("保存");
    public JLabel label_in = new JLabel("\u59D3\u540D");
    public JLabel label_in1 = new JLabel("\u8054\u7CFB\u65B9\u5F0F");
    public JLabel label_in2 = new JLabel("\u5C45\u4F4F\u5730");
    public JLabel label_in3 = new JLabel("身份证号");
    public JTextField name_in = new JTextField();
    public JTextField idnumber_in = new JTextField();
    public JTextField tel_in = new JTextField();
    public JTextField home_in = new JTextField();
    String id0 = null;
    String name = null;

    /**
     * 录入时输入个人信息界面
     *
     * @param webcam 摄像头
     * @param p2     输入界面JPanel
     * @param db     数据库
     * @param panel  摄像头Panel
     */
    public void initialize_in(Webcam webcam, JPanel p2, DBBean db, WebcamPanel panel) {
        save.setBackground(new Color(0, 153, 153));
        save.setForeground(Color.WHITE);
        save.setFont(new Font("宋体", Font.PLAIN, 16));
        save.setBounds(118, 559, 640, 50);
        p2.add(save);
        label_in.setFont(new Font("宋体", Font.PLAIN, 16));
        label_in.setBackground(Color.WHITE);
        label_in.setBounds(118, 180, 67, 25);
        p2.add(label_in);
        label_in1.setFont(new Font("宋体", Font.PLAIN, 16));
        label_in1.setBackground(Color.WHITE);
        label_in1.setBounds(118, 260, 67, 25);
        p2.add(label_in1);
        label_in2.setFont(new Font("宋体", Font.PLAIN, 16));
        label_in2.setBackground(Color.WHITE);
        label_in2.setBounds(118, 340, 67, 25);
        p2.add(label_in2);
        label_in3.setFont(new Font("宋体", Font.PLAIN, 16));
        label_in3.setBackground(Color.WHITE);
        label_in3.setBounds(118, 420, 67, 25);
        p2.add(label_in3);
        name_in.setFont(new Font("宋体", Font.PLAIN, 16));
        name_in.setBackground(Color.WHITE);
        name_in.setBounds(438, 180, 320, 25);
        p2.add(name_in);
        name_in.setColumns(10);
        tel_in.setBounds(438, 260, 320, 25);
        p2.add(tel_in);
        tel_in.setColumns(10);
        home_in.setBounds(438, 340, 320, 25);
        p2.add(home_in);
        idnumber_in.setBounds(438, 420, 320, 25);
        p2.add(idnumber_in);
        home_in.setColumns(10);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name_in0 = name_in.getText();
                String tel_in0 = tel_in.getText();
                String home_in0 = home_in.getText();
                String idnumber_in0 = idnumber_in.getText();
                db.executeQuery("person(name,tel,home_location,id_number)", '\'' + name_in0 + '\'' + ',' + '\'' + tel_in0 + '\'' + ',' + '\'' + home_in0 + '\'' + ',' + '\'' + idnumber_in0 + '\'');
                ResultSet re = db.executeFindMAXID("person", "id");
                try {
                    while (re.next()) {
                        id0 = re.getString("id");
                        name = re.getString("name");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                db.executeCreateNewTable(name);
                input in = new input(webcam, db, panel);
                in.inpanel(p2);
                in.take_picture(p2, id0);
            }
        });
    }
}
