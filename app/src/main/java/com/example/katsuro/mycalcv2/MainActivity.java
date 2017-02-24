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
    private List<String> listOfResults;
    private String text;
    private String currentOperation;
    private String message;
    private String firstNumber;
    private String secondNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResults = (TextView) findViewById(R.id.textViewResults);
        listOfResults = new ArrayList<String>();
        text = EMPTY_STRING;
        currentOperation = EMPTY_STRING;
        firstNumber = EMPTY_STRING;
        secondNumber = EMPTY_STRING;
        sendMessage();
    }

    public void sendMessage()
    {
        if(listOfResults.isEmpty())
        {
            textViewResults.setText(text);
        }
        else if(listOfResults.size()==LEN_EQUALS_ONE)
        {
            textViewResults.setText(listOfResults.get(LEN_EQUALS_ZERO) + NEW_LINE + text);
        }
        else if(listOfResults.size()>LEN_EQUALS_ONE)
        {
            textViewResults.setText(listOfResults.get(listOfResults.size()-LEN_EQUALS_TWO) + NEW_LINE + listOfResults.get(listOfResults.size()-LEN_EQUALS_ONE) + NEW_LINE + text);
        }
    }

    public void buttonNumberClick(View view)
    {
        currentButton = (Button)view;
        if(currentOperation.isEmpty()) // jeśli aktualna operacja jest pusta
        {
            if(firstNumber.length()<NUMBER_LENGTH) // jeśli pierwsza liczba jest mniejsza od ustalonej wielkości("-" liczy sie do długości liczby)
            {
                if(firstNumber.length()==1 && (firstNumber.charAt(FIRST_CHAR) == CHAR_MINUS || firstNumber.charAt(FIRST_CHAR) == CHAR_ZERO) && currentButton.getId() == R.id.buttonZero)
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


    private double operate(String numberFirst, String numberSecond, String operation)
    {
        switch(operation)
        {
            case "+":
                return Double.valueOf(numberFirst) + Double.valueOf(numberSecond);
            case "-":
                return Double.valueOf(numberFirst) - Double.valueOf(numberSecond);
            case "×":
                return Double.valueOf(numberFirst) * Double.valueOf(numberSecond);
            case "÷":
                try
                {
                    return Double.valueOf(numberFirst) / Double.valueOf(numberSecond);
                }
                catch (Exception e)
                {
                    Log.d(TAG, e.getMessage());
                }
            default:
                return -1;
        }
    }

    private void clear()
    {
        currentOperation = EMPTY_STRING;
        text = EMPTY_STRING;
        firstNumber = EMPTY_STRING;
        secondNumber = EMPTY_STRING;
    }

    public void getResult()
    {
        text += EQUALS + String.valueOf(operate(firstNumber, secondNumber, currentOperation)); // wynik operacji
        Log.i(TAG,"Operation: " + firstNumber + currentOperation + secondNumber + EQUALS + String.valueOf(operate(firstNumber, secondNumber, currentOperation)));
        listOfResults.add(text); // dodanie wyniku do listy wyników
        clear(); // czyści zmienne
        sendMessage();
    }

    public void buttonEqualsClick(View view)
    {
        if(!firstNumber.isEmpty() && !secondNumber.isEmpty() && !currentOperation.isEmpty()) // jeśli firstNumber , SecondNumber i CurrentOperation nie są puste
        {
            getResult();
        }
        else
        {
            Log.i(TAG,"Do nothing");
        }
    }

    public void buttonArrowClick(View view)
    {
        if(!text.isEmpty())
        {
            if(!secondNumber.isEmpty())// jesli secondNumber nie jest pusty
            {
                secondNumber = secondNumber.substring(FIRST_CHAR, secondNumber.length()-LAST_CHAR); // usuwa jeden element z secondNumber
                text = text.substring(FIRST_CHAR, text.length()-LAST_CHAR);
            }
            else if(secondNumber.isEmpty() && !currentOperation.isEmpty()) // jesli secondNumber jest puste i currentOperation nie jest pusty
            {
                currentOperation = EMPTY_STRING; // zmienia currentOperation na pusty
                text = text.substring(FIRST_CHAR, text.length()-LAST_CHAR);
            }
            else if(secondNumber.isEmpty() && currentOperation.isEmpty() && !firstNumber.isEmpty())// jesli secondNumber i currentNumber są puste i firstNumber nie jest pusty
            {
                firstNumber = firstNumber.substring(FIRST_CHAR, firstNumber.length()-LAST_CHAR); // usuwa jeden element z firstNumber
                text = text.substring(FIRST_CHAR, text.length()-LAST_CHAR);
            }
            sendMessage();
        }
    }

    public void buttonClearClick(View view)
    {
        clear();
        sendMessage();
    }

    public void buttonListClick(View view)
    {
        Log.i(TAG,"Opening history.");
        message = EMPTY_STRING;
        Intent intent = new Intent(this, ListActivity.class);
        for(int result=0; result < listOfResults.size(); result++)
        {
            message += listOfResults.get(result) + NEW_LINE;
            message += NEW_LINE;
        }
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void buttonClearListClick(View view)
    {
        if(listOfResults.size()>0) // lista wynikow ma byc wieksza od 0
        {
            Log.i(TAG,"Deleting history.");
            listOfResults.clear();
            sendMessage();
        }
    }
}