package com.example.grocy.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.grocy.Adapters.CardAdapter;
import com.example.grocy.Models.CardModel;
import com.example.grocy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class CardActivity extends AppCompatActivity {
    static final Pattern CODE_PATTERN = Pattern.compile("([0-9]{0,4})|([0-9]{4}-)+|([0-9]{4}-[0-9]{0,4})+");
    String a;
    int keyDel;
    Button buttonPay;
    EditText editTextCard;
    private ViewPager2 viewPager2SavedCard;
    private Handler sliderHandler = new Handler();
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2SavedCard.setCurrentItem(viewPager2SavedCard.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        viewPager2SavedCard = findViewById(R.id.pagerSavedCard);
        editTextCard = findViewById(R.id.card_number);
        buttonPay = findViewById(R.id.pay_button);


        Toolbar toolbar = findViewById(R.id.card_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        List<CardModel> cardModelArrayList = new ArrayList<>();
        cardModelArrayList.add(new CardModel(R.drawable.rupay, R.drawable.visa_logo, "5612", "4884", "Tapan Yadav", "10 / 24"));
        cardModelArrayList.add(new CardModel(R.drawable.master, R.drawable.mastercardlogo, "5624", "4271", "Prashant Bhardwaj", "08 / 28"));
        cardModelArrayList.add(new CardModel(R.drawable.rupay, R.drawable.visa_logo, "5646", "4848", "Utkarsh Gupta", "04 / 26"));
        cardModelArrayList.add(new CardModel(R.drawable.visa, R.drawable.mastercardlogo, "5672", "4684", "Utkarsh Raghu", "01 / 22"));

        viewPager2SavedCard.setAdapter(new CardAdapter(cardModelArrayList, viewPager2SavedCard));

        viewPager2SavedCard.setClipToPadding(false);
        viewPager2SavedCard.setClipChildren(false);
        viewPager2SavedCard.setOffscreenPageLimit(3);
        viewPager2SavedCard.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager2SavedCard.setPageTransformer(compositePageTransformer);

        viewPager2SavedCard.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 4000);
            }

        });

        editTextCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0 && !CODE_PATTERN.matcher(s).matches()) {
                    String input = s.toString();
                    String numbersOnly = keepNumbersOnly(input);
                    String code = formatNumbersAsCode(numbersOnly);

                    Log.w("", "numbersOnly" + numbersOnly);
                    Log.w("", "code" + code);

                    editTextCard.removeTextChangedListener(this);
                    editTextCard.setText(code);
                    // You could also remember the previous position of the cursor
                    editTextCard.setSelection(code.length());
                    editTextCard.addTextChangedListener(this);
                }
            }

            private String keepNumbersOnly(CharSequence s) {
                return s.toString().replaceAll("[^0-9]", ""); // Should of course be more robust
            }

            private String formatNumbersAsCode(CharSequence s) {
                int groupDigits = 0;
                String tmp = "";
                for (int i = 0; i < s.length(); ++i) {
                    tmp += s.charAt(i);
                    ++groupDigits;
                    if (groupDigits == 4) {
                        tmp += "-";
                        groupDigits = 0;
                    }
                }
                return tmp;
            }
        });

        buttonPay.setOnClickListener(v -> {
            if (editTextCard != null) {
                String string = editTextCard.getText().toString().substring(0, 1);


                if (string.equals("4")) {
                    editTextCard.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_email, 0);
                } else if (string.equals("5")) {
                    editTextCard.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_password, 0);
                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }
}