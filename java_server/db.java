package com;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class db {
    private static String url1 = null;
    private static String username1 = null;
    private static String password1 = null;

    //初始化数据库连接参数
    public db(){
        try(FileReader fr=new FileReader("connect.txt");
            BufferedReader bufferedReader = new BufferedReader(fr)) {
            url1 = bufferedReader.readLine();
            username1 = bufferedReader.readLine();
            password1 = bufferedReader.readLine();
        }catch (Exception e){
            System.out.println("数据库连接失败");
        }
    }

    //用户登录
    public JSONObject Compare(String user,String p,String type) throws Exception {
        // 1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql="";

        if(type.equals("1")){  //普通用户
            sql = "select * from user";
        }else if(type.equals("2")){  //管理员
            sql = "select * from admin";
        }else{
            System.out.println("ERROR");
        }

        //查询数据
        JSONObject json=new JSONObject();
        try(Connection conn=DriverManager.getConnection(url1,username1,password1);
            Statement statement = conn.createStatement()) {

            String id=null;
            String pass=null;
            String check="";
            try(ResultSet rs = statement.executeQuery(sql)) {
                while(rs.next()){
                    //获取id这一列数据
                    id=rs.getString("id");
                    //获取password这一列数据
                    pass=rs.getString("password");
                    System.out.println(id+" "+pass);
                    if(id.equals(user)&&pass.equals(p)){
                        check="yes2";
                        break;
                    }else{
                        check="no2";
                    }
                }
            }catch (Exception e1){
                System.out.println("ERROR");
            }
            System.out.println(check);

            json.put("answer",check);

            System.out.println(json);

        }catch (Exception e){
            System.out.println("ERROR");
        }

        return json;
    }

    //用户注册
    public JSONObject SignIn(String user,String p,String type) throws Exception {
        // 1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        String check="";//判断注册是否成功

        try(Connection conn=DriverManager.getConnection(url1,username1,password1);
            Statement statement = conn.createStatement()){

            if(type.equals("1")){  //普通用户注册
                String sql = "select * from user";
                try(ResultSet rs = statement.executeQuery(sql)) {
                    String id=null;
                    String pass=null;
                    while(rs.next()){
                        //获取id这一列数据
                        id=rs.getString("id");
                        //获取password这一列数据
                        pass=rs.getString("password");
                        if(id.equals(user)){
                            check="no1";
                            break;
                        }else{
                            System.out.println(type+" "+user+" "+p );
                            try(PreparedStatement psql = conn.prepareStatement("insert into user (id,password,name,sex,birth,career,mail) " + "values(?,?,?,?,?,?,?)");) {
                                psql.setString(1, user);      //设置参数2，name 为王刚
                                psql.setString(2, p);
                                psql.setString(3," ");
                                psql.setString(4," ");
                                psql.setString(5,"1999-01-01");
                                psql.setString(6," ");
                                psql.setString(7," ");

                                psql.executeUpdate();           //执行更新
                                check="yes1";

                            }catch (Exception e){
                                System.out.println("ERROR");
                            }

                            break;
                        }
                    }
                }

            }else if(type.equals("2")){  //管理员注册
                String sql = "select * from admin";
                try(ResultSet rs = statement.executeQuery(sql)) {
                    String id=null;
                    String pass=null;
                    while(rs.next()) {
                        //获取id这一列数据
                        id = rs.getString("id");
                        //获取password这一列数据
                        pass = rs.getString("password");
                        System.out.println(id + " " + pass);
                        if (id.equals(user)) {
                            check = "no1";
                            break;
                        } else {
                            ResultSet res;
                            try(PreparedStatement psql = conn.prepareStatement("insert into admin (id,password,name,sex,birth,career,mail) " + "values(?,?,?,?,?,?,?)");) {
                                psql.setString(1, user);      //设置参数2，name 为王刚
                                psql.setString(2, p);
                                psql.setString(3," ");
                                psql.setString(4," ");
                                psql.setString(5,"1999-01-01");
                                psql.setString(6," ");
                                psql.setString(7," ");
                                psql.executeUpdate();           //执行更新
                                check = "yes1";
                            }
                            break;
                        }
                    }
                }



            } else{
                System.out.println("sign in ERROR");
            }
        }


        JSONObject json=new JSONObject();
        json.put("answer",check);

        return json;
    }

    //查询
    public JSONObject queryData(String date) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");

        String sql = "select * from data1";
        String[] date1= new String[7];
        String[] tmax1= new String[7];
        String[] tmin1= new String[7];
        JSONObject json=new JSONObject();
        int mar;
        try(Connection conn=DriverManager.getConnection(url1,username1,password1);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()){
                date1[0]=rs.getString("date");

                if(date1[0].equals(date)){
                    mar=rs.getInt("mark");
                    System.out.println(mar);
                    tmax1[0]=rs.getString("tmax");
                    tmin1[0]=rs.getString("tmin");

                    int mar1=mar+1;
                    //String sql1="select * from data1 where mark="+mar1;
                    String sql1="select * from data1 where mark=?";
                    try(PreparedStatement p=conn.prepareStatement(sql1);) {
                        p.setInt(1,mar1);
                        try(ResultSet resultSet1=p.executeQuery()) {
                            while(resultSet1.next()){
                                date1[1]= resultSet1.getString("date");
                                tmax1[1]= resultSet1.getString("tmax");
                                tmin1[1]= resultSet1.getString("tmin");
                                break;
                            }
                        }
                    }

                    //(ResultSet resultSet1=statement.executeQuery(sql1))

                    int mar2=mar+2;
                    //String sql1="select * from data1 where mark="+mar1;
                    String sql2="select * from data1 where mark=?";
                    try(PreparedStatement p=conn.prepareStatement(sql2);) {
                        p.setInt(1,mar2);
                        try(ResultSet resultSet2=p.executeQuery()) {
                            while(resultSet2.next()){
                                date1[2]= resultSet2.getString("date");
                                tmax1[2]= resultSet2.getString("tmax");
                                tmin1[2]= resultSet2.getString("tmin");
                                break;
                            }
                        }
                    }

                    int mar3=mar+3;
                    //String sql1="select * from data1 where mark="+mar1;
                    String sql3="select * from data1 where mark=?";
                    try(PreparedStatement p=conn.prepareStatement(sql3);) {
                        p.setInt(1,mar3);
                        try(ResultSet resultSet3=p.executeQuery()) {
                            while(resultSet3.next()){
                                date1[3]= resultSet3.getString("date");
                                tmax1[3]= resultSet3.getString("tmax");
                                tmin1[3]= resultSet3.getString("tmin");
                                break;
                            }
                        }
                    }

                    int mar4=mar+4;
                    //String sql1="select * from data1 where mark="+mar1;
                    String sql4="select * from data1 where mark=?";
                    try(PreparedStatement p=conn.prepareStatement(sql4);) {
                        p.setInt(1,mar4);
                        try(ResultSet resultSet4=p.executeQuery()) {
                            while(resultSet4.next()){
                                date1[4]= resultSet4.getString("date");
                                tmax1[4]= resultSet4.getString("tmax");
                                tmin1[4]= resultSet4.getString("tmin");
                                break;
                            }
                        }
                    }

                    int mar5=mar+5;
                    //String sql1="select * from data1 where mark="+mar1;
                    String sql5="select * from data1 where mark=?";
                    try(PreparedStatement p=conn.prepareStatement(sql5);) {
                        p.setInt(1,mar5);
                        try(ResultSet resultSet5=p.executeQuery()) {
                            while(resultSet5.next()){
                                date1[5]= resultSet5.getString("date");
                                tmax1[5]= resultSet5.getString("tmax");
                                tmin1[5]= resultSet5.getString("tmin");
                                break;
                            }
                        }
                    }

                    int mar6=mar+6;
                    //String sql1="select * from data1 where mark="+mar1;
                    String sql6="select * from data1 where mark=?";
                    try(PreparedStatement p=conn.prepareStatement(sql6);) {
                        p.setInt(1,mar6);
                        try(ResultSet resultSet6=p.executeQuery()) {
                            while(resultSet6.next()){
                                date1[6]= resultSet6.getString("date");
                                tmax1[6]= resultSet6.getString("tmax");
                                tmin1[6]= resultSet6.getString("tmin");
                                break;
                            }
                        }
                    }

                    //将取出的历史天气数据构建成json格式
                    json.put("date1",date1[0]);
                    json.put("tmax1",tmax1[0]);
                    json.put("tmin1",tmin1[0]);

                    json.put("date2",date1[1]);
                    json.put("tmax2",tmax1[1]);
                    json.put("tmin2",tmin1[1]);

                    json.put("date3",date1[2]);
                    json.put("tmax3",tmax1[2]);
                    json.put("tmin3",tmin1[2]);

                    json.put("date4",date1[3]);
                    json.put("tmax4",tmax1[3]);
                    json.put("tmin4",tmin1[3]);

                    json.put("date5",date1[4]);
                    json.put("tmax5",tmax1[4]);
                    json.put("tmin5",tmin1[4]);

                    json.put("date6",date1[5]);
                    json.put("tmax6",tmax1[5]);
                    json.put("tmin6",tmin1[5]);

                    json.put("date7",date1[6]);
                    json.put("tmax7",tmax1[6]);
                    json.put("tmin7",tmin1[6]);

                    break;
                }
            }

        }

        return json;
    }

    //保存用户或管理员信息
    public JSONObject saveUserInfor(String type,String id,String name,String sex,String birth,String career,String mail) throws Exception{
        // 1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //String sql="update user set name=?,sex=?,birth=?,career=?,mail=? where id=?";
        String sql=null;
        if(type.equals("1")){//普通用户
            sql="update user set name=?,sex=?,birth=?,career=?,mail=? where id=?";
        }else if(type.equals("2")){//管理员
            sql="update admin set name=?,sex=?,birth=?,career=?,mail=? where id=?";
        }

        JSONObject jsonObject=new JSONObject();
        try(Connection conn=DriverManager.getConnection(url1,username1,password1);
            PreparedStatement prep=conn.prepareStatement(sql);) {
            prep.setString(1,name);
            prep.setString(2,sex);
            prep.setString(3,birth);
            prep.setString(4,career);
            prep.setString(5,mail);
            prep.setString(6,id);
            jsonObject.put("answer","yes");

            prep.executeUpdate();
        }
        return jsonObject;
    }

    //查询用户信息
    public JSONObject queryUser(String type,String id) throws Exception{
        // 1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //String sql="select * from user";
        String sql=null;
        if(type.equals("1")){
            sql="select * from user";
        }else if(type.equals("2")){
            sql="select * from admin";
        }
        JSONObject jsonObject=new JSONObject();

        String name="";
        String sex="";
        String birth="";
        String career="";
        String mail="";
        try(Connection conn=DriverManager.getConnection(url1,username1,password1);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);) {
            while(rs.next()){
                String ID=rs.getString("id");
                if(ID.equals(id)){
                    name=rs.getString("name");
                    sex=rs.getString("sex");
                    birth=rs.getString("birth");
                    career=rs.getString("career");
                    mail=rs.getString("mail");
                    break;
                }
            }
        }

        jsonObject.put("name",name);
        jsonObject.put("sex",sex);
        jsonObject.put("birth",birth);
        jsonObject.put("career",career);
        jsonObject.put("mail",mail);

        return jsonObject;
    }


    public JSONArray scanUser() throws  Exception{ //查看所有用户
        Class.forName("com.mysql.cj.jdbc.Driver");
        JSONArray json=new JSONArray();
        String sql="select * from user";
        List<user> listUser=new ArrayList<user>() ;

        try(Connection conn=DriverManager.getConnection(url1,username1,password1);
            Statement statement = conn.createStatement()) {
            String id=null;
            String name=null;
            Date birth=null;
            String mail=null;
            try(ResultSet rs = statement.executeQuery(sql)) {
                while(rs.next()){
                    id=rs.getString("id");
                    System.out.println(id);
                    name=rs.getString("name");
                    birth=rs.getDate("birth");
                    mail=rs.getString("mail");
                    user us=new user(id,name,birth,mail);
                    listUser.add(us);
                }
            }catch (Exception e1){
                System.out.println("ERROR????");
            }

            json=JSONArray.parseArray(JSONObject.toJSONString(listUser));
            System.out.println(json);

        }catch (Exception e){
            System.out.println("ERROR");
        }

        return json;
    }

    public JSONArray queryExtend(String date, int number) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        JSONArray jsonArray=new JSONArray();
        String sql = "select * from data1";

        System.out.println(date+" "+number);
        List<Temperature> ListTem=new ArrayList<Temperature>();
        String dat=null;
        int mar;
        int tmax;
        int tmin;
        try(Connection conn=DriverManager.getConnection(url1,username1,password1);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()){
                dat=rs.getString("date");

                if(dat.equals(date)){
                    mar=rs.getInt("mark");
                    System.out.println(mar);
                    tmax=rs.getInt("tmax");
                    tmin=rs.getInt("tmin");
                    Temperature t=new Temperature(dat,tmax,tmin);
                    ListTem.add(t);

                    int mar1=mar+1;

                    while(mar1<mar+number){
                        //String sql1="select * from data1 where mark="+mar1;
                        String sql1="select * from data1 where mark=?";
                        try(PreparedStatement p=conn.prepareStatement(sql1);) {
                            p.setInt(1,mar1);
                            System.out.println(mar1);
                            try(ResultSet resultSet1=p.executeQuery()) {
                                if(!resultSet1.next()){
                                    break;
                                }else{
                                    dat=resultSet1.getString("date");
                                    tmax=resultSet1.getInt("tmax");
                                    tmin=resultSet1.getInt("tmin");
                                    Temperature tem=new Temperature(dat,tmax,tmin);
                                    ListTem.add(tem);
                                }
                            }
                        }
                        mar1++;
                    }

                    break;
                }
            }

            jsonArray=JSONArray.parseArray(JSONObject.toJSONString(ListTem));
        }catch(Exception e){
            System.out.println("ERROR");
        }

        System.out.println(jsonArray);
        return jsonArray;
    }

    public JSONObject deleteUser(String ID) throws Exception{
        // 1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql="select * from user";
        //查询数据
        JSONObject json=new JSONObject();
        try(Connection conn=DriverManager.getConnection(url1,username1,password1);
            Statement statement = conn.createStatement()) {
            String id=null;
            String check="";
            try(ResultSet rs = statement.executeQuery(sql)) {
                while(rs.next()){
                    //获取id这一列数据
                    id=rs.getString("id");
                    if(id.equals(ID)){
                        try(PreparedStatement psql = conn.prepareStatement("delete from user where id=?") ) {
                            psql.setString(1, ID);
                            psql.executeUpdate();
                            psql.close();
                            check = "yes3";
                            break;
                        }
                    }else{
                        check="no3";
                    }
                }
            }catch (Exception e1){
                System.out.println("ERROR");
            }
            json.put("answer",check);

            System.out.println(json);

        }catch (Exception e){
            System.out.println("ERROR");
        }

        return json;
    }
}
