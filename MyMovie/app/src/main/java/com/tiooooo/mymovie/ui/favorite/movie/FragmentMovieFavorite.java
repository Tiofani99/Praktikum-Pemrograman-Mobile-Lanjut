package com.tiooooo.mymovie.ui.favorite.movie;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tiooooo.mymovie.R;
import com.tiooooo.mymovie.data.local.entitiy.Movie;
import com.tiooooo.mymovie.ui.favorite.FavoriteFragmentCallback;
import com.tiooooo.mymovie.ui.main.movie.MovieViewModel;
import com.tiooooo.mymovie.viewmodel.ViewModelFactory;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentMovieFavorite extends Fragment implements FavoriteFragmentCallback {

    private PagedListMovieAdapter adapter;

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.tv_information)
    TextView tvInformationData;
    @BindView(R.id.shimmerFrameLayout)
    ShimmerFrameLayout shimmerFrameLayout;
    MovieFavoriteViewModel viewModel;

    public FragmentMovieFavorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (getActivity() != null) {
            initAdapter();
            showLoading(true);
            getMovies();
        }
    }

    private void initAdapter() {
        rvMovies.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new PagedListMovieAdapter(this);
        adapter.notifyDataSetChanged();
    }

    private void showLoading(Boolean state) {
        if (state) {
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmer();
        } else {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    private void getMovies() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(MovieFavoriteViewModel.class);
        showLoading(true);
        viewModel.getMovieFavorite().observe(getViewLifecycleOwner(),movies -> {
            showLoading(false);
            adapter.submitList(movies);
            adapter.notifyDataSetChanged();
        });

        rvMovies.setAdapter(adapter);


    }


}
