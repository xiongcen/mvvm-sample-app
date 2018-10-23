package com.example.test.mvvmsampleapp.viewmodel;

import android.app.Application;
import android.util.Log;

import com.example.test.mvvmsampleapp.service.model.Project;
import com.example.test.mvvmsampleapp.service.repository.GitHubService;
import com.example.test.mvvmsampleapp.service.repository.ProjectRepository;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectViewModel extends AndroidViewModel {
    private static final String TAG = ProjectViewModel.class.getName();
    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    private final LiveData<Project> projectObservable;
    private final MutableLiveData<String> projectID;

    public ObservableField<Project> project = new ObservableField<>();

    public ProjectViewModel(@NonNull Application application) {
        super(application);

        this.projectID = new MutableLiveData<>();

        projectObservable = Transformations.switchMap(projectID, input -> {
            if (input.isEmpty()) {
                Log.i(TAG, "ProjectViewModel projectID is absent!!!");
                return ABSENT;
            }

            Log.i(TAG,"ProjectViewModel projectID is " + projectID.getValue());
            GitHubService gitHubService = new Retrofit.Builder().baseUrl(GitHubService.HTTPS_API_GITHUB_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GitHubService.class);
            return ProjectRepository.getInstance(gitHubService).getProjectDetails("Google", projectID.getValue());
        });
    }

    public LiveData<Project> getObservableProject() {
        return projectObservable;
    }

    public void setProject(Project project) {
        this.project.set(project);
    }

    public void setProjectID(String projectID) {
        this.projectID.setValue(projectID);
    }
}
