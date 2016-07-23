package rayfantasy.icode.Ui.Activity;

import android.os.*;

import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;

import rayfantasy.icode.R;

public class userSetActivity extends AppCompatActivity
{
	private Toolbar toolbar;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userset);
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
	}
}
