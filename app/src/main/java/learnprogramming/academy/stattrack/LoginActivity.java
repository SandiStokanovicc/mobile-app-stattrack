package learnprogramming.academy.stattrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText usernameLoginId,passwordLoginId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameLoginId=findViewById(R.id.usernameLoginId);
        passwordLoginId=findViewById(R.id.passwordLoginId);
    }
    public void LogIn(View v)
    {
        UserDao userDao=UserDatabase.getInstance(this).userDao();
        User user= userDao.login(usernameLoginId.getText().toString(),passwordLoginId.getText().toString());
        String username= user.getUsername();
        String password=user.getPassword();
        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        startActivity(intent);
    }

    public void goToRegister(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

}