public class RunApp {

    public static void main(String[] args) {
        Manager manager = new Manager();
        System.out.println("Приступаем к тестированию программы.");

        //менеджер наполняется задачами
        System.out.println("Генерируются задачи Task и EpicTask на стороне пользователя...");
        manager.createTask (new Task("0", "Цветы", "Полить цветы"));                    //д.б. id1
        manager.createTask (new Task("0", "Собака", "Покормить собаку"));               //д.б. id2
        manager.createTask (new Task("0", "Салат", "Купить ингридиенты для салата"));   //д.б. id3
        manager.createTask (new EpicTask("0", "Друзья", "Встретиться с друзьями"));     //д.б. idЕ4
        manager.createTask (new EpicTask("0", "Поход", "Сходить в поход"));             //д.б. idЕ5
        manager.createTask (new EpicTask("0", "Фильм", "Сходить в кино"));              //д.б. idЕ6

        //генерируем subTask'и как будто это на стороне пользователя, потом отправим их в наш бэкэнд
        System.out.println("Генерируются задачи SubTask на стороне пользователя...");
        EpicTask epicTask = new EpicTask("E0", "To make SubTasks", "and no more reason");
            epicTask.createSubTask("Созвониться", "Договориться о времени", "E4"); //д.б. id E0.0
            epicTask.createSubTask("Место", "Выбрать место", "E4"); //д.б. id E0.1
            epicTask.createSubTask("Настроение", "Взять с собой хорошее настроение", "E4"); //д.б. id E0.2
            epicTask.createSubTask("Рюкзак", "Взять теплые вещи и горячий чай", "E5"); //д.б. id E0.3
            epicTask.createSubTask("Палатка", "Купить палатку", "E5"); //д.б. id E0.4
            epicTask.createSubTask("Фильм", "Выбрать фильм", "E6"); //д.б. id E0.5
            epicTask.createSubTask("Билеты", "Купить билеты", "E6"); //д.б. id E0.6

        //закидываем сгенерированные подзадачи в менеджер
        manager.createTask(epicTask.getSubTasks().get("E0.0"));
        manager.createTask(epicTask.getSubTasks().get("E0.1"));
        manager.createTask(epicTask.getSubTasks().get("E0.2"));
        manager.createTask(epicTask.getSubTasks().get("E0.3"));
        manager.createTask(epicTask.getSubTasks().get("E0.4"));
        manager.createTask(epicTask.getSubTasks().get("E0.5"));
        manager.createTask(epicTask.getSubTasks().get("E0.6"));
        System.out.println("Сгенерированные задачи загружены в менеджер.");

        //Проверка функции создания задач и функции вывода всех задач
        System.out.println("\nПроверка функции создания и вывода задач:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        //в выводе задачи дублируются, потому как toString EpicTask настроен на вывод также своих SubTask

        //проверка получения задачи по ID
        System.out.println("\nПроверка получения задачи по id:");
        String id;
        id = "E5";
        System.out.println("\nПолучена задача по ID: " + id + "\n" + manager.getTaskWithID(id));
        id = "1";
        System.out.println("\nПолучена задача по ID: " + id + "\n" + manager.getTaskWithID(id));
        id = "E6.1";
        System.out.println("\nПолучена задача по ID: " + id + "\n" + manager.getTaskWithID(id));

        //проверка функции обновления задачи
        System.out.println("\nПроверка функции обновления задач:");
        id = "E6";
        manager.updateTask(new EpicTask(id, "Фильм", "Скачать фильм"));
        System.out.println("\nПолучена ОБНОВЛЕННАЯ задача по ID: " + id + "\n" + manager.getTaskWithID(id));

        System.out.println("\nПроверка фукнции обновления статуса epicTask:");
        //Генерируем подзадачи для проверка update subTask и работы перерасчета статусов EpicTask
        System.out.println("Генерируются задачи subTask на стороне пользователя.");
        EpicTask epicTaskAboutFilms = new EpicTask("E6", "To make SubTasks", "and no more reason");
            epicTaskAboutFilms.createSubTask("Билеты", "Купить билеты", "E6"); //д.б. id E6.0
            epicTaskAboutFilms.createSubTask("Билеты", "Купить билеты", "E6"); //д.б. id E6.1
        System.out.println("Меняются статусы subTask.");
        //меняем статусы подзадач
        epicTaskAboutFilms.getSubTasks().get("E6.0").setStatus("DONE");
        epicTaskAboutFilms.getSubTasks().get("E6.1").setStatus("IN_PROGRESS");
        //обновляем подзадачи в менеджере
        manager.updateTask(epicTaskAboutFilms.getSubTasks().get("E6.0"));
        manager.updateTask(epicTaskAboutFilms.getSubTasks().get("E6.1"));
        id = "E6";
        //вывод EpicTask с обновленным статусом и подзадачами
        System.out.println("\nПолучена ОБНОВЛЕННАЯ задача по ID: " + id + "\n" + manager.getTaskWithID(id));
        System.out.println("Меняются статусы subTask.");
        epicTaskAboutFilms.getSubTasks().get("E6.1").setStatus("DONE");
        manager.updateTask(epicTaskAboutFilms.getSubTasks().get("E6.1"));
        id = "E6";
        //вывод EpicTask с обновленным статусом и подзадачами
        System.out.println("\nПолучена ОБНОВЛЕННАЯ задача по ID: " + id + "\n" + manager.getTaskWithID(id));

        //проверка удаления задачи по ID
        System.out.println("Проверка удаления задачи по ID:");
        id = "E6.1";
        manager.removeTaskByID(id);
        System.out.println("Удаление задачи по id: " + id);
        id = "E6";
        System.out.println("\nПолучена задача по ID: " + id + "\n" + manager.getTaskWithID(id));

        //Получение списка подзадач эпика
        System.out.println("Проверка функции вывода подзадачи EpicTask: ");
        System.out.println("Подзадачи эпика E5:");
        for (Task task: manager.getSubTasksOfEpic("E5")) {
            System.out.println(task);
        }

        //Проверка удаления всех задач
        System.out.println("\nПроверка удаления всех задач:");
        manager.removeAllTasks();
        if (manager.tasks.isEmpty())
            System.out.println("\n" + "Список задач пуст.");

        System.out.println("\nКажется все работает!");
    }
}
