package com.kapp.kappcore;

import com.kapp.kappcore.model.dto.process.ProcessDTO;

import java.io.InputStream;
import java.util.List;

public interface ProcessService {
    void deployment(String name, InputStream inputStream);

    List<ProcessDTO> queryAllProcess();

    void activeProcess(String processId);
}
