package com.example.tcp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    Button mButton;
    EditText mEdit;
    String usr_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This assumes we already have the message from the user
        mEdit = (EditText) findViewById(R.id.input_txt);
        mButton = (Button) findViewById(R.id.btn_input);
        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        System.out.println("[BUTTON]: WOOHOO");
                        usr_input = mEdit.getText().toString();
                        System.out.println("[BUTTON]: USER INPUT AFTER LOOP: " + usr_input);
                        System.out.println("[BUTTON]: EXECUTING MIDDLE MAN");
                        String[] cmd = usr_input.split(" ");
                        new middleMan().execute(cmd);
                    }
                }
        );

    }

    private class middleMan extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params){
            try {
                System.out.println("CLIENT: MAKE A PURCHASE IN TCP IN BACKGROUND");

                String hostAddress = "192.168.0.6";//"10.0.2.2"; //"localhost";
                int tcpPort = 8080;
                String returned = tcpSend(params, hostAddress, tcpPort);
                System.out.println(returned);
                return(returned);
            } catch(Exception e) {
                System.err.println(e);
                return("Fail");
            }
        }
        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private String tcpSend(String[] tokens, String hostAddress,int tcpPort) throws IOException{
        try {
            System.out.println("CLIENT INITIALIZING ON PORT " + tcpPort);
            System.out.println("[CLIENT] " + hostAddress + " " + tcpPort);
            Socket server = new Socket(hostAddress, tcpPort);
            System.out.println("[CLIENT]: SOCKET CONNECTED.");
            PrintStream pout = new PrintStream(server.getOutputStream());
            Scanner din = new Scanner(server.getInputStream());
            String message = makeMessage(tokens);
            System.out.println("CLIENT:SENDING: " + message);
            pout.println(message);
            pout.flush();
            // TODO - Make this a scanner so we can get all the necesary informaton from the server
            //int retValue = din.nextInt();
            //String retValStr = String.valueOf(retValue);
            System.out.println("DIN: " + din.hasNext());
            while (!din.hasNext()) {
            }
            System.out.println("DIN: " + din.hasNext());
            //THE RESPONSE FROM THE SERVER ISNT COMING IN
            String retValStr = din.nextLine();
            while (din.hasNextLine()) {
                retValStr += "\n" + din.nextLine();
            }
            din.close();
            server.close();
            return retValStr;
        }catch(Exception e){
            System.err.println(e);
            e.printStackTrace();
            return "fail";
        }
    }

    private static String makeMessage(String[] tokens){
        String msg = new String("");
        int i=0;
        for (i=0;i<tokens.length-1;i++){
            msg = msg+ tokens[i] +" ";
        }
        msg = msg+tokens[i];
        return msg;
    }
}
