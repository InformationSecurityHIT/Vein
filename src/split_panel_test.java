import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class split_panel_test extends JFrame {
    public static void main(String[] args) {
        JFrame f = new JFrame("CardLayerout");
        f.setSize(300, 400);
        f.setLocation(300, 300);
        JPanel p1 = new JPanel();//按钮的类
        p1.setBounds(50, 50, 300, 60);
        JButton b1 = new JButton("杰杰");
        JButton b2 = new JButton("海里");
        JButton b3 = new JButton("主见");
        p1.add(b1);
        p1.add(b2);
        p1.add(b3);
        JPanel p2 = new JPanel();
        p2.setBounds(10, 150, 300, 60);
        JLabel l = new JLabel();
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                // TODO Auto-generated method stub
                ImageIcon i = new ImageIcon("img/0.png");
                l.setIcon(i);
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                // TODO Auto-generated method stub
                ImageIcon i2 = new ImageIcon("img/1.png");
                l.setIcon(i2);
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon i3 = new ImageIcon("img/2.png");
                l.setIcon(i3);
            }
        });
        p2.add(l);
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p1, p2);
        sp.setDividerLocation(80);
        f.add(sp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

}
