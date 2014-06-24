package ir.dariushzandi.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends ActionBarActivity {

	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mCheatButton;
	private ImageButton mNextButton;
	private ImageButton mPrevButton;
	private TextView mQuestionTextView;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[]{
		new TrueFalse(R.string.question_sanandaj, true),
		new TrueFalse(R.string.question_tehran, false),
		new TrueFalse(R.string.question_iran, true)
	};
	
	private int mCurrentIndex = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
                
        Log.d(TAG, "onCreat(Bundle) called");

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
        
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
        
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex+1)% mQuestionBank.length;
		        updateQuestion();
			}
		});
        
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex-1)<0 ? mCurrentIndex+mQuestionBank.length-1 : mCurrentIndex-1;
				updateQuestion();
			}
		});
        
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
//				startActivity(intent);
				startActivityForResult(intent, 0);
			}
		});

        if(savedInstanceState != null)
        	mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        
        updateQuestion();

    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
    	super.onSaveInstanceState(savedInstanceState);
    	Log.i(TAG, "onSaveInstanceState");
    	savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause(){
    	super.onPause();
    	Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume(){
    	super.onResume();
    	Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop(){
    	super.onStop();
    	Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	Log.d(TAG, "onDestroy() called");
    }
    
	protected void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int messageResId = 0;
		
		if(userPressedTrue == answerIsTrue){
			messageResId = R.string.correct_toast;
		}else{
			messageResId = R.string.incorrect_toast;
		}
		
		Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show();
		
	}


	protected void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
            return rootView;
        }
    }

}
