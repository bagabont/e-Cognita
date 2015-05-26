package rwth.elearning.ecognita.client.ecognitaclient.model;

/**
 * Created by ekaterina on 25.05.2015.
 */
public class NavDrawerItem implements IListItem {
    private String title;
    private int icon;

    public NavDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }
}
