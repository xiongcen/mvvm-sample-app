package com.example.test.mvvmsampleapp.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.mvvmsampleapp.R;
import com.example.test.mvvmsampleapp.service.model.Project;
import com.example.test.mvvmsampleapp.view.adapter.ProjectAdapter;
import com.example.test.mvvmsampleapp.view.callback.ProjectClickCallback;
import com.example.test.mvvmsampleapp.viewmodel.ProjectListViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectListFragment extends Fragment {
    public static final String TAG = "ProjectListFragment";
    private TextView mTextView;
    private LinearLayout mContainer;
    private RecyclerView mRecyclerView;
    private ProjectAdapter projectAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list, null);
        mTextView = view.findViewById(R.id.loading_projects);
        mContainer = view.findViewById(R.id.container);
        mRecyclerView = view.findViewById(R.id.project_list);
        projectAdapter = new ProjectAdapter(projectClickCallback);
        mRecyclerView.setAdapter(projectAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ProjectListViewModel viewModel = ViewModelProviders.of(this).get(ProjectListViewModel.class);

        observeViewModel(viewModel);
    }

    private void observeViewModel(ProjectListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getProjectListObservable().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                if (projects != null) {
                    mTextView.setVisibility(View.GONE);
                    mContainer.setVisibility(View.VISIBLE);
                    projectAdapter.setProjectList(projects);
                }
            }
        });
    }

    private final ProjectClickCallback projectClickCallback = new ProjectClickCallback() {
        @Override
        public void onClick(Project project) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(project);
            }
        }
    };
}
