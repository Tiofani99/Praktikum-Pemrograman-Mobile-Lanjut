package com.tiooooo.academy.ui.bookmark;

import com.tiooooo.academy.data.CourseEntity;
import com.tiooooo.academy.data.source.AcademyRepository;
import com.tiooooo.academy.utils.DataDummy;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookmarkViewModelTest {

    private BookmarkViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule;

    @Mock
    AcademyRepository academyRepository;

    @Mock
    private Observer<List<CourseEntity>> observer;

    @Before
    public void setUp(){
        viewModel = new BookmarkViewModel(academyRepository);
    }

    @Test
    public void getBookmark(){
        ArrayList<CourseEntity> dummyCourses = DataDummy.generateDummyCourses();
        MutableLiveData<List<CourseEntity>> courses = new MutableLiveData<>();

        when(academyRepository.getBookmarkedCourses()).thenReturn(courses);
        List<CourseEntity> courseEntities = viewModel.getBookmarks().getValue();
        verify(academyRepository).getBookmarkedCourses();
        assertNotNull(courseEntities);
        assertEquals(5, courseEntities.size());

        viewModel.getBookmarks().observeForever(observer);
        verify(observer).onChanged(dummyCourses);
    }


}