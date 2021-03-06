package yumeet.freebird.yumeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventListActivity extends AppCompatActivity {

    static final String TAG = "EventListActivity";
    static final int ADD_EVENT_REQUEST = 1;

    private ListView eventList;
    private ArrayAdapter<Event> adapter;

    private EventManager eventManager = EventManager.getManager();
    static final String EVENT_ID_KEY = "eventId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        eventList = (ListView) findViewById(R.id.eventListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                eventManager.getEvents());
        eventManager.setAdapter(adapter);

        eventList.setAdapter(adapter);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEvent(position);
            }
        });
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventListActivity.this, AddEventActivity.class);
                startActivityForResult(intent, ADD_EVENT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == ADD_EVENT_REQUEST){
            if (resultCode == RESULT_OK){
                String title = data.getStringExtra(AddEventActivity.TITLE_KEY);
                String location = data.getStringExtra(AddEventActivity.LOCATION_KEY);
                String description = data.getStringExtra(AddEventActivity.DESC_KEY);
                int peopleNeeded = data.getIntExtra(AddEventActivity.NEEDED_KEY, 0);
                int peopleHave = data.getIntExtra(AddEventActivity.HAVE_KEY, 0);
                String startTime = data.getStringExtra(AddEventActivity.START_KEY);
//                Log.d(TAG, title + " " + location + " " + description);
                Event event = new Event(title, description, location, peopleNeeded, peopleHave,
                        startTime);
                eventManager.addEvent(event);
            }
        }
    }

    private void showEvent(int i){
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra(EVENT_ID_KEY, i);
        startActivity(intent);
    }

//        FragmentManager fm = getSupportFragmentManager();
//        Fragment eventFragmentList = fm.findFragmentById(R.id.EventListFragment);
//
//        if (eventFragmentList == null) {
//            eventFragmentList = new EventFragmentList();
//            fm.beginTransaction()
//                    .add(R.id.EventListFragment, eventFragmentList)
//                    .commit();
//        }
//    }


//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v  = inflater.inflate(R.layout.fragment_event_list, container, false);
//        eventList = (ListView)v.findViewById(R.id.eventListView);
//        eventList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
//                eventManager.getEvents()));
//        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showEvent(position);
//            }
//        });
//        Button button = (Button) v.findViewById(R.id.fab);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EventListActivity.this, AddEventActivity.class);
//                startActivity(intent);
//            }
//        });
//        return v;
//    }




}
