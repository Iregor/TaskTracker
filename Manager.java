import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    String countID = "0";
    HashMap<String, Task> tasks = new HashMap<>();

    public String getTaskID(){
        int countID = Integer.parseInt(this.countID);
        countID++;
        this.countID = Integer.toString(countID);
        return this.countID;
    }

    void createTask(Task task){   //создает Task, EpicTask или SubTask в зависимости от природы пришедшего task
            if (task instanceof EpicTask){
                String id = "E" + getTaskID();
                tasks.put(id, new EpicTask(id, task.title, task.description));
            } else if (task instanceof EpicTask.SubTask){
                EpicTask.SubTask newSubTask = (EpicTask.SubTask)task;
                EpicTask epicTask = (EpicTask)tasks.get(newSubTask.idOfEpicTask);
                //idOfEpicTask известен, так как пользователь всегда выбирает, к какой задаче создать подзадачу
                epicTask.createSubTask(newSubTask.title, newSubTask.description, newSubTask.idOfEpicTask);
            } else {
                String id = getTaskID();
                tasks.put(id, new Task(id, task.title, task.description));
            }
    }

    void updateTask(Task task){      //обновляет задачу (task, epicTask или subTask)
        Task taskToRenew = getTaskWithID(task.getID());
        taskToRenew.title = task.title;
        taskToRenew.description = task.description;
        taskToRenew.setStatus(task.getStatus());
    }

    ArrayList<Task> getAllTasks (){
//        есть указание, что нужно возвращать объекты, а не печатать в консоль. В подсказке к данному методу указано,
//        что нужно получить список задач. Попробую соединить эти два требования в виде возврата ArrayList'а объектов :)
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task: this.tasks.values()) {
            EpicTask epicTask = null;
            if (task instanceof EpicTask){
                epicTask = (EpicTask) task;
                tasks.add(epicTask);
                tasks.addAll(epicTask.getSubTasks().values());
            } else {
                tasks.add(task);
            }
        }
        return tasks;
    }

    void removeAllTasks(){
        tasks.clear();
    }

    void removeTaskByID(String id){
        if (id.indexOf('.') != -1){
            String[] partsOfID = id.split("\\.");
            String idOfEpicTaskPart = partsOfID[0];
            String idOfSubTaskPart = partsOfID[1];
            EpicTask epicTask = (EpicTask) tasks.get(idOfEpicTaskPart);
            epicTask.getSubTasks().remove(idOfEpicTaskPart + "." + idOfSubTaskPart);
        } else {
            tasks.remove(id);
        }
    }

    ArrayList<Task> getSubTasksOfEpic(String idOfEpicTask){         //возврат всех подзадач EpicTask'а
        EpicTask epicTask = (EpicTask) tasks.get(idOfEpicTask);
        return new ArrayList<>(epicTask.getSubTasks().values());
    }

    Task getTaskWithID(String id){
        if (id.indexOf('.') != -1){
            String[] partsOfID = id.split("\\.");
            String idOfEpicTaskPart = partsOfID[0];
            String idOfSubTaskPart = partsOfID[1];
            EpicTask epicTask = (EpicTask) tasks.get(idOfEpicTaskPart);
            return epicTask.getSubTasks().get(idOfEpicTaskPart + "." + idOfSubTaskPart);
        } else {
            return tasks.get(id);
        }
    }
}