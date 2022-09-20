package com.example.hqwallpaper;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout errorLayout;
    private Button btnRetry;
    private RecyclerView rvCategories;
    private ArrayList<Category> categoryArrayList;
    private CategoryAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        errorLayout = view.findViewById(R.id.errorLayout);
        btnRetry = view.findViewById(R.id.btnRetry);
        rvCategories = view.findViewById(R.id.rvCategories);

        fetchCategoriesFromServer();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchCategoriesFromServer();
            }
        });
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCategoriesFromServer();
                errorLayout.setVisibility(View.GONE);
            }
        });

    }

    private void fetchCategoriesFromServer() {
        String categoriesURL = "https://androidworkshop.net/apis/pixabay/categories.php";

        if (!swipeRefresh.isRefreshing()){
            progressBar.setVisibility(View.VISIBLE);
        }

        StringRequest request = new StringRequest(categoriesURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                errorLayout.setVisibility(View.GONE);

                try {
                    Gson gson = new Gson();
                    JSONArray categoriesArray = new JSONArray(response);
                    categoryArrayList = new ArrayList<>();
                    categoryArrayList = gson.fromJson(categoriesArray.toString(), new TypeToken<ArrayList<Category>>(){}.getType());

                    rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new CategoryAdapter(categoryArrayList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Category selecteditem = categoryArrayList.get(position);
                            Intent categoryIntent = new Intent(getContext(), CategoryDetailActivity.class);
                            categoryIntent.putExtra("category", selecteditem);
                            startActivity(categoryIntent);

                        }
                    });
                    rvCategories.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    errorLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Unable to fetch data from Server", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (categoryArrayList == null){
                    progressBar.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                    swipeRefresh.setRefreshing(false);
                }
            }
        });
        Volley.newRequestQueue(getContext()).add(request);
    }
}