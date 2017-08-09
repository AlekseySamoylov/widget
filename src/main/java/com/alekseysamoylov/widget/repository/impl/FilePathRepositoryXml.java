package com.alekseysamoylov.widget.repository.impl;

import com.alekseysamoylov.widget.repository.FilePathRepository;
import com.alekseysamoylov.widget.service.FileChangeScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;

/**
 * Implementation {@link FilePathRepository}
 */
@Repository
public class FilePathRepositoryXml implements FilePathRepository {

    private final FileChangeScanner fileChangeScanner;

    @Autowired
    public FilePathRepositoryXml(FileChangeScanner fileChangeScanner) {
        this.fileChangeScanner = fileChangeScanner;
    }

    @Override
    public String findOne() {
        return fileChangeScanner.getFilePath();
    }

}
