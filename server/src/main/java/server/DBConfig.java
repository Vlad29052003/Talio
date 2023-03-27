package server;

import commons.Board;
import commons.Task;
import commons.TaskList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.database.BoardRepository;
import server.database.TaskListRepository;
import server.database.TaskRepository;
import java.util.List;

@Configuration
public class DBConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            BoardRepository br, TaskListRepository lr, TaskRepository tr) {
        return args -> {
            Board b1 = new Board("board1", null);
            Board b2 = new Board("board2", null);
            TaskList l1 = new TaskList("list1", 1);
            TaskList l2 = new TaskList("list2", 2);
            TaskList l3 = new TaskList("list3", 3);
            Task t1 = new Task("task1", 0, null);
            Task t2 = new Task("task2", 0, null);
            Task t3 = new Task("task3", 1, null);
            Task t4 = new Task("task4", 1, null);
            Task t5 = new Task("task5", 2, null);
            l1.addTask(t1);
            l1.addTask(t3);
            l1.addTask(t5);
            l3.addTask(t2);
            l3.addTask(t4);
            b1.addTaskList(l1);
            b1.addTaskList(l3);
            b2.addTaskList(l2);
            br.saveAll(List.of(b1, b2));
            lr.saveAll(List.of(l1, l2, l3));
            tr.saveAll(List.of(t1, t2, t3, t4, t5));
        };
    }
}
