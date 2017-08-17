package com.alekseysamoylov.widget;

import com.alekseysamoylov.widget.config.SpringBootWebApplication;
import com.alekseysamoylov.widget.service.FolderScannerAsyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * I didn't have enough time for writing test. But I can use TDD approach.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SpringBootWebApplication.class)
public class MainTest {

    @Autowired
    private FolderScannerAsyncService folderScannerAsyncService;

    @Test
    public void asyncTest() throws InterruptedException {
    }



}
