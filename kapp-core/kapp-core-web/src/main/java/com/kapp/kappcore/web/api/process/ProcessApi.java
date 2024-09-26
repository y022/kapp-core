package com.kapp.kappcore.web.api.process;

import com.kapp.kappcore.ProcessService;
import com.kapp.kappcore.model.dto.process.ProcessDTO;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class ProcessApi {
    private final ProcessService processService;

    @GetMapping("/deploy")
    public void deploy(MultipartFile file) throws Exception {
        processService.deployment(file.getName(), file.getInputStream());
    }

    @GetMapping("queryAllProcess")
    public List<ProcessDTO> queryAllProcess() {
        return processService.queryAllProcess();
    }

    @GetMapping("/activeProcess")
    public void activeProcess(String processId) {
        processService.activeProcess(processId);
    }
}
