package com.codeworks.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.codeworks.myapplication.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private GoogleApiClient mGoogleApiClient;
    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this.requireContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        ListView lvCategories = getView().findViewById(R.id.lvCategories);
        ArrayList<HashMap<String, String>> categoriesList = new ArrayList<>();
        final HashMap<String,String> categorie1 = new HashMap<>();
        categorie1.put("list_name_categorie","Alimentos");
        categoriesList.add(categorie1);
        final HashMap<String,String> categorie2 = new HashMap<>();
        categorie2.put("list_name_categorie","Aseo");
        categoriesList.add(categorie2);
        final HashMap<String,String> categorie3 = new HashMap<>();
        categorie3.put("list_name_categorie","Panadería y Dulces");
        categoriesList.add(categorie3);
        final HashMap<String,String> categorie4 = new HashMap<>();
        categorie4.put("list_name_categorie","Aperitivos y Licores");
        categoriesList.add(categorie4);
        final HashMap<String,String> categorie5 = new HashMap<>();
        categorie5.put("list_name_categorie","Huevos, Lácteos y Café");
        categoriesList.add(categorie5);
        final HashMap<String,String> categorie6 = new HashMap<>();
        categorie6.put("list_name_categorie","Aceite, Pasta y Legumbres");
        categoriesList.add(categorie6);
        final HashMap<String,String> categorie7 = new HashMap<>();
        categorie7.put("list_name_categorie","Conservas y Comida Preparada");
        categoriesList.add(categorie7);
        ListAdapter adapter = new SimpleAdapter(this.getContext(), categoriesList, R.layout.list_row_categorie,new String[]{"list_name_categorie"}, new int[]{R.id.list_name_categorie});
        lvCategories.setAdapter(adapter);
    }}