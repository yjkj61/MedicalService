package com.example.medicalservice.fragments.goodDetailChildView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.medicalservice.R;
import com.example.medicalservice.activity.shopChildView.GoodDetailView;
import com.example.medicalservice.baseFile.BaseFragment;
import com.example.medicalservice.databinding.FragmentGoodsIntroduceBinding;
import com.example.medicalservice.tools.GlideUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodsIntroduceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsIntroduceFragment extends BaseFragment<FragmentGoodsIntroduceBinding> {

    private String url;

    private GoodsIntroduceFragment() {

    }


    public static GoodsIntroduceFragment newInstance(String params) {
        GoodsIntroduceFragment fragment = new GoodsIntroduceFragment();
        Bundle args = new Bundle();
        args.putString("params", params);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString("params");
        }
    }


    @Override
    public void initView() {
        super.initView();

        Log.d("TAG", "GoodsIntroduceFragment: " + url);

        List<String> imageList = new ArrayList<>();
        imageList.add(url);

        ImageAdapter imageAdapter = new ImageAdapter(imageList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);

        viewBinding.imageList.setLayoutManager(linearLayoutManager);

        viewBinding.imageList.setAdapter(imageAdapter);



        //GlideUtils.load(activity, url, viewBinding.image);
    }

    @Override
    public void initData() {
        super.initData();
    }


    class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

        private List<String> imageUrl;

        public ImageAdapter(List<String> imageUrl) {
            this.imageUrl = imageUrl;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item_full, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String url = imageUrl.get(position);

            GlideUtils.load(activity, url, holder.image);
        }

        @Override
        public int getItemCount() {
            return imageUrl.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }
}