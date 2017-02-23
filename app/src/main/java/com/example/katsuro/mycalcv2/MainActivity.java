package com.example.katsuro.mycalcv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = "MyCalculator";
    public final static String EXTRA_MESSAGE = "com.example.katsuro.mycalv2.MESSAGE";
    private final static Character CHAR_MINUS = '-';
    private final static Character CHAR_MULTIPLY = '×';
    private final static Character CHAR_DIVISION = '÷';
    private final static Character CHAR_ZERO = '0';
    private final static String EMPTY_STRING = "";
    private final static String NEW_LINE ="\n";
    private final static String EQUALS = "=";
    private final static int LEN_EQUALS_ZERO = 0;
    private final static int LEN_EQUALS_ONE = 1;
    private final static int LEN_EQUALS_TWO = 2;
    private final static int NUMBER_LENGTH = 11;
    private final static int FIRST_CHAR = 0;
    private final static int LAST_CHAR = 1;
    private final static int TWO_LAST_CHARS = 2;
    private Button currentButton;
    private TextView textViewResults;
    private String text;
    private String currentOperation;
    private String firstNumber;
    private String secondNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResults = (TextView) findViewById(R.id.textViewResults);
        text = EMPTY_STRING;
        currentOperation = EMPTY_STRING;
        firstNumber = EMPTY_STRING;
        secondNumber = EMPTY_STRING;
        sendMessage();
    }

    public void buttonNumberClick(View view)
    {
        currentButton = (Button)view;
        if(currentOperation.isEmpty()) // jeśli aktualna operacja jest pusta
        {
            if(firstNumber.length()<NUMBER_LENGTH) // jeśli pierwsza liczba jest mniejsza od ustalonej wielkości("-" liczy sie do długości liczby)
            {
                if(firstNumber.length()==LEN_EQUALS_ONE && (firstNumber.charAt(FIRST_CHAR) == CHAR_MINUS || firstNumber.charAt(FIRST_CHAR) == CHAR_ZERO) && currentButton.getId() == R.id.buttonZero)
                // jeśli pierwsza liczba ma długość 1 i pierwszy znak to - lub 0 i aktualny button to 0
                {
                    Log.i(TAG,"Do nothing");
                }
                else
                {
                    firstNumber += currentButton.getText(); // dodawanie wartości buttona do pierwszej liczby
                    text += currentButton.getText();
                }
            }
            else
            {
                Log.i(TAG,"Do nothing");
            }
        }
        else if(!currentOperation.isEmpty()) // jeśli aktualna operacja nie jest pusta
        {
            if(secondNumber.length()<NUMBER_LENGTH) // jeśli druga liczba jest mniejsza od ustalonej wielkości("-" liczy sie do długości liczby)
            {
                if(secondNumber.length()==LEN_EQUALS_ONE && (secondNumber.charAt(FIRST_CHAR) == CHAR_MINUS || secondNumber.charAt(FIRST_CHAR) == CHAR_ZERO) && currentButton.getId() == R.id.buttonZero)
                {
                    Log.i(TAG,"Do nothing");
                }
                else
                {
                    secondNumber += currentButton.getText(); // dodawanie wartości buttona do pierwszej liczby
                    text += currentButton.getText();
                }
            }
            else
            {
                Log.i(TAG,"Do nothing");
            }
        }
        sendMessage();
    }

    public static boolean isNumeric(String str) // sprawdzenie czy liczba jest liczbą (wartość true)
    {
        try
        {
            double testNumber = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public void buttonOperationClick(View view) // funkcja wyboru operacji
    {
        currentButton = (Button)view;

        if(currentOperation.isEmpty()) // jeśli obecna operacja jest pusta
        {
            if(firstNumber.isEmpty() && currentButton.getId() == R.id.buttonMinus) // jeśli pierwsza liczba jest pusta i aktualny button to minus
            {
                firstNumber += currentButton.getText(); // dodajemy minus do pierwszej liczby
                text += currentButton.getText();
                sendMessage();
            }
            else if(isNumeric(firstNumber)) // jeśli pierwsza liczba jest liczbą nawet minusową
            {
                currentOperation = currentButton.getText().toString(); // aktualna operacja to aktualny button
                text += currentButton.getText();
                sendMessage();
            }
            else
            {
                Log.i(TAG,"Do nothing");
            }
        }
        else if(!currentOperation.isEmpty()) // jeśli obecna operacja nie jest pusta
        {
            if(secondNumber.isEmpty() && (currentOperation.charAt(FIRST_CHAR) == CHAR_MULTIPLY || currentOperation.charAt(FIRST_CHAR) == CHAR_DIVISION) && currentButton.getId() == R.id.buttonMinus)
            // jesli druga liczba jest pusta i obecna operacja jest "*" lub "/" i aktualny button to -
            {
                secondNumber += currentButton.getText(); // dodajemy minus do drugiej liczby
                text += currentButton.getText();
                sendMessage();
            }
            else if(secondNumber.isEmpty() && (currentButton.getId() == R.id.buttonPlus || currentButton.getId() == R.id.buttonMinus ||
                    currentButton.getId() == R.id.buttonDivision || currentButton.getId() == R.id.buttonMultiply))
                // jesli druga liczba jest pusta i aktualny button jest + lub - lub / lub *
            {
                currentOperation = currentButton.getText().toString();//aktualna operacja to aktualny button
                text = text.substring(FIRST_CHAR, text.length() - LAST_CHAR);//wycina text bez currentOperation(ostatniego znaku) np. 2+ zmienia na 2-
                text += currentButton.getText();// i wkleja obecny znak działania
                sendMessage();
            }
            else if(secondNumber.length()==LEN_EQUALS_ONE && secondNumber.charAt(FIRST_CHAR) == CHAR_MINUS && (currentButton.getId() == R.id.buttonPlus ||
                    currentButton.getId() == R.id.buttonDivision || currentButton.getId() == R.id.buttonMultiply))
                // jeśli długość secondNumber jest równe 1 i znak w secondNumber to "-" i aktualny button to + lub / lub *
            {
                currentOperation = currentButton.getText().toString(); // aktualna operacja to aktualny button
                secondNumber = EMPTY_STRING; // kasuje minusa w secondNumber
                text = text.substring(FIRST_CHAR, text.length() - TWO_LAST_CHARS); // wycina z text 2 ostatnie znaki np. 2*- na 2+ (tylko dla mnożenia i dzielenia)
                text += currentButton.getText();
                sendMessage();
            }
            else
            {
                Log.i(TAG,"Do nothing");
            }
        }
    }
}