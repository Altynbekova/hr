package com.altynbekova.hrquestionnaire;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.altynbekova.hrquestionnaire.databinding.ActivityMainBinding;
import com.google.android.material.slider.Slider;

import java.util.List;
import java.util.OptionalInt;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        RadioButton answer1 = findViewById(R.id.answer1);
        RadioButton answer2 = findViewById(R.id.answer2);
        RadioButton answer3 = findViewById(R.id.answer3);
        RadioButton answer4 = findViewById(R.id.answer4);
        RadioButton answer5 = findViewById(R.id.answer5);
        CheckBox experience = findViewById(R.id.experience);
        CheckBox skills = findViewById(R.id.skills);
        CheckBox businessTrip = findViewById(R.id.businessTrip);
        EditText age = findViewById(R.id.age);
        Slider salary = findViewById(R.id.salary);
        List<RadioButton> radioButtons = List.of(
                answer1, answer2, answer3,
                answer4, answer5
        );

        findViewById(R.id.submit).setOnClickListener(v -> {
            int candidateAge = Integer.parseInt(age.getText().toString());
            int candidateSalary = (int)salary.getValue();
            TextView result = findViewById(R.id.result);
            result.setVisibility(TextView.VISIBLE);

//            validateInputs(candidateAge, salary);
            if(candidateAge < 21 || candidateAge>40){
                result.setText("Вы не подошли по критерию Возраст");
                return;
            }
            if(candidateSalary < 800 || candidateSalary>1600){
                result.setText("Вы не подошли по критерию Зарплата");
                return;
            }

            int points = 0;

            for (RadioButton rb : radioButtons) {
                if (rb.isChecked()) points += 2;
            }

            if (experience.isChecked()) points += 2;
            if (skills.isChecked()) points += 1;
            if (businessTrip.isChecked()) points += 1;
            if (points >= 10) {
                result.setText("Свяжитесь с нами по почте hr@example.com");
            } else {
                result.setText("Попробуйте еще раз!");
            }
        });

        EditText fio = findViewById(R.id.fio);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (fio.getText().toString().isBlank() || age.getText().toString().isBlank()) {
                    findViewById(R.id.submit).setEnabled(false);
                } else {
                    findViewById(R.id.submit).setEnabled(true);
                }
            }
        };
        fio.addTextChangedListener(textWatcher);
        age.addTextChangedListener(textWatcher);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}