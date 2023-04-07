package sample;



public class Item {

    private String itemname;

    private String state;

    private String taskname;

    public Item(String itemname, String state, String taskname) {
        this.itemname = itemname;
        this.state = state;
        this.taskname = taskname;
    }


    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public Item(String itemname, String state) {
        this.itemname = itemname;
        this.state = state;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
