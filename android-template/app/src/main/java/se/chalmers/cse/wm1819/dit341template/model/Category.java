package se.chalmers.cse.wm1819.dit341template.model;

//My camel Class. Used by GSON to transfer JSON directly to Java Object
public class Category {
    public String name;
    public String _id;
    Category() {
    }
    Category(String _id, String name) {
        this._id = _id;
        this.name = name;
    }
}
