package com.yslmain.mailutil;

/**
 * Created by Huiwu-Android on 2017/2/23.
 */
import javax.mail.*;

public class MyAuthenticator extends Authenticator {
  String userName=null;
  String password=null;

  public MyAuthenticator(){
  }
  public MyAuthenticator(String username, String password) {
    this.userName = username;
    this.password = password;
  }
  protected PasswordAuthentication getPasswordAuthentication(){
    return new PasswordAuthentication(userName, password);
  }

}
