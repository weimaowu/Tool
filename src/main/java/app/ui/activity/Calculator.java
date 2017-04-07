package app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class Calculator extends Activity implements OnClickListener {
    TextView text;
    Button ac, one, two, three, four, five, six, seven, eight, nine, zero, jiajian, jia, baifen, cheng, chu, jian, dian, deng;
    ImageButton menu;
    private BigDecimal bd_1;
    private BigDecimal bd_2;
    private String number1 = "";
    private String number2 = "";
    private String number3 = "";
    private String sign = "";
    private String symbol = null;
    private double result = 0;
    MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        anniu();
    }

    private void anniu() {
        text = (TextView) findViewById(R.id.text_c);
        ac = (Button) findViewById(R.id.ac);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        dian = (Button) findViewById(R.id.dian);
        jiajian = (Button) findViewById(R.id.jiajian);
        jia = (Button) findViewById(R.id.jia);
        baifen = (Button) findViewById(R.id.baifen);
        cheng = (Button) findViewById(R.id.cheng);
        chu = (Button) findViewById(R.id.chu);
        jian = (Button) findViewById(R.id.jian);
        deng = (Button) findViewById(R.id.deng);
        menu = (ImageButton) findViewById(R.id.menu);
        menu.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        jiajian.setOnClickListener(this);
        jia.setOnClickListener(this);
        baifen.setOnClickListener(this);
        cheng.setOnClickListener(this);
        chu.setOnClickListener(this);
        jian.setOnClickListener(this);
        text.setOnClickListener(this);
        zero.setOnClickListener(this);
        ac.setOnClickListener(this);
        deng.setOnClickListener(this);
        dian.setOnClickListener(this);

    }

    //删除字符串最前面的0，小数点前面没数则加0
    public void settext(String number) {
        if (number.equals("+") || number.equals("-") || number.equals("÷") || number.equals("x") || number.equals("=")) {
            if (!sign.equals("") && !number3.equals("")) {
                if (number3.equals("0")) {
                    text.setText("分母不能为0");
                } else {
                    calculation();
                    if (number.equals("=")) {
                        text.setText(number1 + sign + "         " + number2 + "\n" + number3);
                        number1 = number1 + sign + "         " + number2 + "\n";
                        sign = "";
                    } else {
                        number1 = number1 + sign + "         " + number2 + "\n";
                        sign = number;
                        number2 = number3;
                        number3 = "";
                        text.setText(number1 + sign + "         " + number2 + "\n");

                    }
                }

            } else {
                if (!number.equals("=")) {
                    if (!sign.equals("")) {

                        sign = number;
                        text.setText(number1 + sign + "         " + number2 + "\n");

                    } else {

                        number1 = number1 + sign + "         " + number2 + "\n";
                        sign = number;
                        number2 = number3;
                        number3 = "";
                        text.setText(number1 + sign + "         " + number2 + "\n");
                    }
                }
            }
        } else {
            if (number.equals(".")) {
                if (number3.equals("")) {
                    number3 = "0.";
                    text.setText(number1 + sign + "         " + number2 + "\n" + number3);
                } else {
                    number3 = number3 + ".";
                    text.setText(number1 + sign + "         " + number2 + "\n" + number3);
                }
            } else {
                number3 = number3 + number;
                text.setText(number1 + sign + "         " + number2 + "\n" + number3);
            }
        }
    }

    //	选择进行运算的符号
    public void calculation() {
        switch (sign) {
            case "+":
                bd_1 = new BigDecimal(number2);
                bd_2 = new BigDecimal(number3);
                number3 = String.valueOf(bd_1.add(bd_2));
                number3 = Mantissa(number3);

                break;
            case "-":
                bd_1 = new BigDecimal(number2);
                bd_2 = new BigDecimal(number3);
                number3 = String.valueOf(bd_1.subtract(bd_2));
                number3 = Mantissa(number3);

                break;
            case "x":
                bd_1 = new BigDecimal(number2);
                bd_2 = new BigDecimal(number3);
                number3 = String.valueOf(bd_1.multiply(bd_2));
                number3 = Mantissa(number3);

                break;
            case "÷":
                bd_1 = new BigDecimal(number2);
                bd_2 = new BigDecimal(number3);
                number3 = String.valueOf(bd_1.divide(bd_2,7, BigDecimal.ROUND_HALF_UP) );
                number3 = Mantissa(number3);

                break;
            default:
                break;
        }
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.one:
                settext("1");
                break;
            case R.id.two:
                settext("2");
                break;
            case R.id.three:
                settext("3");
                break;
            case R.id.four:
                settext("4");
                break;
            case R.id.five:
                settext("5");
                break;
            case R.id.six:
                settext("6");
                break;
            case R.id.seven:
                settext("7");
                break;
            case R.id.eight:
                settext("8");
                break;
            case R.id.nine:
                settext("9");
                break;
            case R.id.zero:
                settext("0");
                break;
            case R.id.dian:
                if (!number3.contains(".")) {
                    settext(".");
                }
                break;
            case R.id.ac:
                ac();
                break;
            case R.id.jia:
                settext("+");
                break;
            case R.id.jian:
                settext("-");
                break;
            case R.id.cheng:
                settext("x");
                break;
            case R.id.chu:
                settext("÷");
                break;
            case R.id.deng:
                settext("=");
                break;
            case R.id.menu:
                Intent intent = new Intent();
                intent.setClass(Calculator.this, MainActivity.class);
                startActivity(intent);
                Calculator.this.finish();
                break;

            default:
                break;
        }
    }

    public void ac() {

        number1 = "";
        number2 = "";
        number3 = "";
        sign = "";
        text.setText("");
    }

    //判断字符串是否以.0结尾，如果是.0结尾则删除
    public String Mantissa(String str) {
        double db = Double.valueOf(str);
        str = String.valueOf(db);
        if (str.length() > 2) {
            char a = str.charAt(str.length() - 1);
            char b = str.charAt(str.length() - 2);
            if (a == '0' && b == '.') {
                str = str.substring(0, str.length() - 2);
            }
        }
        return str;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.setClass(Calculator.this,MainActivity.class);
            startActivity(intent);
            Calculator.this.finish();
        }
        return true;
    }
}
