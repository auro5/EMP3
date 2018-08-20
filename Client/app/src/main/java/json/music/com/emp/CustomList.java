package json.music.com.emp;

/**
 * Created by Aurobind on 06-03-2017.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class CustomList extends ArrayAdapter<String> {
    private String[] ids;
    private String[] names;
    private String[] path;
    private String[] mood;
    private Activity context;

    public CustomList(Activity context, String[] ids, String[] names, String[] path, String[] mood) {
        super(context, R.layout.list_view_layout, ids);
        this.context = context;
        this.ids = ids;
        this.names = names;
        this.path = path;
        this.mood = mood;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);
        TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPath = (TextView) listViewItem.findViewById(R.id.textViewPath);
        TextView textViewMood = (TextView) listViewItem.findViewById(R.id.textViewMood);

        textViewId.setText(ids[position]);
        textViewName.setText(names[position]);
        textViewPath.setText(path[position]);
        textViewMood.setText(mood[position]);

        return listViewItem;
    }
}