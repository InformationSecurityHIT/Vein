package UI;

import DataBase.DBBean;
import search.table;
import search.returnVector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class search {
    Object[][] playerInfo = {
            // 创建表格中的数据
            {" ", " ", " "},};
    // 创建表格中的横标题
    String[] Names = {"时间", "体温", "位置"};
    JTable table0 = new JTable(playerInfo, Names);

    JScrollPane jScrollPane = new JScrollPane(table0);//滚动条
    public JTextField search = new JTextField();
    public JButton sea = new JButton("查找");
    private DBBean db;

    public search(DBBean db) {
        this.db = db;
    }

    /**
     * 搜索界面
     *
     * @param p2 界面的JPanel
     */
    public void searpanel(JPanel p2) {
        search.setBounds(0, 100, 1000, 50);
        search.setFont(new Font("宋体", Font.PLAIN, 18));
        sea.setBackground(new Color(0, 153, 153));
        sea.setForeground(Color.WHITE);
        sea.setFont(new Font("宋体", Font.PLAIN, 18));
        sea.setBounds(1000, 100, 215, 50);
        p2.add(sea);
        p2.add(search);
        jScrollPane.setBounds(0, 150, 1215, 700);
        p2.add(jScrollPane);
        sea.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name_sea = search.getText();
                p2.remove(jScrollPane);
                table0 = new table(returnVector.getHeadName(db, name_sea), db).returnAllData_table(name_sea);
                table0.setFont(new Font("宋体", Font.PLAIN, 18));
                jScrollPane = new JScrollPane(table0);
                jScrollPane.setBounds(0, 150, 1215, 700);
                p2.add(jScrollPane);
            }
        });
    }
}
