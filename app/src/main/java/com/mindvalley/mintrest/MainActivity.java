package com.mindvalley.mintrest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.baoyz.widget.PullRefreshLayout;
import com.etsy.android.grid.StaggeredGridView;
import com.google.gson.reflect.TypeToken;
import com.mindvalley.mintrest.io.http.Api;
import com.mindvalley.mintrest.io.http.ApiClientFactory;
import com.mindvalley.mintrest.io.http.response.ProductListResponse;
import com.mindvalley.mintrest.io.http.response.WebResponse;
import com.mindvalley.mintrest.models.Product;
import com.mindvalley.mintrest.views.adapters.ListItemBinder;
import com.mindvalley.mintrest.views.adapters.ProductListItemBinder;
import com.mindvalley.mintrest.views.adapters.SimpleListAdapter;
import com.mindvalley.requestqueue.Request;
import com.mindvalley.requestqueue.Response;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ActionBarActivity implements PullRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {


    @Bind(R.id.swipeRefreshLayout)
    PullRefreshLayout layout;

    @Bind(R.id.grid_view)
    StaggeredGridView gridView;

    Request<WebResponse<ProductListResponse>> req;
    SimpleListAdapter<Product> adapter;
    private ListItemBinder<Product> binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        binder = new ProductListItemBinder(new ArrayList<Product>()); // List is accessible from within the ListBinder.
        adapter = new SimpleListAdapter<>(this, binder);
        gridView.setAdapter(adapter);
        layout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        layout.setOnRefreshListener(this);
        gridView.setOnItemClickListener(this);

        // The type inference is bit dirty which can be fixed by moving these method definitions and there return types else were
        req = ApiClientFactory
                .getDefaultJsonApiClient()
                .get(Api.WOMENS_CLOTHING, new TypeToken<WebResponse<ProductListResponse>>() {
                }.getType());
    }

    private void loadProducts() {


        // TODO: show that request can be cancelled
        layout.setRefreshing(true);
        req.queue(new com.mindvalley.requestqueue.Callback<WebResponse<ProductListResponse>>() {
            @Override
            public void onSuccess(Response<WebResponse<ProductListResponse>> response) {
                layout.setRefreshing(false);
                binder.setItems(response.getBody().getMetadata().getResults());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("Api", t.getMessage() + "");
                layout.setRefreshing(false);
                // TODO: Set error view
                Snackbar
                        .make(MainActivity.this.gridView, R.string.error_unknown, Snackbar.LENGTH_LONG)
                        .show();

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        req.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            //TODO: Open Dialog giving a basic introduction to the app
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        loadProducts();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(binder.getItem(position).getData().getUrl().replace("/mobile-api", "")));
        startActivity(browserIntent);
    }
}
