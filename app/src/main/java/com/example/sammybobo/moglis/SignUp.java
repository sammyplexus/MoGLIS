package com.example.sammybobo.moglis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sammybobo.moglis.interfaces.MoGLISWebInterface;
import com.example.sammybobo.moglis.models.SignUpModel;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func4;
import rx.schedulers.Schedulers;

/**
 * Created by Sammy bobo on 19/03/2016.
 */
public class SignUp extends Fragment {
    Button sign_up;
    EditText username;
    EditText password;
    EditText password2;
    EditText email_address;
    String _username;
    String _password;
    TextInputLayout til;
    String _email_address;
    final String base_url = "http://10.0.3.2/MoGLIS/";
    final String user_id = "user_id";
    final String user_name = "user_name";
    final String user_emailaddress = "user_emailaddress";
    final String user_fullname = "user_fullname";
    final String user_phonenumber = "user_phonenumber";
    TextInputLayout password_wrapper;
    Boolean confirm_password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    rx.Observable<CharSequence> username_;
    rx.Observable<CharSequence> password_;
    rx.Observable<CharSequence> password2_;
    rx.Observable<CharSequence> email_address_;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        sharedPreferences = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        v = inflater.inflate(R.layout.sign_up, container, false);
        password_wrapper = (TextInputLayout)v.findViewById(R.id.password_wrapper);
        sign_up = (Button) v.findViewById(R.id.button_sign_up);
        password2 = (EditText)v.findViewById(R.id.password_signup2);
        username = (EditText) v.findViewById(R.id.username_signup);
        password = (EditText) v.findViewById(R.id.password_signup);
        email_address = (EditText) v.findViewById(R.id.emailaddress_signup);
        til = (TextInputLayout) v.findViewById(R.id.til);
        username_ = RxTextView.textChanges(username);
        password_ = RxTextView.textChanges(password);
        password2_ = RxTextView.textChanges(password2);
        email_address_ = RxTextView.textChanges(email_address);



        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                _username = username.getText().toString();
                _password = password.getText().toString();
                _email_address = email_address.getText().toString();
                retro_observable(_username, _password, _email_address);
            }
        });
        return v;
    }


    private void retro_observable(String username, String password, String email_address) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(base_url)
                .build();
        MoGLISWebInterface service = retrofit.create(MoGLISWebInterface.class);


       rx.Observable<SignUpModel> a = service.sign_up_pojo("sign_up",username, password, email_address);

                a.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SignUpModel>()
                {

                    @Override
                    public void onCompleted() {
                        progressDialog.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.hide();
                        Log.d("Error", e.toString());
                    }

                    @Override
                    public void onNext(SignUpModel signUpModels) {
                        editor.putString(user_id, signUpModels.getUserId());
                        editor.putString(user_name, signUpModels.getUserName());
                        editor.putString(user_fullname, signUpModels.getFullName());
                        editor.putString(user_emailaddress,signUpModels.getEmailAddress());
                        editor.putString(user_phonenumber, signUpModels.getPhoneNumber());
                        editor.commit();

                        if(signUpModels.getUserId().length() > 0 )
                        {
                            Intent a = new Intent(getActivity(), MoGLISMaps.class);
                            startActivity(a);
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "There is something up here", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}

/*  rx.Observable.combineLatest(username_, password_, password2_, email_address_, new Func4<CharSequence, CharSequence,CharSequence, CharSequence, String>()
        {
            @Override
            public String call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) {
                Log.d("charSequence", charSequence.toString());
                Log.d("charSequence2", charSequence2.toString());
                Log.d("charSequence3", charSequence3.toString());
                Log.d("charSequence4", charSequence4.toString());

                confirm_password = validate_password(charSequence2, charSequence3);
                Log.d("Boolean password valid?", confirm_password.toString());
                String a = " ";
                if (charSequence2.length() > 6 && charSequence3.length() > 6)
                {
                    if (confirm_password == true)
                    {
                        sign_up.setEnabled(true);
                    }
                }
                else
                {
                    password_wrapper.setError("Your passwords do not match");
                    sign_up.setEnabled(false);
                }
                return a;
            }
        }).subscribe(new Observer<String>()
        {
            @Override
            public void onCompleted()
            {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });*/


