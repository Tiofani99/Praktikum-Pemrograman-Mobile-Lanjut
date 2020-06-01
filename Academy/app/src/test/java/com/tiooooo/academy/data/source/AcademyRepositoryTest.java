package com.tiooooo.academy.data.source;

import com.tiooooo.academy.data.CourseEntity;
import com.tiooooo.academy.data.ModuleEntity;
import com.tiooooo.academy.data.source.remote.RemoteDataSource;
import com.tiooooo.academy.data.source.remote.response.ContentResponse;
import com.tiooooo.academy.data.source.remote.response.CourseResponse;
import com.tiooooo.academy.data.source.remote.response.ModuleResponse;
import com.tiooooo.academy.utils.DataDummy;
import com.tiooooo.academy.utils.LiveDataTestUtil;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcademyRepositoryTest {
    private RemoteDataSource remote = Mockito.mock(RemoteDataSource.class);
    private FakeAcademyRepository academyRepository = new FakeAcademyRepository(remote);

    private ArrayList<CourseResponse> courseResponses = (ArrayList<CourseResponse>) DataDummy.generateRemoteDummyCourses();
    private String courseId = courseResponses.get(0).getId();
    private ArrayList<ModuleResponse> moduleResponses = (ArrayList<ModuleResponse>) DataDummy.generateRemoteDummyModules(courseId);
    private String moduleId = moduleResponses.get(0).getModuleId();
    private ContentResponse content = DataDummy.generateRemoteDummyContent(moduleId);

    @Test
    public void getAllCourses() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));
        List<CourseEntity> courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllCourses());
        verify(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));
        assertNotNull(courseEntities);
        assertEquals(courseResponses.size(), courseEntities.size());
    }

    @Test
    public void getAllModulesByCourse() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadModulesCallback) invocation.getArguments()[1])
                    .onAllModulesReceived(moduleResponses);
            return null;
        }).when(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModulesCallback.class));

        List<ModuleEntity> courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId));

        verify(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModulesCallback.class));

        assertNotNull(courseEntities);
        assertEquals(moduleResponses.size(), courseEntities.size());
    }

    @Test
    public void getBookmarkedCourses() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));

        List<CourseEntity> courseEntities = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourses());

        verify(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));

        assertNotNull(courseEntities);
        assertEquals(courseResponses.size(), courseEntities.size());
    }

    @Test
    public void getContent() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadModulesCallback) invocation.getArguments()[1])
                    .onAllModulesReceived(moduleResponses);
            return null;
        }).when(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModulesCallback.class));

        doAnswer(invocation -> {
            ((RemoteDataSource.LoadContentCallback) invocation.getArguments()[1])
                    .onContentReceived(content);
            return null;
        }).when(remote).getContent(eq(moduleId), any(RemoteDataSource.LoadContentCallback.class));

        ModuleEntity courseEntitiesContent = LiveDataTestUtil.getValue(academyRepository.getContent(courseId, moduleId));

        verify(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModulesCallback.class));

        verify(remote).getContent(eq(moduleId), any(RemoteDataSource.LoadContentCallback.class));

        assertNotNull(courseEntitiesContent);
        assertNotNull(courseEntitiesContent.contentEntity);
        assertNotNull(courseEntitiesContent.contentEntity.getContent());
        assertEquals(content.getContent(), courseEntitiesContent.contentEntity.getContent());
    }


    @Test
    public void getCourseWithModules() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));

        CourseEntity courseEntities = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId));

        verify(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));

        assertNotNull(courseEntities);
        assertNotNull(courseEntities.getTitle());
        assertEquals(courseResponses.get(0).getTitle(), courseEntities.getTitle());
    }
}