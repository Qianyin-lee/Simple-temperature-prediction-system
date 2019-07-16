package com;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SimpleHttpServer implements Runnable{
    ServerSocket serverSocket;
    public static final int PORT = 5500;    //标准http端口

    public SimpleHttpServer(){
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("server Error");
            System.exit(1);
        }
        new Thread(this).start();
        System.out.println("HTTP服务器正在运行，端口为："+PORT);
    }
    @Override
    public void run(){
        while(true){
            OutputStream outputStream=null;
            try {
                Socket client = serverSocket.accept();      //客户机（这里是IE等浏览器)已经连接到了当前服务器
                if(client!=null){
                    System.out.println("连接到服务器的客户端是："+client);
                    InputStream is = client.getInputStream();

                    byte[] bytes = new byte[1024];
                    int len;
                    StringBuilder sb = new StringBuilder();
                    while ((len = is.read(bytes)) != -1) {
                        sb.append(new String(bytes, 0, len,"UTF-8"));
                    }
                    System.out.println("get message from client: " + sb);

                    JSONObject json = JSONObject.parseObject(sb.toString());
                    String option=json.getString("options");//操作类型
                    System.out.println(option);

                    JSONObject js =new JSONObject();
                    if(option.equals("7")){
                        JSONArray j=new JSONArray();
                        db d=new db();
                        j=d.scanUser();
                        System.out.println(j.toJSONString());
                        String s=j.toJSONString();

                        OutputStream output = null;
                        try{
                            output = client.getOutputStream();
                            output.write(s.getBytes("UTF-8"));
                            System.out.println("发送成功");
                            output.flush();
                            output.close();
                        }catch(Exception e){
                            System.out.println("ERROR");
                        }

                    }else if(option.equals("3")){
                        JSONArray j=new JSONArray();
                        String number=json.getString("number");
                        String date=json.getString("date");
                        db d=new db();
                        int num=Integer.parseInt(number);
                        j=d.queryExtend(date,num);
                        System.out.println(j.toJSONString());
                        String s=j.toJSONString();

                        OutputStream output = null;
                        try{
                            output = client.getOutputStream();
                            output.write(s.getBytes("UTF-8"));
                            System.out.println("发送成功");
                            output.flush();
                            output.close();
                        }catch(Exception e){
                            System.out.println("ERROR");
                        }
                    } else{
                        if(option.equals("0")){
                            js.put("answer","yes");
                            break;
                        }
                        else if(option.equals("1")){       //注册
                            String type=json.getString("type");
                            String id=json.getString("id");
                            String password=json.getString("password");
                            System.out.println(id);
                            db d=new db();
                            //str=d.SignIn(id,password,type);
                            js=d.SignIn(id,password,type);

                        }else if(option.equals("2")){      //登录
                            String type=json.getString("type");
                            String id=json.getString("id");
                            String password=json.getString("password");
                            db d=new db();
                            //str=d.Compare(id,password,type);
                            js=d.Compare(id,password,type);
                        }else if(option.equals("4")){       //传输预测数据
                            ClientSocket cs=new ClientSocket();
                            String number=json.getString("number");
                            String date=json.getString("date");
                            js=cs.predict(date,number);
                            System.out.println(js.toJSONString());
                        }else if(option.equals("5")){     //保存用户信息

                            String type=json.getString("type");
                            String id=json.getString("id");
                            String name=json.getString("name");
                            String sex=json.getString("sex");
                            String birth=json.getString("birth");
                            String career=json.getString("career");
                            String mail=json.getString("email");
                            System.out.println(json.toJSONString());
                            db d=new db();
                            js=d.saveUserInfor(type,id,name,sex,birth,career,mail);
                        }else if(option.equals("6")){     //查询用户信息
                            String type=json.getString("type");
                            String id=json.getString("id");
                            db d=new db();
                            js=d.queryUser(type,id);
                            System.out.println(js.toJSONString());
                        }else if(option.equals("8")){     //删除用户
                            String id=json.getString("id");
                            db d=new db();
                            js=d.deleteUser(id);
                        }

                        String str=js.toJSONString();
                        System.out.println(str);

                        outputStream = client.getOutputStream();
                        outputStream.write(str.getBytes("UTF-8"));
                        System.out.println("发送成功");

                        outputStream.flush();
                        outputStream.close();
                    }
                    is.close();
                    client.close();
                }
            }  catch (Exception e) {
                System.out.println("ERROR！！！！");
            }
        }
    }

    public static void main(String[] args) {
        SimpleHttpServer server = new SimpleHttpServer();
        server.run();
    }
}