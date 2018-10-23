package com.example.test.mvvmsampleapp.viewmodel;

import android.app.Application;

import com.example.test.mvvmsampleapp.service.model.Project;
import com.example.test.mvvmsampleapp.service.repository.GitHubService;
import com.example.test.mvvmsampleapp.service.repository.ProjectRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectListViewModel extends AndroidViewModel {
    private final LiveData<List<Project>> projectListObservable;

    public ProjectListViewModel(@NonNull Application application) {
        super(application);

        GitHubService gitHubService = new Retrofit.Builder().baseUrl(GitHubService.HTTPS_API_GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubService.class);
        projectListObservable = ProjectRepository.getInstance(gitHubService).getProjectList("Google");
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<List<Project>> getProjectListObservable() {
        return projectListObservable;
    }
}
