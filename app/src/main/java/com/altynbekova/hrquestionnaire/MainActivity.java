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

    private static final String EMPTY_STRING = "";
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        List<RadioButton> radioButtons = List.of(
                binding.answer1, binding.answer2, binding.answer3,
                binding.answer4, binding.answer5
        );

        binding.submit.setOnClickListener(v -> {
            int candidateAge = Integer.parseInt(binding.age.getText().toString());
            int candidateSalary = (int)binding.salary.getValue();
            result.setVisibility(TextView.VISIBLE);
            result.setText(EMPTY_STRING);

            boolean valid = validateInputs(candidateAge, candidateSalary);
            if (!valid) return;

            int points = 0;
            for (RadioButton rb : radioButtons) {
                if (rb.isChecked()) points += 2;
            }

            if (binding.experience.isChecked()) points += 2;
            if (binding.skills.isChecked()) points += 1;
            if (binding.businessTrip.isChecked()) points += 1;
            if (points >= 10) {
                result.setText("Свяжитесь с нами по почте hr@example.com");
            } else {
                result.setText("Попробуйте еще раз!");
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enabled = true;
                if (binding.fio.getText().toString().isBlank() || binding.age.getText().toString().isBlank()) {
                    enabled = false;
                }

                if (binding.fio.getText().toString().isBlank()) {
                    binding.layoutFio.setError("ФИО не может быть пустым");
                }

                if (binding.age.getText().toString().isBlank()) {
                    binding.layoutAge.setError("Возраст не может быть пустым");
                }

                findViewById(R.id.submit).setEnabled(enabled);
            }
        };
        binding.fio.addTextChangedListener(textWatcher);
        binding.age.addTextChangedListener(textWatcher);

    }

    private boolean validateInputs(int age, int salary) {
        boolean valid = true;
        if(age < 21 || age>40){
            result.append("Вы не подошли по критерию Возраст");
            valid = false;
        }
        if(salary < 800 || salary>1600){
            result.append("\nВы не подошли по критерию Зарплата");
            valid = false;
        }
        return valid;
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