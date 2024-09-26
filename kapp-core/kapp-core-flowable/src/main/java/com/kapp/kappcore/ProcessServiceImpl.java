package com.kapp.kappcore;

import com.kapp.kappcore.model.dto.process.ProcessDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final HistoryService historyService;
    private static final String BPMN_FILE_SUFFIX = ".bpmn";

    @Override
    public void deployment(String name, InputStream inputStream) {
        Deployment deploy = repositoryService.createDeployment().addInputStream(name + BPMN_FILE_SUFFIX, inputStream).name(name).deploy();
        repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
    }

    @Override
    public List<ProcessDTO> queryAllProcess() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        return list.stream().map(item -> {
            ProcessDTO processDTO = new ProcessDTO();
            processDTO.setId(item.getId());
            processDTO.setName(item.getName());
            return processDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void activeProcess(String processId) {
        repositoryService.activateProcessDefinitionById(processId);
    }


}
