package com.geekbrains.cloud.jan.server;

// import com.geekbrains.cloud.jan.client.User;

import com.geekbrains.cloud.jan.db.DatabaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

public class Handler extends SimpleChannelInboundHandler<String> {

    private static final List<Channel> channels = new ArrayList<>();
    private String clientName;
    private static int newClientIndex = 1;
   // User user = new User();
    DatabaseHandler databaseHandler = new DatabaseHandler();

    private String login;
    private String password;
    private String name;
    private String surName;
    private String gender;
    private String country;
    private String city;

    public Handler(String login, String password, String name, String surName, String gender, String country, String city) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surName = surName;
        this.gender = gender;
        this.country = country;
        this.city = city;
    }

    public Handler() {
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(getLogin());
        System.out.println("Client connected " + ctx);
        channels.add(ctx.channel());
        databaseHandler.getDbConnection();
       // databaseHandler.getUser(user);
        newClientIndex++;
        System.out.println(getLogin());
        broadcastMessage("SERVER", "Connected new client: " + login); /*user. */
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Client " + clientName + " disconnect");
        channels.remove(ctx.channel());
        broadcastMessage("SERVER", "Client " + clientName + " disconnected");
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("Message received: " + s);
//        if(s.startsWith("/")) {
//            if(s.startsWith("/changename")) {
//                String newNickname = s.split("\\s", 2)[1];
//                broadcastMessage("SERVER",   clientName + " change nick on " + newNickname);
//                clientName = newNickname;
//            }
//            System.out.println("You changed name");
//            return;
//        }
        broadcastMessage(clientName, s);

    }

    public void broadcastMessage(String clientName, String message) {
        String out = String.format("[%s]: %s\n", clientName, message);
        for (Channel c : channels) {
            c.writeAndFlush(out);
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Handler{" +
                "login='" + login + '\'' +
                '}';
    }
}
