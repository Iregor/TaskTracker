import java.util.HashMap;

public class EpicTask extends Task {
    //    класс для эпиков и задач (эпиком будет считаться задача, у которой не пустой HashMap subTasks

    private HashMap<String, SubTask> subTasks;

    public EpicTask (String id, String title, String description) {
        super(id, title, description);
        subTasks = new HashMap<>();
    }

    void createSubTask(String title, String description, String idOfEpicTask){       //создается subTask с полным id
        String id = this.getID() + getSubTaskID();
        subTasks.put(id, new SubTask(id, title, description, idOfEpicTask));
    }

    String getSubTaskID(){                                      //возвращается добавочная часть id
        return ("." + Integer.toString(subTasks.size()));
    }

    public HashMap<String, SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public void setStatus(String status){
        int countDone = 0;
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getStatus().equals("IN_PROGRESS")) {
                super.setStatus("IN_PROGRESS");
                return;
            } else if (subTask.getStatus().equals("DONE"))
                countDone++;
        }
        if (countDone == subTasks.size())
            super.setStatus("DONE");
    }

    @Override
    public String toString(){
        String outStr = super.toString() + "\n";
        for (SubTask subTask: subTasks.values()) {
            outStr += "\t" + subTask.toString() + "\n";
        }
        return outStr;
    }

    class SubTask extends Task {
        //        класс для подзадач
        String idOfEpicTask;
        SubTask(String id, String title, String description, String idOfEpicTask) {
            super(id, title, description);
            this.idOfEpicTask = idOfEpicTask;
        }
        
        @Override
        public void setStatus(String status){   //при обновлении статуса SubTask будет автоматом обновляться EpicTask
            super.setStatus(status);
            EpicTask.this.setStatus("noNeed");
        }
    }
}