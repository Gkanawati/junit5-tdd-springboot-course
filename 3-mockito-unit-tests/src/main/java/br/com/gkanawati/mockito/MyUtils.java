package br.com.gkanawati.mockito;

public class MyUtils {

  public static String getWelcomeMessage(String username, boolean isCustomer) {
    if (isCustomer) {
      return "Welcome valued customer " + username + "!";
    } else {
      return "Welcome " + username + "!";
    }
  }

}
