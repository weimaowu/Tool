package app.model.imple;

import android.util.Log;

/**
 * Created by HSAEE on 2016/9/8.
 */
public class NumberToCN {
    private int i = 0;
    private double xiaoshu = 0;
    private String b = "", decimal = "0.", str = "", integer = "";
    // 汉语中数字大写
    private static final String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    // 汉语中货币单位大写
    private static final String[] CN_UPPER_MONETRAY_UNIT = {"元", "拾", "佰", "仟", "万", "拾", "佰",
            "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟"};

    public NumberToCN(String db) {
        b = db;
        str = b;
        String reg = ".*\\..*";  //判断字符串中是否含有特定字符串.
        if (b.matches(reg)) {
            int i = b.indexOf(".");
            b = b + "000";
            str = b.substring(0, i);
            decimal = decimal + b.substring(i + 1, i + 3);
            xiaoshu = Double.valueOf(decimal);
            if (b.charAt(i + 3) >= '5') {
                xiaoshu = xiaoshu + 0.01;
            }
            decimal = String.valueOf(xiaoshu);
        }
    }

    public String Transformation() {
        if (xiaoshu > 0) {
            System.out.println(xiaoshu);
            System.out.println(decimal.length());
            if (decimal.charAt(2) == '0') {
                String a = String.valueOf(decimal.charAt(3));
                int fen = Integer.parseInt(a);
                decimal = CN_UPPER_NUMBER[fen] + "分";
            } else {
                String f = String.valueOf(decimal.charAt(2));
                int jiao = Integer.parseInt(f);
                if (decimal.length() == 4) {
                    String a = String.valueOf(decimal.charAt(3));
                    int fen = Integer.parseInt(a);
                    decimal = CN_UPPER_NUMBER[jiao] + "角" + CN_UPPER_NUMBER[fen] + "分";
                } else {
                    decimal = CN_UPPER_NUMBER[jiao] + "角";
                }
            }
        } else {
            decimal = "整";
        }
    for (int i = 0; i < str.length(); i=i+4) {
        boolean bl = false;
        if (String.valueOf(str.charAt(str.length() - i - 1)).equals("0")){integer=CN_UPPER_MONETRAY_UNIT[i]+integer;}
        for (int j = i;  j<i+4&&j < str.length(); j++) {
            int e = Integer.parseInt(String.valueOf(str.charAt(str.length() - j - 1)));
            if (e>0){
                integer = CN_UPPER_NUMBER[e] + CN_UPPER_MONETRAY_UNIT[j] + integer;
                Log.i("",integer);
                bl = true;
            }else if (bl){integer="零"+integer;}
            }
        }
        String reg = "..*";
        if (integer.matches(reg)) {
            for (int m = 1; m < str.length(); m++) {
                integer = integer.replace("零零", "零");
            }
        }
        return integer + decimal;
    }
}
