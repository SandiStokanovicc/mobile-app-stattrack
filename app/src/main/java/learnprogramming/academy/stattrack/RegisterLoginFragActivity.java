package learnprogramming.academy.stattrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class RegisterLoginFragActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login_frag);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        Bundle extras = getIntent().getExtras();

        /*
        if(extras!=null){
            Bundle bundle = new Bundle();
            bundle.putString("edittext", "From Activity");
            // set Fragmentclass Arguments
            LoginFragment fragobj = new LoginFragment();
            fragobj.setArguments(bundle);
        }*/


        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        viewPager = findViewById(R.id.fragment_container);

        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);
        setUpAdapter(viewPager);
    }

    public void setUpAdapter(ViewPager2 viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPagerAdapter.addFragment(new LoginFragment());
        viewPagerAdapter.addFragment(new RegisterFragment());
        viewPager.setUserInputEnabled(false);

        viewPager.setAdapter(viewPagerAdapter);
    }

    public  BottomNavigationView.OnItemSelectedListener onItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case (R.id.nav_login):
                    viewPager.setCurrentItem(0);
                    return true;
                case (R.id.nav_register):
                    viewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };
}