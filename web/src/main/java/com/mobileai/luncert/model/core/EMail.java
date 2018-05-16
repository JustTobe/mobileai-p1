package com.mobileai.luncert.model.core;

import java.util.Properties;

public class EMail {
  
  public static final int TENCENT = 0;

  public static final int NETEASE = 1;

  private static final String[] SMTP_SERVER_ADDR = {"smtp.qq.com", "smtp.164.com"};

  private static final int[] SMTP_SERVER_PORT = {465, 25};

  private String mailServerHost;

  private int mailServerPort;

  private String sourceAddress;

  private String targetAddress;
  
  private boolean validate = true;

  private String userName;
  
  private String password;

  private String subject;

  private String content;

  // 邮件附件的文件名    
  private String[] attachFileNames;
  
  public EMail(String target) {
    
    String sp = target.substring(target.indexOf("@") + 1);
    int type;
    if (sp.compareTo("qq.com") == 0) type = TENCENT;
    else if (sp.compareTo("163.com") == 0) type = NETEASE;
    else type = 0;
    mailServerHost = SMTP_SERVER_ADDR[type];
    mailServerPort =  SMTP_SERVER_PORT[type];

    sourceAddress = "lijingweisee@qq.com";
    targetAddress = target;
    userName = "2725115515";
    password = "sanlcutywmeldghf";
  }

  /**   
    * 获得邮件会话属性   
    */    
  public Properties getProperties(){
    Properties props = new Properties();
    props.setProperty("mail.transport.protocol", "smtp");
    props.setProperty("mail.smtp.host", mailServerHost);
    props.setProperty("mail.smtp.port", String.valueOf(mailServerPort));
    props.setProperty("mail.smtp.auth", validate ? "true" : "false");
    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
    props.setProperty("mail.smtp.socketFactory.fallback", "false");  
    props.setProperty("mail.smtp.socketFactory.port", String.valueOf(mailServerPort));  
    return props;
  }

  public String getMailServerHost() { return mailServerHost; }

  public void setMailServerHost(String mailServerHost) { this.mailServerHost = mailServerHost; }

  public int getMailServerPort() { return mailServerPort; }

  public void setMailServerPort(int mailServerPort) { this.mailServerPort = mailServerPort; }
  
  public boolean getValidate() { return validate; }

  public void setValidate(boolean validate) { this.validate = validate; }

  public String[] getAttachFileNames() { return attachFileNames; }

  public void setAttachFileNames(String[] fileNames) { this.attachFileNames = fileNames; }
  
  public String getSourceAddress() { return sourceAddress; }
  
  public void setSourceAddress(String sourceAddress) { this.sourceAddress = sourceAddress; }

  public String getPassword() { return password; }

  public void setPassword(String password) { this.password = password; }

  public String getTargetAddress() { return targetAddress; }

  public void setTargetAddress(String targetAddress) { this.targetAddress = targetAddress; }
  
  public String getUserName() { return userName; }

  public void setUserName(String userName) { this.userName = userName; }

  public String getSubject() { return subject; }
  
  public void setSubject(String subject) { this.subject = subject; }
  
  public String getContent() { return content; }
  
  public void setContent(String textContent) { this.content = textContent; }

}  