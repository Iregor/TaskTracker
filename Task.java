import java.util.HashMap;
import java.util.Objects;

public class Task {
    String title;
    String description;
    private final String id;
    protected String status; //NEW, IN_PROGRESS, DONE

    public Task(String id, String title, String description) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = "NEW";
    }

    public String getID() {
        return id;
    }

    public void setStatus(String status) {
        if (status.equals("DONE") || status.equals("IN_PROGRESS") || status.equals("NEW"))
            this.status = status;
        else
            System.out.println("Ошибка при установке статуса");
    }

    public String getStatus() {
        return status;
    }
//    Address{city='Санкт-Петербург', street='Кирочная', houseNumber=19}
    @Override
    public String toString(){
        String outStr = Objects.toString(getClass());
        outStr += " {id = '" + id + "', title = '" + title + "', description = '" + description
                + "', status = '" + status + "'}";
        return outStr;
    }
}

