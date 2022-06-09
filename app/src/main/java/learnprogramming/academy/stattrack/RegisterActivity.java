package learnprogramming.academy.stattrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, email, password, confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.usernameRegistrationId);
        email=findViewById(R.id.emailRegistrationId);
        password=findViewById(R.id.usernameLoginId);
        confirmpassword=findViewById(R.id.passwordLoginId);

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
            UserDatabase.getInstance(this).userDao().addUser(user);
            Intent intent=new Intent(this,LoginActivity.class);
            //intent.putExtra("profilePhoto",profilePhoto);
            startActivity(intent);
            Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent2);
        }

    }

    public void goToLogin(View view){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}