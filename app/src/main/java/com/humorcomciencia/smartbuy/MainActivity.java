package com.humorcomciencia.smartbuy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blockInputMethods();
        installFocusChangeListeners();
        installOnClickListeners();
    }

    int activeEdit = -1;

    private void installOnClickListeners() {
        installClickListenerOnSingleButton(R.id.btn0);
        installClickListenerOnSingleButton(R.id.btn1);
        installClickListenerOnSingleButton(R.id.btn2);
        installClickListenerOnSingleButton(R.id.btn3);
        installClickListenerOnSingleButton(R.id.btn4);
        installClickListenerOnSingleButton(R.id.btn5);
        installClickListenerOnSingleButton(R.id.btn6);
        installClickListenerOnSingleButton(R.id.btn7);
        installClickListenerOnSingleButton(R.id.btn8);
        installClickListenerOnSingleButton(R.id.btn9);
        installDeleteBtn();
        installProxBtn();
        installCalcularBtn();
    }

    private void installCalcularBtn(){
        Button btn = (Button) findViewById(R.id.btnCalcular);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = checkFields();
                if(str.equals("")){
                    calcular();
                } else{
                    // ------------
                    LinearLayout layout=new LinearLayout(getBaseContext());
                    layout.setBackgroundColor(Color.WHITE);
                    TextView tv = new TextView(getBaseContext());
                    // set the TextView properties like color, size etc
                    tv.setTextColor(Color.RED);
                    tv.setTextSize(25);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    // set the text you want to show in  Toast
                    // ---------
                    Context context = getApplicationContext();
                    CharSequence text = "Faltou preencher: "+str+".";
                    tv.setText(text);
                    layout.addView(tv);
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = new Toast(getBaseContext());
                    toast.setView(layout);
                    toast.setGravity(Gravity.BOTTOM, 0, 50);
                    toast.show();
                }
            }
        });
    }

    private void calcular() {
        double quantidadeA = 0.0, quantidadeB = 0.0, valorA = 0.0, valorB = 0.0;
        EditText edit = (EditText) findViewById(R.id.qtdeA);
        quantidadeA = Double.parseDouble(edit.getText().toString());
        edit = (EditText) findViewById(R.id.qtdeB);
        quantidadeB = Double.parseDouble(edit.getText().toString());
        edit = (EditText) findViewById(R.id.valorA);
        valorA = Double.parseDouble(edit.getText().toString());
        edit = (EditText) findViewById(R.id.valorB);
        valorB = Double.parseDouble(edit.getText().toString());

        double resultadoA = valorA/quantidadeA, resultadoB = valorB/quantidadeB;

        if(resultadoA == resultadoB){
            System.out.println("RESULTADO >>> mesma coisa...");
        }else if(resultadoA > resultadoB){
            System.out.println("RESULTADO >>> A ta mais caro, compra B...");
        }else{ // resultadoB > resultadoA
            System.out.println("RESULTADO >>> B ta mais caro, compra A...");
        }
    }

    private String checkFields() {
        String str = "";
        EditText edit = (EditText) findViewById(R.id.qtdeA);
        if(edit.getText().toString().equals("Quantidade A")){
            str += " 'Quantidade A'";
        }
        edit = (EditText) findViewById(R.id.qtdeB);
        if(edit.getText().toString().equals("Quantidade B")){
            if(str.length() > 1)
                str += ", 'Quantidade B'";
            else
                str += " 'Quantidade B'";
        }
        edit = (EditText) findViewById(R.id.valorA);
        if(edit.getText().toString().equals("Valor A")){
            if(str.length() > 1)
                str += ", 'Valor A'";
            else
                str += " 'Valor A'";
        }
        edit = (EditText) findViewById(R.id.valorB);
        if(edit.getText().toString().equals("Valor B")){
            if(str.length() > 1)
                str += " e 'Valor B'";
            else
                str += " 'Valor B'";
        }
        return str;
    }

    private void installProxBtn(){
        Button btn = (Button) findViewById(R.id.btnProx);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( activeEdit != -1 ){
                    //EditText edit = (EditText) findViewById(activeEdit);
                    //edit.clearFocus();
                    int activateId = getNextId(activeEdit);
                    EditText edit = (EditText) findViewById(activateId);
                    edit.requestFocus();
                }
            }
        });
    }

    private int getNextId(int id){
        if(id == R.id.valorA){
            return R.id.qtdeA;
        } else if (id == R.id.qtdeA) {
            return R.id.valorB;
        } else if(id == R.id.valorB) {
            return R.id.qtdeB;
        } else return R.id.valorA;
    }

    private void installDeleteBtn() {
        Button btn = (Button) findViewById(R.id.btnDelete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( activeEdit != -1 ){
                    EditText activeEditField = (EditText) findViewById(activeEdit);
                    nullifyText(activeEditField);
                    String value = activeEditField.getText().toString();
                    String subValue = "";
                    if(!value.equals("")){
                        subValue = value.substring(0,value.length()-1);
                    }else{
                        return;
                    }
                    activeEditField.setText(subValue);
                }
            }
        });

    }

    private void installClickListenerOnSingleButton(int btnId){
        Button btn = (Button) findViewById(btnId);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( activeEdit != -1 && (view.getId() != R.id.btnCalcular ||
                        view.getId() != R.id.btnDelete ||
                        view.getId() != R.id.btnProx)){
                    EditText activeEditField = (EditText) findViewById(activeEdit);
                    nullifyText(activeEditField);
                    activeEditField.append(getStringWithValueForId(view.getId()));
                }
            }
        });
    }

    private String getStringWithValueForId(int id){
        String str="";
        switch (id){
            case R.id.btn0:
                str += "0";
                break;
            case R.id.btn1:
                str += "1";
                break;
            case R.id.btn2:
                str += "2";
                break;
            case R.id.btn3:
                str += "3";
                break;
            case R.id.btn4:
                str += "4";
                break;
            case R.id.btn5:
                str += "5";
                break;
            case R.id.btn6:
                str += "6";
                break;
            case R.id.btn7:
                str += "7";
            break;
            case R.id.btn8:
                str += "8";
            break;
            case R.id.btn9:
                str += "9";
            break;
            default:
                break;
        }
        return str;
    }

    private void blockInputMethods(){
        EditText edit = (EditText) findViewById(R.id.qtdeA);
        edit.setKeyListener(null);
        edit = (EditText) findViewById(R.id.qtdeB);
        edit.setKeyListener(null);
        edit = (EditText) findViewById(R.id.valorA);
        edit.setKeyListener(null);
        edit = (EditText) findViewById(R.id.valorB);
        edit.setKeyListener(null);
    }

    private void installFocusChangeListeners(){
        addFocusListener(R.id.qtdeA);
        addFocusListener(R.id.qtdeB);
        addFocusListener(R.id.valorA);
        addFocusListener(R.id.valorB);
    }

    private void addFocusListener(int id){
        EditText edit = (EditText) findViewById(id);
        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    view.getBackground().mutate().setColorFilter(Color.parseColor("#22aa22"), PorterDuff.Mode.ADD);
                    activeEdit = view.getId();
                } else {
                    view.getBackground().mutate().setColorFilter(Color.parseColor("#2c3e50"), PorterDuff.Mode.ADD);
                    String str = ((EditText)view).getText().toString();
                    if(str.equals("")){
                        restoreDefaultValue((EditText) view);
                    }
                }
            }
        });
    }

    private void restoreDefaultValue(EditText edit){
        if(activeEdit == R.id.valorA){
            edit.setText("Valor A");
        }else if(activeEdit == R.id.valorB){
            edit.setText("Valor B");
        }else if(activeEdit == R.id.qtdeA){
            edit.setText("Quantidade A");
        }else{
            edit.setText("Quantidade B");
        }
    }

    private void nullifyText(EditText view) {
        String str = view.getText().toString();
        if(str.equals("Valor A") || str.equals("Valor B") || str.equals("Quantidade A") || str.equals("Quantidade B")){
            view.setText("");
        }
    }

}
