package com.mindvalley.mintrest;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.etsy.android.grid.StaggeredGridView;
import com.google.gson.reflect.TypeToken;
import com.mindvalley.mintrest.io.http.Api;
import com.mindvalley.mintrest.io.http.ApiClientFactory;
import com.mindvalley.mintrest.io.http.ApiFactory;
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
import retrofit.Call;
import retrofit.Callback;

public class MainActivity extends ActionBarActivity implements PullRefreshLayout.OnRefreshListener {


    @Bind(R.id.txt_header)
    TextView txtHeader;

    @Bind(R.id.swipeRefreshLayout)
    PullRefreshLayout layout;

    @Bind(R.id.grid_view)
    StaggeredGridView gridView;


    private ListItemBinder<Product> binder;
    SimpleListAdapter<Product> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        binder = new ProductListItemBinder(new ArrayList<Product>()); // List should be accessible from within the ListBinder.
        adapter = new SimpleListAdapter<>(this, binder);
        gridView.setAdapter(adapter);
        layout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        layout.setOnRefreshListener(this);

        loadProducts();
    }

    private void loadProducts1() {

        Call<WebResponse<ProductListResponse>> req = ApiFactory.getApi().getWomensClothing();
        layout.setRefreshing(true);
        req.enqueue(new Callback<WebResponse<ProductListResponse>>() {
            @Override
            public void onResponse(retrofit.Response<WebResponse<ProductListResponse>> response) {

                layout.setRefreshing(false);
                binder.setItems(response.body().getMetadata().getResults());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Api", t.getMessage());
                layout.setRefreshing(false);
                // TODO: Set error view
                Snackbar
                        .make(MainActivity.this.gridView, R.string.error_unknown, Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void loadProducts() {

        Request<WebResponse<ProductListResponse>> req = ApiClientFactory
                .getDefaultJsonApiClient()
                .get(Api.WOMENS_CLOTHING, new TypeToken<WebResponse<ProductListResponse>>() {
                }.getType());

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
                Log.e("Api", t.getMessage());
                layout.setRefreshing(false);
                // TODO: Set error view
                Snackbar
                        .make(MainActivity.this.gridView, R.string.error_unknown, Snackbar.LENGTH_LONG)
                        .show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_refresh) {
            loadProducts();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        loadProducts();
    }
}
