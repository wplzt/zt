import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class InitialTest {

    //流程引擎
    private ProcessEngine engine;
    @Test
    public void init(){
        ProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("yy2512891195");
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        //如果数据库不存在就创建数据库.
        configuration.setDatabaseSchemaUpdate("true");
        engine = configuration.buildProcessEngine();
    }
    @Test
    public  void init2(){
        engine = ProcessEngines.getDefaultProcessEngine();
    }

    @Test
    public void deploy(){
        //从流程引擎中获取仓库服务，主要用于部署流程
        RepositoryService repositoryService = engine.getRepositoryService();
        //部署流程需要从仓库服务中创建部署流程的构建者
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.name("测试请假申请");//构建者为此构建取名
        builder.addClasspathResource("leave.bpmn");//构建者需要部署资源
        builder.addClasspathResource("leave.png");
        Deployment deployment = builder.deploy();
        String id = deployment.getId();
        String name = deployment.getName();
        System.out.printf("id:%s,name:%s\n",id, name);

    }
    @Test
    public void startProcess(){
        //运行时服务
        RuntimeService runtimeService = engine.getRuntimeService();
        //通过key启动流程
        ProcessInstance instance = runtimeService.startProcessInstanceById("testLeave");
        String id = instance.getId();
        String name = instance.getName();
        System.out.println("id:%s,name:%s\n,id,name");
    }
    @Test
    public  void listTask(){
        //任务服务
        TaskService taskService = engine.getTaskService();
        //使用任务服务创建任务查询
        TaskQuery query = taskService.createTaskQuery();
        query.taskCandidateOrAssigned("张三");//相当于where条件
        List<Task> tasks = query.list();
        for (Task t:tasks){
            System.out.println(t.getId() + "\t" + t.getName());
            //完成任务，跳转至下一个任务
            taskService.complete(t.getId());
        }
    }
}
