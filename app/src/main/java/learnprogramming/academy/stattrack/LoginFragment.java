package learnprogramming.academy.stattrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {
    Activity activity;
    EditText usernameLoginId,passwordLoginId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment_layout, container, false);
        activity = getActivity();
        usernameLoginId=view.findViewById(R.id.usernameLoginId);
        passwordLoginId=view.findViewById(R.id.passwordLoginId);

        Button button = (Button) view.findViewById(R.id.RegisterButtonREGISTERId);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LogIn(view);
            }
        });


        return view;
    }

    public void LogIn(View v)
    {
        UserDao userDao=UserDatabase.getInstance(activity).userDao();
        User user = userDao.login(usernameLoginId.getText().toString(),passwordLoginId.getText().toString());
        String username = user.getUsername();
        String password = user.getPassword();
        Intent intent=new Intent(activity, MainActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        intent.putExtra("buttonVisibility", AppCompatButton.INVISIBLE);
        startActivity(intent);
    }
}
