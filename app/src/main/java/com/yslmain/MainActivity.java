package com.yslmain;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.yslmain.mailutil.MailSenderInfo;
import com.yslmain.mailutil.SimpleMailSender;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class MainActivity extends AppCompatActivity {
  private Button send;
  private TextView sendd;
  String Title;
  String Date;
  String From;
  String Content;
  String username;
  String password;
  String receivehost;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    send = (Button) findViewById(R.id.send);
    sendd = (TextView) findViewById(R.id.sendd);

    send.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        MenuList();
      }
    });
  }

  private void sendMessage(final String msg) {
    /*****************************************************/
    Log.i("shuxinshuxin", "开始发送邮件");
    //Toast.makeText(MainActivity.this,"发送成功", Toast.LENGTH_SHORT).show();
    // 这个类主要是设置邮件
    new Thread(new Runnable() {

      @Override
      public void run() {

        // TODO Auto-generated method stub
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.126.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("a3068793203@126.com");
        mailInfo.setPassword("aa120112");// 您的邮箱密码
        mailInfo.setFromAddress("a3068793203@126.com");
        mailInfo.setToAddress("aa3068793203@126.com");
        mailInfo.setSubject("这是标题");
        mailInfo.setContent(msg);
        // 这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        boolean isSuccess = sms.sendTextMail(mailInfo);// 发送文体格式
        // sms.sendHtmlMail(mailInfo);//发送html格式
        if (isSuccess) {
          Log.i("shuxinshuxin", "发送成功");
          //Toast.makeText(MainActivity.this,"发送成功",Toast.LENGTH_LONG).show();
        } else {
          Log.i("shuxinshuxin", "发送失败");
          //Toast.makeText(MainActivity.this,"发送失败",Toast.LENGTH_LONG).show();
        }
      }
    }).start();}

  public void MenuList()  {
    new Thread(new Runnable() {

      @Override
      public void run() {
        Looper.prepare();
        try {
          Properties props = new Properties();
          Session session = Session.getDefaultInstance(props); // 取得pop3协议的邮件服务器
          Store store = session.getStore("pop3");
          //连接pop.sina.com邮件服务器 //
          store.connect("pop.126.com","aa3068793203@126.com", "aa120112"); // 返回文件夹对象
          Folder folder = store.getFolder("INBOX"); // 设置仅读
          folder.open(Folder.READ_ONLY); // 获取信息
          Message message[] = folder.getMessages();
          ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();//定义一个List并且将其实例化
          for (int i = 0; i < message.length; i++) {//通过for语句将读取到的邮件内容一个一个的在list中显示出来
            //ResolveMail receivemail = new ResolveMail((MimeMessage) message[i]);
            //Title = receivemail.getSubject();//得到邮件的标题
            //Date = receivemail.getSentDate();//得到邮件的发送时间
            Content= String.valueOf((CharSequence) message[0].getContent().toString());
            Log.e("TAG",Content);
            //Toast.makeText(MainActivity.this,Content,Toast.LENGTH_SHORT).show();
          }
          folder.close(true);//用好之后记得将floder和store进行关闭
          store.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
        Looper.loop(); }
    }).start();
  }
}
