package com.geeks.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultView;
    private String currentInput = "";
    private String lastOperator = "";
    private double firstValue = 0;
    private boolean isNewOperation = true;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = findViewById(R.id.resultView);

        setButtonListeners();
    }

    private void setButtonListeners() {
        int[] numberButtons = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(this::onNumberClick);
        }

        findViewById(R.id.buttonPlus).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.buttonMinus).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.buttonMultiply).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.buttonDivide).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.buttonEqual).setOnClickListener(this::onEqualClick);
        findViewById(R.id.buttonClear).setOnClickListener(v -> clear());
        findViewById(R.id.buttonDot).setOnClickListener(v -> addDot());
        findViewById(R.id.buttonPlusMinus).setOnClickListener(v -> toggleSign());
        findViewById(R.id.buttonPercent).setOnClickListener(v -> applyPercent());
    }

    private void onNumberClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String number = button.getText().toString();

        if (isNewOperation) {
            currentInput = number;
            isNewOperation = false;
        } else {
            currentInput += number;
        }

        resultView.setText(currentInput);
    }

    private void onOperatorClick(View view) {
        MaterialButton button = (MaterialButton) view;
        lastOperator = button.getText().toString();
        firstValue = Double.parseDouble(currentInput);
        isNewOperation = true;
    }

    private void onEqualClick(View view) {
        if (lastOperator.isEmpty()) return;

        double secondValue = Double.parseDouble(currentInput);
        double result = 0;

        switch (lastOperator) {
            case "+":
                result = firstValue + secondValue;
                break;
            case "-":
                result = firstValue - secondValue;
                break;
            case "X":
                result = firstValue * secondValue;
                break;
            case "/":
                result = secondValue != 0 ? firstValue / secondValue : 0;
                break;
        }

        resultView.setText(String.valueOf(result));
        currentInput = String.valueOf(result);
        isNewOperation = true;
    }

    private void clear() {
        currentInput = "0";
        firstValue = 0;
        lastOperator = "";
        isNewOperation = true;
        resultView.setText(currentInput);
    }

    private void addDot() {
        if (!currentInput.contains(".")) {
            currentInput += ".";
            resultView.setText(currentInput);
        }
    }

    private void toggleSign() {
        if (!currentInput.equals("0")) {
            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1);
            } else {
                currentInput = "-" + currentInput;
            }
            resultView.setText(currentInput);
        }
    }

    private void applyPercent() {
        if (!lastOperator.isEmpty()) {
            double percentValue = Double.parseDouble(currentInput) / 100;

            switch (lastOperator) {
                case "+":
                case "-":
                    currentInput = String.valueOf(firstValue * percentValue);
                    break;
                case "X":
                    currentInput = String.valueOf(firstValue * percentValue);
                    break;
                case "/":
                    currentInput = String.valueOf(firstValue / percentValue);
                    break;
            }

            resultView.setText(currentInput);
        } else {
            // Если оператора нет, просто делим число на 100
            double value = Double.parseDouble(currentInput) / 100;
            currentInput = String.valueOf(value);
            resultView.setText(currentInput);
        }
    }
}
