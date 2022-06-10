package learnprogramming.academy.stattrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class RegisterFragment extends Fragment {
    Activity activity;
    private EditText username, email, password, confirmpassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment_layout, container, false);
        activity = getActivity();


        username = view.findViewById(R.id.usernameRegistrationId);
        email = view.findViewById(R.id.emailRegistrationId);
        password = view.findViewById(R.id.usernameLoginId);
        confirmpassword = view.findViewById(R.id.passwordLoginId);

        Button button = (Button) view.findViewById(R.id.RegisterButtonREGISTERId);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUpProfile(view);
            }
        });

        return view;
    }



    private Boolean validateUsername() {
        String validate = username.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (validate.isEmpty()) {
            username.setError("You must enter a username");
            return false;
        } else if (validate.length() >= 20) {
            username.setError("Username cannot have more than 20 characters");
            return false;
        } else if (!validate.matches(noWhiteSpace)) {
            username.setError("White Spaces are not allowed");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String validate = email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (validate.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!validate.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String validate = password.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (validate.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!validate.matches(passwordVal)) {
            password.setError("Password is too weak");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
    public Boolean validateConfirmPassword()
    {
        String validate=confirmpassword.getText().toString();
        String pass=password.getText().toString();
        if(!validate.matches(pass))
        {
            confirmpassword.setError("Passwords are not the same");
            return false;
        }
        if (validate.isEmpty()) {
            confirmpassword.setError("Field cannot be empty");
            return false;
        }
        else {
            confirmpassword.setError(null);
            return true;
        }

    }



    public void setUpProfile(View view) {

        if(!validateUsername() | !validateEmail() | !validatePassword() | !validateConfirmPassword()){
            return;
        }
        else
        {
            User user=new User(username.getText().toString(),email.getText().toString(),password.getText().toString());
            UserDatabase.getInstance(activity).userDao().addUser(user);
            Intent intent=new Intent(activity, MainActivity.class);
            intent.putExtra("username",username.getText());
            intent.putExtra("password",password.getText());
            intent.putExtra("buttonVisibility", AppCompatButton.INVISIBLE);
            Toast.makeText(activity, "Successfully registered", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            //Intent intent=new Intent(activity,LoginFragment.class);
            //intent.putExtra("profilePhoto",profilePhoto);
            //startActivity(intent);
            //Intent intent2 = new Intent(activity, LoginFragment.class);
            //startActivity(intent2);
        }

    }



}
