package com.example.sammybobo.moglis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sammybobo.moglis.interfaces.MoGLISWebInterface;
import com.example.sammybobo.moglis.models.SignUpModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Sammy bobo on 26/02/2016.
 */
public class SignIn extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener
{

    Button button;
    SignInButton signInButton;

    private static final int RC_SIGN_IN = 9001;
    GoogleApiClient mGoogleApiClient;
    EditText username;
    EditText password;
    String _username;
    String _password;
    String base_url = "http://10.0.3.2/MoGLIS/";
    final String user_id = "user_id";
    final String user_name = "user_name";
    final String user_emailaddress = "user_emailaddress";
    final String user_fullname = "user_fullname";
    final String user_phonenumber = "user_phonenumber";
    MoGLISWebInterface inter;
    ProgressDialog progressDialog;
    Observable<SignUpModel> signIn;
    Retrofit retrofit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result)
    {
        if (result.isSuccess())
        {
            GoogleSignInAccount _result = result.getSignInAccount();
            Log.d("Details", _result.getDisplayName() + " " + _result.getId() + " " + _result.getEmail() + " " + _result.getPhotoUrl());
        }
        else
        {
            Snackbar.make(getView(), "No google sign-in men",  Snackbar.LENGTH_LONG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view;
        view = inflater.inflate(R.layout.sign_in, container, false);
        sharedPreferences = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        signInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       inter = retrofit.create(MoGLISWebInterface.class);

        button = (Button)view.findViewById(R.id.button_login);
        username = (EditText)view.findViewById(R.id.username_login);
        password = (EditText)view.findViewById(R.id.password_login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                _username = username.getText().toString();
                _password = password.getText().toString();

                signIn = inter.sign_in_pojo("signin", _username, _password);
                signIn.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<SignUpModel>()
                        {
                            @Override
                            public void onCompleted() {
                                progressDialog.hide();
                            }

                            @Override
                            public void onError(Throwable e)
                            {
                                progressDialog.hide();
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(SignUpModel signUpModel)
                            {
                                String returned = signUpModel.getEmailAddress();
                                    editor.putString(user_id, signUpModel.getUserId());
                                    editor.putString(user_name, signUpModel.getUserName());
                                    editor.putString(user_fullname, signUpModel.getFullName());
                                    editor.putString(user_emailaddress,signUpModel.getEmailAddress());
                                    editor.putString(user_phonenumber, signUpModel.getPhoneNumber());
                                    editor.commit();
                                    Intent a = new Intent();
                                    a.setClass(getActivity(), MoGLISMaps.class);
                                    startActivity(a);
                                    Log.d("Result", returned);
                                if(signUpModel.getUserId().length() > 0 )
                                {
                                    Intent ab = new Intent(getActivity(), MoGLISMaps.class);
                                    startActivity(ab);
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "There is something up here", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

       mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
                return view;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        Log.d("Connection failed", "Yep! Connection failed! to google's servers");
    }

    private void sign_in(View view)
    {
        Intent googleIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(googleIntent, RC_SIGN_IN );
    }

    }
