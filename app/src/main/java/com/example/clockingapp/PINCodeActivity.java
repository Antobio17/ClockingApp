package com.example.clockingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;


public class PINCodeActivity extends AppCompatActivity {

    public static int PIN_CODE_LENGTH = 4;
    private int[] pinCode = new int[PIN_CODE_LENGTH];
    private int digitsCount = 0;

    private Button codeBox1;
    private Button codeBox2;
    private Button codeBox3;
    private Button codeBox4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode);

        codeBox1 = findViewById(R.id.outlinedButton1);
        codeBox2 = findViewById(R.id.outlinedButton2);
        codeBox3 = findViewById(R.id.outlinedButton3);
        codeBox4 = findViewById(R.id.outlinedButton4);

        codeBox1.setClickable(false);
        codeBox2.setClickable(false);
        codeBox3.setClickable(false);
        codeBox4.setClickable(false);
    }

    public void pressButton0(View view) {
        _addDigit(0);
    }

    public void pressButton1(View view) {
        _addDigit(1);
    }

    public void pressButton2(View view) {
        _addDigit(2);
    }

    public void pressButton3(View view) {
        _addDigit(3);
    }

    public void pressButton4(View view) {
        _addDigit(4);
    }

    public void pressButton5(View view) {
        _addDigit(5);
    }

    public void pressButton6(View view) {
        _addDigit(6);
    }

    public void pressButton7(View view) {
        _addDigit(7);
    }

    public void pressButton8(View view) {
        _addDigit(8);
    }

    public void pressButton9(View view) {
        _addDigit(9);
    }

    public void pressButtonDelete(View view) {
        digitsCount--;
        _updateCodeBoxes();
    }

    public void pressButtonCheck(View view) {
        if (digitsCount < 4) {
            Snackbar.make(
                    view, "Por favor introduzca los 4 dígitos del PIN",
                    Snackbar.LENGTH_LONG
            ).setAction("Action", null).show();
        } else {
            int result = pinCode[0] * 1000 + pinCode[1] * 100 + pinCode[2] * 10 + pinCode[3];

            setResult(result);
            this.finish();
        }
    }

    public void pressButtonCancel(View view) {
        setResult(RESULT_CANCELED);
        this.finish();
    }

    private void _addDigit(int digit) {
        if (digitsCount < 4) {
            pinCode[digitsCount] = digit;
            digitsCount++;
        }
        _updateCodeBoxes();
    }

    private void _updateCodeBoxes() {
        codeBox1.setText("");
        codeBox2.setText("");
        codeBox3.setText("");
        codeBox4.setText("");
        if (digitsCount == 1) {
            codeBox1.setText("*");
        } else if (digitsCount == 2) {
            codeBox1.setText("*");
            codeBox2.setText("*");
        } else if (digitsCount == 3) {
            codeBox1.setText("*");
            codeBox2.setText("*");
            codeBox3.setText("*");
        } else if (digitsCount == 4) {
            codeBox1.setText("*");
            codeBox2.setText("*");
            codeBox3.setText("*");
            codeBox4.setText("*");
        }
    }
}