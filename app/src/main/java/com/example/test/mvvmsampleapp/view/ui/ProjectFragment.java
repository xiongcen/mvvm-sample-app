package com.example.test.mvvmsampleapp.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.mvvmsampleapp.R;
import com.example.test.mvvmsampleapp.service.model.Project;
import com.example.test.mvvmsampleapp.viewmodel.ProjectViewModel;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ProjectFragment extends Fragment {
    private static final String KEY_PROJECT_ID = "project_id";
    private TextView mTextView;
    private LinearLayout mContainer;
    private TextView mName;
    private TextView mProjectDesc;
    private TextView mLanguages;
    private TextView mProjectWatchers;
    private TextView mProjectOpenIssues;
    private TextView mProjectCreatedAt;
    private TextView mProjectUpdatedAt;
    private TextView mCloneUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_details, null);
        mTextView = view.findViewById(R.id.loading_project);
        mContainer = view.findViewById(R.id.container);
        mName = view.findViewById(R.id.name);
        mProjectDesc = view.findViewById(R.id.project_desc);
        mLanguages = view.findViewById(R.id.languages);
        mProjectWatchers = view.findViewById(R.id.project_watchers);
        mProjectOpenIssues = view.findViewById(R.id.project_open_issues);
        mProjectCreatedAt = view.findViewById(R.id.project_created_at);
        mProjectUpdatedAt = view.findViewById(R.id.project_updated_at);
        mCloneUrl = view.findViewById(R.id.clone_url);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ProjectViewModel viewModel = ViewModelProviders.of(this)
                .get(ProjectViewModel.class);

        viewModel.setProjectID(getArguments().getString(KEY_PROJECT_ID));

        observeViewModel(viewModel);
    }

    private void observeViewModel(final ProjectViewModel viewModel) {
        // Observe project data
        viewModel.getObservableProject().observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@Nullable Project project) {
                if (project != null) {
                    mTextView.setVisibility(View.GONE);
                    mContainer.setVisibility(View.VISIBLE);
                    mName.setText(project.name);
                    mProjectDesc.setText(project.description);
                    mLanguages.setText(String.format(getString(R.string.languages), project.language));
                    mProjectWatchers.setText(String.format(getString(R.string.watchers), project.watchers));
                    mProjectOpenIssues.setText(String.format(getString(R.string.openIssues), project.open_issues));
                    mProjectCreatedAt.setText(String.format(getString(R.string.created_at), project.created_at));
                    mProjectUpdatedAt.setText(String.format(getString(R.string.updated_at), project.updated_at));
                    mCloneUrl.setText(String.format(getString(R.string.clone_url), project.clone_url));
                }
            }
        });
    }

    /** Creates project fragment for specific project ID */
    public static ProjectFragment forProject(String projectID) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();

        args.putString(KEY_PROJECT_ID, projectID);
        fragment.setArguments(args);

        return fragment;
    }
}
