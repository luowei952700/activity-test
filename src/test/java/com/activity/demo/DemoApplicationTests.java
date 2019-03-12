package com.activity.demo;


import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
//    public static final String DB_SCHEMA_UPDATE_TRUE = "true";//不能自动创建表，需要表存在
//    public static final String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";//先删除表再创建表
//    public static final String DB_SCHEMA_UPDATE_TRUE = "true";//如果表不存在，自动创建表
    @Test
    public void contextLoads() {
    }

    @Test
    public void testActivity(){
       /* ProcessEngine创建方式：
        1 使用代码创建工作流需要的23张表*/
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        //连接数据库的配置
        processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
        processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activity?useUnicode=true&characterEncoding=utf8");
        processEngineConfiguration.setJdbcUsername("root");
        processEngineConfiguration.setJdbcPassword("110");
        //  public static final String DB_SCHEMA_UPDATE_FALSE = "false";不能自动创建表，需要表存在;
        //  public static final String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";//先删除表再创建表
        //  public static final String DB_SCHEMA_UPDATE_TRUE = "true";//如果表不存在，自动创建表
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //工作流的核心对象，ProcessEnginee对象
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
        //2 /**使用配置文件创建工作流需要的23张表*/
      /* ProcessEngine processEngine2 = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml")   //
                .buildProcessEngine();*/
       // 3
        //ProcessEngine processEngine3 = ProcessEngines.getDefaultProcessEngine();
    }

    @Test
    public void testActivityTwo(){//使用配置文件创建工作流需要的23张表
        ProcessEngine processEngine2 = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
                .buildProcessEngine();

    }
    @Autowired
    ProcessEngine processEngine;
    @Test
    public void testActivityTherr(){//使用默认方式创建23张表
         processEngine =ProcessEngines.getDefaultProcessEngine();
        testActivityFour();
    }
    //发布 流程
   @Test
    public void testActivityFour(){//管理流程定义 是Activiti的仓库服务类。所谓的仓库指流程定义文档的两个文件
        RepositoryService repositoryService=processEngine.getRepositoryService();//
                                        //根据id发布
        repositoryService.createDeployment().name("aaa").addClasspathResource("processes/aaa.bpmn").deploy();
    }
    @Autowired
    ProcessEngine  processEngine2 ;
    //启动流程
    @Test
    public void testActivityFive(){
        RuntimeService runtimeService =processEngine2.getRuntimeService();
                                    //根据id 和key 启动
        runtimeService.startProcessInstanceByKey("aaa");

    }

    //查看任务
    @Test
    public void testActivitySix(){
        TaskService taskService = processEngine2.getTaskService();
        String assignee ="emp";
        List<Task> tasks=taskService.createTaskQuery().taskAssignee(assignee).list();
        int size = tasks.size();
        for (int i =0;i<tasks.size();i++){
            Task task=tasks.get(i);
        }
        for (Task task:
        tasks) {
            System.out.println("taskId"+task.getId()+"taskName"+task.getName()
                                +"assignee"+task.getAssignee()+"createTime"+task.getCreateTime());
        }
    }
    //办理任务
    @Test
    public void testActivityServer(){
        TaskService taskService =processEngine2.getTaskService();
        String id="12502";
        taskService.complete(id);

    }

    //查看流程定义
    @Test
    public  void liucheg(){

        List<ProcessDefinition> processDefinitions=processEngine2.getRepositoryService().createProcessDefinitionQuery().orderByDeploymentId().asc().list();
        processDefinitions.forEach(System.out::println);

    }


}
