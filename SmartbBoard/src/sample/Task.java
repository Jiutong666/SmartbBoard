package sample;

/**
 * @author zsq
 * @date 2021/10/5 18:07
 */
public class Task {

    private String columns;

    private String taskname;

    private String duedate;

    private String progressbar;

    private String description;

    private String state;

    public Task(String columns, String taskname, String duedate, String progressbar, String description, String state) {
        this.columns = columns;
        this.taskname = taskname;
        this.duedate = duedate;
        this.progressbar = progressbar;
        this.description = description;
        this.state = state;
    }

    public Task(String taskname, String duedate, String progressbar, String description) {
        this.taskname = taskname;
        this.duedate = duedate;
        this.progressbar = progressbar;
        this.description = description;
    }

    public Task(String taskname, String duedate, String progressbar, String description, String state) {
        this.taskname = taskname;
        this.duedate = duedate;
        this.progressbar = progressbar;
        this.description = description;
        this.state = state;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getProgressbar() {
        return progressbar;
    }

    public void setProgressbar(String progressbar) {
        this.progressbar = progressbar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
