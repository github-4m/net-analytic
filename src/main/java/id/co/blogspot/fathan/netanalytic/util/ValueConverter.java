package id.co.blogspot.fathan.netanalytic.util;

public class ValueConverter {

  public static Integer stringToInteger(String source) throws Exception {
    Integer value = 0;
    for (int i = 0; i < source.length(); i++) {
      value += source.charAt(i);
    }
    return value;
  }

}
