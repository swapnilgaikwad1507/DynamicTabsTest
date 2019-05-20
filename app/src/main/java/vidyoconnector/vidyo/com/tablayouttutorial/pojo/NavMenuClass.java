package vidyoconnector.vidyo.com.tablayouttutorial.pojo;

import android.view.Menu;

import java.util.ArrayList;

public class NavMenuClass{
    Menu menu;
    ArrayList items;

    public NavMenuClass(Menu menu,ArrayList items){

        this.items = items;
        this.menu = menu;

    }

    public Menu getMenu(){
        return menu;
    }

    public ArrayList getItems(){
        return items;
    }

}
