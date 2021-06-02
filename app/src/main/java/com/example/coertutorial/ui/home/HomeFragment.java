package com.example.coertutorial.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coertutorial.HomePage;
import com.example.coertutorial.LoginActivity;
import com.example.coertutorial.R;
import com.example.coertutorial.SingleTask;
import com.example.coertutorial.adapter.ModuleAdapter;
import com.example.coertutorial.dao.MyLocalDatabase;
import com.example.coertutorial.model.Module;
import com.example.coertutorial.model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private SharedPreferences sp;
    private MyLocalDatabase myLocalDatabase;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SingleTask singleTask = (SingleTask) getActivity().getApplication();
        sp = singleTask.getSharedPreferences();

        myLocalDatabase = new MyLocalDatabase(getActivity());
        HomePage homePage = (HomePage) getActivity();
        user = homePage.getUser();

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.myrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
             //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dummyModule();
        ModuleAdapter moduleAdapter = new ModuleAdapter(list);
        moduleAdapter.setOnPriyaListener(new ModuleAdapter.PriyaListener() {
            @Override
            public void clickOnItem(View view, int position) {
                //Toast.makeText(getActivity(), list.get(position).getName(), Toast.LENGTH_SHORT).show();
                String name = list.get(position).getName();
                NavController navController = Navigation.findNavController(view);
                if (name.equalsIgnoreCase("about us")) {

                    navController.navigate(R.id.action_nav_home_to_nav_gallery);

                }else if (name.equalsIgnoreCase("contact us")) {

                    navController.navigate(R.id.action_nav_home_to_nav_slideshow);

                }else if (name.equalsIgnoreCase("profile")) {

                    navController.navigate(R.id.action_nav_home_to_nav_profile);

                }else if (name.equalsIgnoreCase("Delete Account")) {

                    confirmMessage();

                }else if (name.equalsIgnoreCase("logout")) {

                    SharedPreferences.Editor e = sp.edit();
                    e.remove("status");
                    e.commit();
                    Intent in = new Intent(getActivity(), LoginActivity.class);
                    startActivity(in);
                    getActivity().finish();
                }
            }
        });

        recyclerView.setAdapter(moduleAdapter);

      }

      private void confirmMessage(){
          AlertDialog.Builder a =new AlertDialog.Builder(getActivity());
          a.setTitle("Deactivate Account");
          a.setMessage("Are you sure ?");
          a.setCancelable(false);
          a.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                 long t = myLocalDatabase.deleteAccount(user.getEmail());
                 if (t>0){
                     Toast.makeText(getActivity(), "Successfully Deleted Account", Toast.LENGTH_SHORT).show();
                     SharedPreferences.Editor e = sp.edit();
                     e.remove("status");
                     e.commit();
                     Intent in = new Intent(getActivity(),LoginActivity.class);
                     startActivity(in);
                     getActivity().finish();
                 }else{
                     Toast.makeText(getActivity(), "Account Deleted is Failed", Toast.LENGTH_SHORT).show();
                 }
              }
          });
          a.setNegativeButton("No", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.dismiss();
              }
          });
          a.show();
      }

        List<Module> list;

        private void dummyModule() {
            list = new ArrayList<>();
            Module m1 = new Module();
            m1.setName("About us");
            m1.setImage(R.drawable.ic_menu_camera);

            Module m2 = new Module();
            m2.setName("Contact us");
            m2.setImage(R.drawable.ic_menu_gallery);

            Module m3 = new Module();
            m3.setName("Profile");
            m3.setImage(R.drawable.ic_menu_camera);

            Module m5 = new Module();
            m5.setName("Delete Account");
            m5.setImage(R.drawable.ic_menu_camera);

            Module m4 = new Module();
            m4.setName("Logout");
            m4.setImage(R.drawable.ic_menu_manage);

            list.add(m1);
            list.add(m2);
            list.add(m3);
            list.add(m5);
            list.add(m4);
        }
    }