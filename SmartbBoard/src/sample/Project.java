package sample;


public class Project {

   private String name;

   private String defaults;

    public Project(String name, String defaults) {
        this.name = name;
        this.defaults = defaults;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaults() {
        return defaults;
    }

    public void setDefaults(String defaults) {
        this.defaults = defaults;
    }
}
