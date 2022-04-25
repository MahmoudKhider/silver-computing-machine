package com.example.handelarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
 Button button;
 EditText urltext;
 ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        urltext=findViewById( R.id.editText );
        imageView=findViewById( R.id.imageView );
        button=findViewById( R.id.button );
        Handler handler= new Handler(){
            @Override
            public void handleMessage(@NonNull Message m){
                super.handleMessage( m );
                Bitmap image =(Bitmap) m.obj;
                imageView.setImageBitmap( image );
                Toast.makeText( MainActivity.this, "ImageDown", Toast.LENGTH_SHORT ).show();
            }
        };
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run(){
                        super.run();
                        Bitmap result =null;
                        try {
                            if (!urltext.getText().toString().isEmpty()){
                                URL url=new URL(urltext.getText().toString());
                                HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
                               connection.connect();
                               result=BitmapFactory.decodeStream( connection.getInputStream() );
                               Message message=new Message();
                               message.obj=result;
                               handler.sendMessage( message );
                            }
                        }catch (MalformedURLException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        } );
    }
}