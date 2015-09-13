package com.mindvalley.mintrest.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.google.common.base.Strings;
import com.mindvalley.mintrest.R;
import com.mindvalley.mintrest.io.http.ApiClientFactory;
import com.mindvalley.mintrest.models.Product;
import com.mindvalley.requestqueue.Callback;
import com.mindvalley.requestqueue.Request;
import com.mindvalley.requestqueue.Response;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 */
public class ProductListItemBinder extends ListItemBinder<Product> {

    //TODO: Set image Ids
    int ERROR_IMAGE_ID = R.mipmap.ic_launcher;
    int EMPTY_IMAGE_ID = R.mipmap.ic_launcher;
    int LOADING_IMAGE_ID = R.mipmap.ic_launcher;

    public ProductListItemBinder(List<Product> items) {
        super(items);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_item;
    }

    @Override
    protected BaseHolder createHolder(View view) {
        return new ProductHolder(view);
    }

    @Override
    protected void setData(Context ctx, BaseHolder holder, Product item) {

        ProductHolder pH = (ProductHolder) holder;
        pH.txtDesc.setText(item.getData().getBrand());
        pH.txtTitle.setText(item.getData().getName());

        if (item.getDefaultImage() == null || Strings.isNullOrEmpty(item.getDefaultImage().getPath())) {
            pH.imgProduct.setImageResource(EMPTY_IMAGE_ID);
        }
        pH.imgProduct.setImageResource(LOADING_IMAGE_ID);

        Request<Bitmap> req = ApiClientFactory.getDefaultImageClient().get(item.getDefaultImage().getPath(), null);
        req.queue(new BitmapDisplayer(pH.imgProduct,item.getDefaultImage().getPath()));
    }

    class BitmapDisplayer implements Callback<Bitmap> {
        ImageView imageView;
        private String tag;

        public BitmapDisplayer(ImageView i, String t) {
            imageView = i;
            tag = t;
            i.setTag(t); // Tag of url set, so that we know when loading the image that this is the same imageview and is not recycled.
        }

        @Override
        public void onSuccess(Response<Bitmap> response) {

            // Strings are Immutable
            if(imageView.getTag() != tag) return;

            if (response.getBody() != null)
                imageView.setImageBitmap(response.getBody());
            else
                imageView.setImageResource(ERROR_IMAGE_ID);
        }

        @Override
        public void onError(Throwable t) {
            imageView.setImageResource(ERROR_IMAGE_ID);
        }
    }

    class ProductHolder extends BaseHolder {

        @Bind(R.id.title)
        public TextView txtTitle;

        @Bind(R.id.image)
        public DynamicHeightImageView imgProduct;

        @Bind(R.id.description)
        public TextView txtDesc;

        public ProductHolder(View view) {
            super();
            ButterKnife.bind(this, view);
        }
    }
}
