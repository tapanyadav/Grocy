package com.example.grocy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.grocy.Adapters.ShopsAdapter;
import com.example.grocy.Models.ShopsModel;
import com.example.grocy.R;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public class FilterActivity extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bottomSheetDialogFilter = inflater.inflate(R.layout.content_filter_bottom_sheet, container, false);
        ImageView ivBottomClose = bottomSheetDialogFilter.findViewById(R.id.imageView_close);
        assert ivBottomClose != null;
        ivBottomClose.setOnClickListener(closeView -> dismiss());
        SeekBar seekBar = bottomSheetDialogFilter.findViewById(R.id.seekBar);
        AtomicReference<String> selectedSeekBar = new AtomicReference<>("0");
        Button submit = bottomSheetDialogFilter.findViewById(R.id.submit);
        CheckBox[] check = new CheckBox[5];
        check[0] = bottomSheetDialogFilter.findViewById(R.id.groceryCheck);
        check[1] = bottomSheetDialogFilter.findViewById(R.id.stationaryCheck);
        check[2] = bottomSheetDialogFilter.findViewById(R.id.pharmacyCheck);
        check[3] = bottomSheetDialogFilter.findViewById(R.id.hardwareCheck);
        check[4] = bottomSheetDialogFilter.findViewById(R.id.fruitsAndVegCheck);
        List<String> selectedCatagories= new ArrayList();

        submit.setOnClickListener(printData -> {
            for(int i=0;i<5;i++){
                System.out.println(check[i].isChecked());
                if(check[i].isChecked()==true){
                    selectedCatagories.add(check[i].getText().toString());
                    System.out.println(check[i].getText().toString());
                }
            }

            Intent main_intent = new Intent(getActivity(), MainActivity.class);
            if(selectedCatagories.size()>0) {
                main_intent.putExtra("checkList", (Serializable) selectedCatagories);
            }
            main_intent.putExtra("rating", seekBar.getProgress()+1);
            startActivity(main_intent);

            selectedSeekBar.set("" + seekBar.getProgress());
            Toast.makeText(getActivity(), "SeekBar value: " + selectedSeekBar.toString(), Toast.LENGTH_LONG).show();



            dismiss();
        });

        return bottomSheetDialogFilter;
    }

}