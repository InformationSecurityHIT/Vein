package tem;

import gnu.io.SerialPort;
import gnu.io.SerialPort;
import serialException.*;
import tem.SerialTool;

import javax.swing.*;

import javax.swing.*;

public class gettem {


    public static void printHexString(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            //System.out.print(hex.toUpperCase());
        }
    }

    private static int convertByteToInt(byte data) {
        int heightBit = (int) ((data >> 4) & 0x0F);
        int lowBit = (int) (0x0F & data);
        return heightBit * 16 + lowBit;
    }

    //如果数据不合法，返回0
    public static int byte2temperature(byte[] b) {
        if (convertByteToInt(b[0]) != 0 || convertByteToInt(b[1]) != 0) {
            //System.out.println("Wrong Data.");
            return 0;
        }
        return convertByteToInt(b[3]) * 256 + convertByteToInt(b[2]);
    }

    public float gethealth() throws SerialPortParameterFailure, NoSuchPort, PortInUse, NotASerialPort, ReadDataFromSerialPortFailure, SerialPortInputStreamCloseFailure {
        SerialPort sp = SerialTool.openPort("COM4", 9600);
        byte[] data;
        while (true) {
            data = SerialTool.readFromPort(sp);
            if (data != null) break;
        }
        //自定义解析过程
        if (data == null || data.length < 1) {    //检查数据是否读取正确
            JOptionPane.showMessageDialog(null, "读取数据过程中未获取到有效数据！请检查设备或程序！", "错误", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        SerialTool.closePort(sp);
        return (float)byte2temperature(data)/100;
    }
}