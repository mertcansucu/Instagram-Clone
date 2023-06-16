package com.mertcansucu.instagramclonee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mertcansucu.instagramclonee.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();//giriş-çıkış işlemleri için

        FirebaseUser user = auth.getCurrentUser();//kullanıcı daha önce giriş yaptıysa uygulamayı kapatıp açtığında tekrar giriş yapmasın direk uygulamaya girsin istersem bu kodu eklemeliyim
        if(user != null){
            Intent intent = new Intent(MainActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();
        }



    }

    public void signInClick(View view){//kayıtlı kişi ile giriş yapmak
        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();


        if(email.equals("") || password.equals("")){//eğer email veya password boş girersem bana hata veriyor
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_LONG).show();
        }else {//ikisini de girersem
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {//kullanıcı girişi başarılıysa
                    Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {//eğer kullanıcı girişi olmadıysa, nedenini direk fairbaseden öğreneceğim şekilde hata mesajı aldım
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                }
            });

        }

    }

    public void signUpClicked(View view){//yeni kayıt

        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();

        if(email.equals("") || password.equals("")){//eğer email veya password boş girersem bana hata veriyor
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_LONG).show();
        }else{//ikisini de girersem ve dikkat et şifre en az 6 haneli olmalı
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {//eğer kayıt oluştuysa bu metotun içine girecek
                    Intent intent = new Intent(MainActivity.this,FeedActivity.class);//kayıt tamamlanınca beni istediğim ekrana götürüyor
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {//eğer kayıt oluşmadıysa nedenini direk fairbaseden öğreneceğim şekilde hata mesajı aldım
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}