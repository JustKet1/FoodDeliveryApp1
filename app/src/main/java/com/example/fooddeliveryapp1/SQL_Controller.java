package com.example.fooddeliveryapp1;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.DriverManager;

import android.os.StrictMode;
import android.util.Log;


public class SQL_Controller {
    Connection sql_con;
    public Connection connectionClass(){
        String ip = "192.168.1.4";
        String port = "1433";
        String db= "deliveryDB";
        String username = "AndroidUser";
        String password = "12345";
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        String ConnectURL = "jdbc:jtds:sqlserver://"+ip+":"+port+";database="+db+";";


        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver"); // Метод инициализацирует драйвер Java для JDBC
            sql_con = DriverManager.getConnection(ConnectURL, username, password); // Получение соединения
        } catch (Exception e){
            Log.e("Error is ", e.getMessage());
        }
        return sql_con;
    }
}
