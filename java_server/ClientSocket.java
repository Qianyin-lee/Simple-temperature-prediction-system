package com;

import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {

    public static final String host="192.168.137.80";
    public static final int Port=6000;
    //接收预测数据，传到前端
    public JSONObject predict(String date,String number)throws Exception {
        String info=null;
        JSONObject jsonObject=null;

        Socket socket=null;
        try {
            socket = new Socket(host,Port);
            //Socket socket = new Socket("192.168.137.80",6000);

            JSONObject js=new JSONObject();
            js.put("date",date);
            js.put("number",number);
            //获取输出流，向服务器端发送信息
            OutputStream os=socket.getOutputStream();//字节输出流
            PrintWriter pw=new PrintWriter(os);//将输出流包装为打印流
            //pw.write(jsonObject.toString());
            pw.write(js.toJSONString());
            System.out.println("发送成功");
            pw.flush();
            socket.shutdownOutput();//关闭输出流

            //接收python端的预测数据
            InputStream is=socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            info=in.readLine();

            jsonObject=JSONObject.parseObject(info);
            System.out.println(jsonObject.toJSONString());
            is.close();
            in.close();
            //socket.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
           System.out.println("ERROR");
        } catch (IOException e) {
            System.out.println("ERROR");
        }finally {
            socket.close();
        }
        return jsonObject;
    }
}
