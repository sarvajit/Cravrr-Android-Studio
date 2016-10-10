package com.retaurants.chinaspice;

import helper.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends FragmentActivity implements
		OnClickListener {

	// UI elements
	EditText txtName, txtPhone, txtEmail, txtAsk;
	TextView txtHotelname, txtPeople, txtDate, btnToday, btnTomorrow,
			btnDafterTomorrow;

	AsyncTask<Void, Void, Void> mRegisterTask;

	Context context;
	Controller aController;
	FragmentManager manager;
	String bookTableName, bookTablePhone, bookTableEmail;
	public static String regId;
	View lastbtnTimeView, lastbtnOccastion;

	SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
	// DateFormat output2 = SimpleDateFormat.getDateInstance();

	SimpleDateFormat output2 = new SimpleDateFormat("MMM dd, yyyy");
	public static String name, people = "", email, phone, note = "", time = "",
			date, occasion = "", date2 = "";
	SeekBar seekBar;
	ImageButton btnSearch, btnAsk, btnDrawer;

	TextView pm12, pm123, pm1, pm13, pm2, pm23, pm3, pm33, pm4, pm43, pm5,
			pm53, pm6, pm63, pm7, pm73, pm8, pm83, pm9, pm93, pm10, pm103,
			pm11, pm113;
	DrawerBaseAdapter adapter;
	ImageButton btnBirthday, btnBaby, btnValentine, btnEngadge, btnBack;
	LinearLayout layoutMain;
	Handler bookHandler;
	ImageButton btnMenu, btnReservation, btnReview, btnAddFavourite;
	ProgressDialog dialog;
	boolean today = true;
	Typeface tf, tf1;
	public static DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	ArrayList<String> mPlanetTitles;
	private TextView textHotelName;
	private ImageButton btnDate;
	private ImageButton btnMeeting;
	private ImageButton btnOther;
	public static Activity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_register);
			context = this;
			activity = this;

			int apiVersion = android.os.Build.VERSION.SDK_INT;
			if (apiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
				getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
			else
				getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

			NaanKing_Urls.prefs = getSharedPreferences("B2C", MODE_PRIVATE);
			bookTableName = NaanKing_Urls.prefs.getString("bookTableName", "");
			bookTablePhone = NaanKing_Urls.prefs
					.getString("bookTablePhone", "");
			bookTableEmail = NaanKing_Urls.prefs.getString(
					Constants.Billing_Email, "");

			manager = getSupportFragmentManager();

			txtHotelname = (TextView) findViewById(R.id.txtRegisterHotelName);
			txtHotelname.setText(NaanKing_Urls.Hotel_Address);

			tf = Typeface.createFromAsset(getAssets(),
					"fonts/Gotham-Rounded-Light_21020.ttf");
			tf1 = Typeface.createFromAsset(getAssets(),
					"fonts/gotham-rounded-book.otf");
			txtHotelname.setTypeface(tf);

			btnSearch = (ImageButton) findViewById(R.id.btnSearch);
			btnSearch.setOnClickListener(this);
			btnBack = (ImageButton) findViewById(R.id.btnRegBack);
			btnBack.setOnClickListener(this);

			btnMenu = (ImageButton) findViewById(R.id.btnMenu);
			btnReservation = (ImageButton) findViewById(R.id.btnReservation);
			btnReview = (ImageButton) findViewById(R.id.btnReview);
			btnAddFavourite = (ImageButton) findViewById(R.id.imgRegPopular);
			btnAddFavourite.setOnClickListener(this);

			textHotelName = (TextView) findViewById(R.id.textHotelName);
			textHotelName.setText(NaanKing_Urls.HOTEL_NAME);
			if (NaanKing_Urls.IS_FAV_HOTEL == 0) {
				btnAddFavourite.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.heart_grey_img));
			} else {
				btnAddFavourite.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.heart_img));
			}

			btnMenu.setOnClickListener(this);
			btnReservation.setOnClickListener(this);
			btnReview.setOnClickListener(this);

			seekBar = (SeekBar) findViewById(R.id.seekPeople);
			seekBar.setMax(50);
			seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					txtPeople.setText("" + progress);

				}
			});

			layoutMain = (LinearLayout) findViewById(R.id.mainLayout);

			pm12 = (TextView) findViewById(R.id.btn12pm);
			pm12.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm123 = (TextView) findViewById(R.id.btn123pm);
			pm123.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm1 = (TextView) findViewById(R.id.btn1pm);
			pm1.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm13 = (TextView) findViewById(R.id.btn13pm);
			pm13.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm2 = (TextView) findViewById(R.id.btn2pm);
			pm2.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm23 = (TextView) findViewById(R.id.btn23pm);
			pm23.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm3 = (TextView) findViewById(R.id.btn3pm);
			pm3.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm33 = (TextView) findViewById(R.id.btn33pm);
			pm33.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm4 = (TextView) findViewById(R.id.btn4pm);
			pm4.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm43 = (TextView) findViewById(R.id.btn43pm);
			pm43.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm5 = (TextView) findViewById(R.id.btn5pm);
			pm5.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm53 = (TextView) findViewById(R.id.btn53pm);
			pm53.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm63 = (TextView) findViewById(R.id.btn63pm);
			pm63.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm7 = (TextView) findViewById(R.id.btn7pm);
			pm7.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm73 = (TextView) findViewById(R.id.btn73pm);
			pm73.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm8 = (TextView) findViewById(R.id.btn8pm);
			pm8.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm83 = (TextView) findViewById(R.id.btn83pm);
			pm83.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm9 = (TextView) findViewById(R.id.btn9pm);
			pm9.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm93 = (TextView) findViewById(R.id.btn93pm);
			pm93.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm10 = (TextView) findViewById(R.id.btn10pm);
			pm10.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm103 = (TextView) findViewById(R.id.btn103pm);
			pm103.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm11 = (TextView) findViewById(R.id.btn11pm);
			pm11.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm113 = (TextView) findViewById(R.id.btn113pm);
			pm113.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			pm6 = (TextView) findViewById(R.id.btn6pm);
			pm6.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));

			pm12.setOnClickListener(new BtnTime());
			pm123.setOnClickListener(new BtnTime());
			pm1.setOnClickListener(new BtnTime());
			pm13.setOnClickListener(new BtnTime());
			pm2.setOnClickListener(new BtnTime());
			pm23.setOnClickListener(new BtnTime());
			pm3.setOnClickListener(new BtnTime());
			pm33.setOnClickListener(new BtnTime());
			pm4.setOnClickListener(new BtnTime());
			pm43.setOnClickListener(new BtnTime());
			pm5.setOnClickListener(new BtnTime());
			pm53.setOnClickListener(new BtnTime());
			pm63.setOnClickListener(new BtnTime());
			pm7.setOnClickListener(new BtnTime());
			pm73.setOnClickListener(new BtnTime());
			pm8.setOnClickListener(new BtnTime());
			pm83.setOnClickListener(new BtnTime());
			pm9.setOnClickListener(new BtnTime());
			pm93.setOnClickListener(new BtnTime());
			pm10.setOnClickListener(new BtnTime());
			pm103.setOnClickListener(new BtnTime());
			pm11.setOnClickListener(new BtnTime());
			pm113.setOnClickListener(new BtnTime());
			pm6.setOnClickListener(new BtnTime());

			pm12.setTypeface(tf1);
			pm123.setTypeface(tf1);
			pm1.setTypeface(tf1);
			pm13.setTypeface(tf1);
			pm2.setTypeface(tf1);
			pm23.setTypeface(tf1);
			pm3.setTypeface(tf1);
			pm33.setTypeface(tf1);
			pm4.setTypeface(tf1);
			pm43.setTypeface(tf1);
			pm5.setTypeface(tf1);
			pm53.setTypeface(tf1);
			pm63.setTypeface(tf1);
			pm7.setTypeface(tf1);
			pm73.setTypeface(tf1);
			pm8.setTypeface(tf1);
			pm83.setTypeface(tf1);
			pm9.setTypeface(tf1);
			pm93.setTypeface(tf1);
			pm10.setTypeface(tf1);
			pm103.setTypeface(tf1);
			pm11.setTypeface(tf1);
			pm113.setTypeface(tf1);
			pm6.setTypeface(tf1);

			btnBirthday = (ImageButton) findViewById(R.id.btnBirthday);
			btnBaby = (ImageButton) findViewById(R.id.btnBaby);
			btnEngadge = (ImageButton) findViewById(R.id.btnEngadge);
			btnValentine = (ImageButton) findViewById(R.id.btnValenetine);
			// new code
			btnDate = (ImageButton) findViewById(R.id.btnDate);
			btnMeeting = (ImageButton) findViewById(R.id.btnMeeting);
			btnOther = (ImageButton) findViewById(R.id.btnOther);

			/*btnDate.setOnClickListener(new BtnOccation());
			btnDate.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.date));
			btnDate.setId(5);


			btnMeeting.setOnClickListener(new BtnOccation());
			btnMeeting.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.meeting));
			btnMeeting.setId(6);

			btnOther.setOnClickListener(new BtnOccation());
			btnOther.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.other));
			btnOther.setId(7);

			btnBirthday.setOnClickListener(new BtnOccation());
			btnBirthday.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.brithday));
			btnBirthday.setId(1);
			btnEngadge.setOnClickListener(new BtnOccation());
			btnEngadge.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.engagement));
			btnEngadge.setId(4);
			btnValentine.setOnClickListener(new BtnOccation());
			btnValentine.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.ff_party));
			btnValentine.setId(3);
			btnBaby.setOnClickListener(new BtnOccation());
			btnBaby.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.baby));
			btnBaby.setId(2);*/

			aController = (Controller) getApplicationContext();

			// Check if Internet Connection present
			if (!aController.isConnectingToInternet()) {

				// Internet Connection is not present
				aController.showAlertDialog(RegisterActivity.this,
						"Internet Connection Error",
						"Please connect to working Internet connection", false);

				// stop executing code by return
				return;
			}

			// Check if GCM configuration is set
			if (Config.YOUR_SERVER_URL == null
					|| Config.GOOGLE_SENDER_ID == null
					|| Config.YOUR_SERVER_URL.length() == 0
					|| Config.GOOGLE_SENDER_ID.length() == 0) {

				// GCM sernder id / server url is missing
				aController.showAlertDialog(RegisterActivity.this,
						"Configuration Error!",
						"Please set your Server URL and GCM Sender ID", false);

				// stop executing code by return
				return;
			}

			// Make sure the device has the proper dependencies.
			///GCMRegistrar.checkDevice(this);

			// Make sure the manifest permissions was properly set
			///GCMRegistrar.checkManifest(this);
			// GCMRegistrar.unregister(this);

			// Register custom Broadcast receiver to show messages on activity
			registerReceiver(mHandleMessageReceiver, new IntentFilter(
					Config.DISPLAY_MESSAGE_ACTION));

			///GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);
			// Get GCM registration id
			///regId = GCMRegistrar.getRegistrationId(this);
			// GCMRegistrar.unregister(this);
			// Check if regid already presents
			///GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);
			/*if (regId.equals("")) {

				// Register with GCM
				GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);

			} else {

				// Device is already registered on GCM Server
				if (GCMRegistrar.isRegisteredOnServer(this)) {

				} else {

					// Try to register again, but not in the UI thread.
					// It's also necessary to cancel the thread onDestroy(),
					// hence the use of AsyncTask instead of a raw thread.

					mRegisterTask = new AsyncTask<Void, Void, Void>() {

						@Override
						protected Void doInBackground(Void... params) {

							// Register on our server
							// On server creates a new user
							// aController.register(context, name, people,
							// regId);

							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							mRegisterTask = null;
						}

					};

					// execute AsyncTask
					mRegisterTask.execute(null, null, null);
				}
			}*/

			txtName = (EditText) findViewById(R.id.txtName);
			txtName.setText(bookTableName);
			txtPeople = (TextView) findViewById(R.id.txtPeople);
			txtPhone = (EditText) findViewById(R.id.txtPhone);
			txtPhone.setText(bookTablePhone);
			txtEmail = (EditText) findViewById(R.id.txtEmail);
			txtEmail.setText(bookTableEmail);
			txtDate = (TextView) findViewById(R.id.txtResDate);
			txtDate.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			txtDate.setTypeface(tf1);
			btnToday = (TextView) findViewById(R.id.btnToday);
			btnToday.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time_hover));
			btnToday.setTextColor(Color.WHITE);
			txtDate.setTypeface(tf1);
			date = output.format(new Date());
			date2 = output2.format(new Date());

			btnTomorrow = (TextView) findViewById(R.id.btnTomorrow);
			btnTomorrow.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			txtDate.setTypeface(tf1);
			btnDafterTomorrow = (TextView) findViewById(R.id.btnDafterTomorrow);
			btnDafterTomorrow.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.day_time));
			txtDate.setTypeface(tf1);
			txtPeople.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

					// if(!txtPeople.getText().toString().equals("")){
					// if(Integer.parseInt(txtPeople.getText().toString())>50){
					// txtPeople.setText("50");
					//
					// }
					//
					// seekBar.setProgress(Integer.parseInt(txtPeople.getText().toString()));
					// }

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {

				}
			});

			btnToday.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Date dt = new Date();
					date = output.format(dt);
					date2 = output2.format(dt);
					btnToday.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.day_time_hover));
					txtDate.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.day_time));
					btnTomorrow.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.day_time));
					btnDafterTomorrow.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.day_time));
					btnToday.setTextColor(Color.WHITE);
					txtDate.setTextColor(getResources().getColor(
							R.color.hotel_blue));
					btnTomorrow.setTextColor(getResources().getColor(
							R.color.hotel_blue));
					btnDafterTomorrow.setTextColor(getResources().getColor(
							R.color.hotel_blue));

					hideEditText();
					today = true;
					checkDate();

				}
			});

			btnTomorrow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Date dt = new Date();

					dt.setDate(dt.getDate() + 1);
					date = output.format(dt);
					date2 = output2.format(dt);

					btnToday.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.day_time));
					txtDate.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.day_time));
					btnTomorrow.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.day_time_hover));
					btnDafterTomorrow.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.day_time));
					btnTomorrow.setTextColor(Color.WHITE);
					txtDate.setTextColor(getResources().getColor(
							R.color.hotel_blue));
					btnToday.setTextColor(getResources().getColor(
							R.color.hotel_blue));
					btnDafterTomorrow.setTextColor(getResources().getColor(
							R.color.hotel_blue));

					hideEditText();
					today = false;
					checkDate();

				}
			});

			btnDafterTomorrow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Date dt = new Date();

					dt.setDate(dt.getDate() + 2);
					date = output.format(dt);
					date2 = output2.format(dt);

					btnToday.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.day_time));
					txtDate.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.day_time));
					btnTomorrow.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.day_time));
					btnDafterTomorrow.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.day_time_hover));
					btnDafterTomorrow.setTextColor(Color.WHITE);
					txtDate.setTextColor(getResources().getColor(
							R.color.hotel_blue));
					btnTomorrow.setTextColor(getResources().getColor(
							R.color.hotel_blue));
					btnToday.setTextColor(getResources().getColor(
							R.color.hotel_blue));

					hideEditText();

					today = false;
					checkDate();
				}
			});

			btnAsk = (ImageButton) findViewById(R.id.btnAsk);

			btnAsk.setOnClickListener(this);

			txtDate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					selectDate(v);

				}
			});

			btnDrawer = (ImageButton) findViewById(R.id.btnDrawer);

			// Click event on Register button
			btnDrawer.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					
					adapter=new  DrawerBaseAdapter();
					mDrawerList.setAdapter(adapter);
					adapter.notifyDataSetChanged();

					mDrawerLayout.openDrawer(Gravity.LEFT);
				}
			});

			mTitle = mDrawerTitle = getTitle();
			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			mDrawerList = (ListView) findViewById(R.id.right_mnu_drawer);
			mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
					GravityCompat.START);

			mPlanetTitles = new ArrayList<String>();
			mPlanetTitles.add("BOOK A TABLE");
			mPlanetTitles.add("ORDER FOOD");
			mPlanetTitles.add("NOTIFICATION");
			mPlanetTitles.add("BRANCHES");
			mPlanetTitles.add("INVITE FRIENDS");
			mPlanetTitles.add("ORDER HISTORY");
			mPlanetTitles.add("COUPONS");
			mPlanetTitles.add("CONTACT US");
			

			mPlanetTitles.add("SETTINGS");
			mPlanetTitles.add("SIGN OUT");

			//mDrawerList.setAdapter(new DrawerBaseAdapter());
			adapter=new  DrawerBaseAdapter();
			mDrawerList.setAdapter(adapter);
			mDrawerLayout.setFocusableInTouchMode(false);
  
			// mDrawerList.setOnItemClickListener(new
			// DrawerItemClickListener());

			mDrawerLayout.setDrawerListener(mDrawerToggle);

			mDrawerLayout
					.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		} else {
			finish();
			startActivity(new Intent(RegisterActivity.this, Home_Screen.class));

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	private void hideEditText() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txtPeople.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(txtPhone.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(txtEmail.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(txtName.getWindowToken(), 0);
	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String newMessage = intent.getExtras().getString(
					Config.EXTRA_MESSAGE);

			// Waking up mobile if it is sleeping
			aController.acquireWakeLock(getApplicationContext());

			// Display message on the screen

			// Toast.makeText(getApplicationContext(),
			// "Got Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			aController.releaseWakeLock();
		}
	};

	private void bookTable() {

		if (aController.isNetworkAvailable()) {
			bookHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if (dialog != null || dialog.isShowing()) {
						dialog.dismiss();
					}
					final SharedPreferences.Editor editPref = NaanKing_Urls.prefs
							.edit();

					editPref.putString("bookTableDate", date.toString());
					editPref.putString("bookTableDate2", date2.toString());
					editPref.putString("bookTableTime", time.toString());
					editPref.commit();

					txtDate.setText("");
					txtName.setText("");
					txtEmail.setText("");
					txtPeople.setText("");
					txtPhone.setText("");

					Intent intent = new Intent(context, ThankYou.class);
					intent.putExtra("date", date2);
					startActivity(intent);

				}

			};

			dialog = ProgressDialog.show(context, "CHINA SPICE",
					Html.fromHtml("<b>Please wait,</b><br/>Processing..."));
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {

						SoapObject request = new SoapObject(
								NaanKing_Urls.HOTEL_NAMESPACE,
								NaanKing_Urls.METHOD_GET_TABLE_BOOK);
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
								SoapEnvelope.VER11);
						Element h = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "HoldTable");
						h.setAttribute(null, "MustUnderstand", "1");
						Element UserID = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "UserID");
						UserID.addChild(Node.TEXT, NaanKing_Urls.HOTEL_ID);
						h.addChild(Node.ELEMENT, UserID);

						Element Seats = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "Seats");
						Seats.addChild(Node.TEXT, people);
						h.addChild(Node.ELEMENT, Seats);

						Element BookDate = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "BookDate");
						BookDate.addChild(Node.TEXT, date);
						h.addChild(Node.ELEMENT, BookDate);

						Element BookTime = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "BookTime");
						BookTime.addChild(Node.TEXT, time);
						h.addChild(Node.ELEMENT, BookTime);

						Element CustomerName = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "CustomerName");
						CustomerName.addChild(Node.TEXT, name);
						h.addChild(Node.ELEMENT, CustomerName);

						Element Mobile = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "Mobile");
						Mobile.addChild(Node.TEXT, phone);
						h.addChild(Node.ELEMENT, Mobile);

						Element EmailID = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "EmailID");
						EmailID.addChild(Node.TEXT, email);
						h.addChild(Node.ELEMENT, EmailID);

						Element Remarks = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "Remarks");
						Remarks.addChild(Node.TEXT, note);
						h.addChild(Node.ELEMENT, Remarks);

						Element DeviceRegID = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "DeviceRegID");
						DeviceRegID.addChild(Node.TEXT, regId);
						h.addChild(Node.ELEMENT, DeviceRegID);

						Element Occassion = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "Occassion");
						Occassion.addChild(Node.TEXT, occasion);
						h.addChild(Node.ELEMENT, Occassion);

						Element ip = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "IP");
						ip.addChild(Node.TEXT, "10.0.2.2");
						h.addChild(Node.ELEMENT, ip);
						
						
						Element BookingID = new Element().createElement(
								NaanKing_Urls.HOTEL_NAMESPACE, "BookingID");
						BookingID.addChild(Node.TEXT, "");
						h.addChild(Node.ELEMENT, BookingID);
						envelope.headerOut = new Element[] { h };
						envelope.dotNet = true;

						envelope.setOutputSoapObject(request);

						HttpTransportSE androidHttpTransport = new HttpTransportSE(
								NaanKing_Urls.HOTEL_URL, 60000);
						androidHttpTransport.debug = true;

						androidHttpTransport.call(
								NaanKing_Urls.SOAP_ACTION_GET_TABLE_BOOK,
								envelope);
                        String dump=androidHttpTransport.requestDump;
                        System.out.println("dump="+dump);
                       
						Object result = envelope.getResponse();
						 System.out.println("result.toString()="+result.toString().toString());
						Log.d("BookTable", result.toString());
						bookHandler.sendEmptyMessage(0);
					} catch (Exception e) {
						if (dialog != null) {
							dialog.dismiss();
						}
						e.printStackTrace();
					}

				}
			}).start();
		} else {
			Toast.makeText(context, aController.NO_NETWORK, Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onClick(View v) {
		if (v == btnSearch) {
			Intent homeIntent = new Intent(context, Home_Screen.class);
			startActivity(homeIntent);
		} else if (v == btnBack) {
			onBackPressed();
		} else if (v == btnAsk) {

			if (date.equals("")) {
				Toast.makeText(context, "Please Select Date.",
						Toast.LENGTH_LONG).show();
			} else if (time.equals("")) {
				Toast.makeText(context, "Please Select  Time.",
						Toast.LENGTH_LONG).show();
			} else if (txtPeople.getText().toString().equals("")
					|| txtPeople.getText().toString().equals("0")) {
				Toast.makeText(context, "Please Enter People.",
						Toast.LENGTH_LONG).show();
			}

			else {

				final SharedPreferences.Editor editPref = NaanKing_Urls.prefs
						.edit();

				editPref.putString("bookTableDate", date.toString());
				editPref.putString("bookTableDate2", date2.toString());
				editPref.putString("bookTableTime", time.toString());
				editPref.putString("bookTablePeople", txtPeople.getText()
						.toString());
				editPref.putString("bookTableOccasion", occasion);
				editPref.commit();

				Intent intetRegister = new Intent(context,
						Register_Edit_Activity.class);
				startActivity(intetRegister);
			}

			/*
			 * final PopupWindow win = new PopupWindow(layoutMain,
			 * LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
			 * 
			 * View view = getLayoutInflater().inflate(R.layout.ask_que_dialog,
			 * null);
			 * 
			 * txtAsk = (EditText) view.findViewById(R.id.txtAskQuestion);
			 * 
			 * Button btnOK = (Button) view.findViewById(R.id.btnAskOk);
			 * ImageButton btnCANCEL = (ImageButton) view
			 * .findViewById(R.id.btnAskCancel);
			 * 
			 * // set the custom dialog components - text, image and button
			 * 
			 * btnOK.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { // note =
			 * txtAsk.getText().toString(); // txtAsk.setText(""); //
			 * win.dismiss();
			 * 
			 * Intent intetRegister = new Intent(context,
			 * Register_Edit_Activity.class); startActivity(intetRegister);
			 * 
			 * } });
			 * 
			 * btnCANCEL.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) {
			 * 
			 * // dialog.create().getButton(DialogInterface.BUTTON_NEGATIVE).
			 * performClick();
			 * 
			 * win.dismiss();
			 * 
			 * } });
			 * 
			 * win.setContentView(view);
			 * 
			 * win.showAtLocation(layoutMain, Gravity.CENTER, 0, 0);
			 */

		} else if (v == btnMenu) {
			Intent reg = new Intent(context, Category_Items.class);
			startActivity(reg);
		} else if (v == btnReview) {
			Intent intetReview = new Intent(context, Reviews_Screen.class);
			startActivity(intetReview);
		} else if (v == btnAddFavourite) {
			if (NaanKing_Urls.IS_FAV_HOTEL == 0) {
				btnAddFavourite.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.heart_img));
				NaanKing_Urls.IS_FAV_HOTEL = 1;
			} else {
				btnAddFavourite.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.heart_grey_img));
				NaanKing_Urls.IS_FAV_HOTEL = 0;
			}
			if (NaanKing_Urls.USER_ID == null) {
				Intent inteLoginForHistory = new Intent(context,
						LoginForHistory.class);
				inteLoginForHistory.putExtra("SCREEN", "R");
				startActivity(inteLoginForHistory);
			} else {
				NaanKing_Urls.setHotelFavourite(context);
			}
		}
	}

	public void selectDate(View view) {
		DialogFragment newFragment = new SelectDateFragment();
		newFragment.show(manager, "DatePicker");

	}

	@SuppressLint("ValidFragment")
	public class SelectDateFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar calendar = Calendar.getInstance();
			int yy = calendar.get(Calendar.YEAR);
			int mm = calendar.get(Calendar.MONTH);
			int dd = calendar.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, yy, mm, dd);
		}

		public void onDateSet(DatePicker view, int yy, int mm, int dd) {
			populateSetDate(yy, mm + 1, dd);

		}
	}

	@SuppressWarnings("deprecation")
	public void populateSetDate(int year, int month, int day) {

		// txtDate.setText(year + "-" + (month <= 9 ? "0" : "") + month + "-"
		// + (day <= 9 ? "0" : "") + day);

		Date dt = new Date((year - 1900), (month - 1), day);
		Date dt2 = new Date();

		date = (year + "-" + (month <= 9 ? "0" : "") + month + "-"
				+ (day <= 9 ? "0" : "") + day);
		date2 = (month <= 9 ? "0" : "") + month + "-" + (day <= 9 ? "0" : "")
				+ day + "-" + year;

		date2 = output2.format(dt);
		txtDate.setText(date2);
		today = false;
		if (dt2.getDate() == day && (dt2.getMonth() + 1) == month
				&& (dt2.getYear() + 1900) == year) {
			txtDate.setText("TODAY");
			today = true;
		}

		btnToday.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.day_time));
		txtDate.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.day_time_hover));
		txtDate.setTextColor(Color.WHITE);
		btnToday.setTextColor(getResources().getColor(R.color.hotel_blue));
		btnTomorrow.setTextColor(getResources().getColor(R.color.hotel_blue));
		btnDafterTomorrow.setTextColor(getResources().getColor(
				R.color.hotel_blue));
		btnTomorrow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.day_time));
		btnDafterTomorrow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.day_time));

		hideEditText();

	}

	public void checkDate() {

		try {
			if (today) {
				Date dt = new Date();
				if (!time.equals("")) {
					SimpleDateFormat displayFormat = new SimpleDateFormat(
							"HH:mm");
					SimpleDateFormat parseFormat = new SimpleDateFormat(
							"hh:mm a");
					Date date = parseFormat.parse(time);
					if (dt.before(new Date((dt.getYear()), (dt.getMonth()), dt
							.getDate(), Integer.parseInt(displayFormat.format(
							date).split(":")[0]), Integer
							.parseInt(displayFormat.format(date).split(":")[1])))) {

					} else {
						lastbtnTimeView.setBackgroundDrawable(getResources()
								.getDrawable(R.drawable.day_time));

						TextView lb = (TextView) lastbtnTimeView;
						lb.setTextColor(getResources().getColor(
								R.color.hotel_blue));

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	public boolean checkTime(View v) {
		TextView t = (TextView) v;
		try {
			if (today) {
				Date dt = new Date();
				SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
				SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
				Date date = parseFormat.parse(t.getText().toString());
				if (dt.before(new Date((dt.getYear()), (dt.getMonth()), dt
						.getDate(), Integer.parseInt(displayFormat.format(date)
						.split(":")[0]), Integer.parseInt(displayFormat.format(
						date).split(":")[1])))) {

					return true;
				} else {
					return false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	class BtnTime implements OnClickListener {

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {

			Date tm = new Date();

			try {

				if (checkTime(v)) {

					if (lastbtnTimeView != null) {
						lastbtnTimeView.setBackgroundDrawable(getResources()
								.getDrawable(R.drawable.day_time));

						TextView lb = (TextView) lastbtnTimeView;
						lb.setTextColor(getResources().getColor(
								R.color.hotel_blue));
					}
					lastbtnTimeView = v;
					v.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.day_time_hover));

					TextView b = (TextView) v;
					b.setTextColor(Color.WHITE);
					time = b.getText().toString();
				} else {
					Toast.makeText(context,
							"Selected time must not be prior to current time.",
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			hideEditText();
		}

	}

	/*class BtnOccation implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (lastbtnOccastion != null) {
				if (lastbtnOccastion.getId() == 1) {
					lastbtnOccastion.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.brithday));
				} else if (lastbtnOccastion.getId() == 2) {
					lastbtnOccastion.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.baby));
				} else if (lastbtnOccastion.getId() == 3) {
					lastbtnOccastion.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.ff_party));
				} else if (lastbtnOccastion.getId() == 4) {
					lastbtnOccastion.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.engagement));
				} else if (lastbtnOccastion.getId() == 5) {
					lastbtnOccastion.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.date));
				} else if (lastbtnOccastion.getId() == 6) {
					lastbtnOccastion.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.meeting));
				} else if (lastbtnOccastion.getId() == 7) {
					lastbtnOccastion.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.other));
				}

			}
			lastbtnOccastion = v;
			v.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.time_box_orange_filled));

			if (v.getId() == 1) {
				btnBirthday.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.brithday_hover));
				occasion = "Birth Day";
			} else if (v.getId() == 2) {
				btnBaby.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.baby_hover));
				occasion = "Baby";
			} else if (v.getId() == 3) {
				btnValentine.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.ff_party_hover));
				occasion = "Party";
			} else if (v.getId() == 4) {
				btnEngadge.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.engagement_hover));
				occasion = "Engagement";
			} else if (v.getId() == 5) {
				btnDate.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.date_hover));
				occasion = "Date";
			} else if (v.getId() == 6) {
				btnMeeting.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.meeting_hover));
				occasion = "Meeting";
			} else if (v.getId() == 7) {
				btnOther.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.other_hover));
				occasion = "Other";
			}
			hideEditText();
		}
	}*/

	private void selectItem(int pos) {
		// mDrawerList.setItemChecked(pos, true);
		// setTitle(mPlanetTitles.get(pos));
		// mDrawerLayout.closeDrawer(mDrawerList);
		Intent intent = null;
		if (pos == 0) {
			intent = new Intent(context, RegisterActivity.class);

		} else if (pos == 1) {
			intent = new Intent(context, Category_Items.class);
		} else if (pos == 2) {
			// intent = new Intent(context, MainActivity.class);
			startActivity(new Intent(context, MainActivity.class));
		} else if (pos == 3) {
			Toast.makeText(context, "Coming Soon..", Toast.LENGTH_SHORT).show();
		} else if (pos == 4) {
			Intent contactIntent = new Intent(context, Contact_Screen.class);
			contactIntent.putExtra("hotel", "");
			contactIntent.putExtra("occasion", "");
			contactIntent.putExtra("time", "");
			context.startActivity(contactIntent);
			finish();
		} else if (pos == 5) {
			String username = NaanKing_Urls.prefs.getString("bookTablePhone",
					"");
			String password = NaanKing_Urls.prefs.getString("password", "");
			String UserID = NaanKing_Urls.prefs.getString("userid", "");
			if (username.equals("") || password.equals("")) {
				Intent inteLoginForHistory = new Intent(context, US_Login.class);
				inteLoginForHistory.putExtra("SCREEN", "H");
				startActivity(inteLoginForHistory);
				finish();
			} else {
				if (NaanKing_Urls.IS_GENERAL) {
					Intent itemIntent = new Intent(context,
							History_Restaurant_Items.class);
					itemIntent.putExtra("cus_id", UserID);
					itemIntent
							.putExtra("hotel_id", "" + NaanKing_Urls.HOTEL_ID);
					startActivity(itemIntent);
					finish();
				} else {
					Intent historyIntent = new Intent(context,
							History_All_Restaurant.class);

					startActivity(historyIntent);
					finish();
				}
			}
		} else if (pos == 6) {
			intent = new Intent(context, Coupons.class);
		} 
		else if(pos==7){
			intent = new Intent(context, Contact_us.class);
			}

		else if (pos == 8) {
			intent = new Intent(context, Settings.class);
		} else if (pos == 9) {
			final SharedPreferences.Editor editPref = NaanKing_Urls.prefs
					.edit();
			editPref.putString("bookTableName", "");
			editPref.putString("bookTablePhone", "");
			editPref.putString(Constants.Billing_Email, "");
			editPref.putString("LoggedInAs", "");
			editPref.putString("LoggedInAsName", "");

			editPref.commit();
			intent = new Intent(context, Login.class);
		}
		if (intent != null) {
			startActivity(intent);
			finish();
		}

	}

	class DrawerBaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (NaanKing_Urls.IsSkip) {
				return mPlanetTitles.size() - 1;
			}
			return mPlanetTitles.size();
		}

		@Override
		public Object getItem(int position) {

			return mPlanetTitles.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view = convertView;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {

				if (position == 0) {
					view = inflater.inflate(R.layout.drawer_list_item_first,
							parent, false);

				} else {
					view = inflater.inflate(R.layout.drawer_list_item, parent,
							false);
				}
				ImageView img = (ImageView) view.findViewById(R.id.imgDrawer);
				if (position == 0) {

					img.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.mk_a_reservation));

				} else if (position == 1) {
					img.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.mk_a_online));
				} else if (position == 2) {
					img.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.mk_a_nav));
					LinearLayout imgCount = (LinearLayout) view
							.findViewById(R.id.imgCountDrawer);
					imgCount.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.orange_dot));
					TextView txtCount = (TextView) view
							.findViewById(R.id.txtCountDrawer);
					txtCount.setText("" + NaanKing_Urls.NOTIFICATION_NO);
				} else if (position == 3) {
					img.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.ic_nav_branch));
					LinearLayout imgCount = (LinearLayout) view
							.findViewById(R.id.imgCountDrawer);
					imgCount.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.blude_dot));
					TextView txtCount = (TextView) view
							.findViewById(R.id.txtCountDrawer);
					txtCount.setText("" + NaanKing_Urls.FAVORITE_NO);
					txtCount.setTextColor(getResources().getColor(
							R.color.hotel_blue));
				} else if (position == 4) {
					img.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.ic_nav_invitefriend));
				} else if (position == 5) {
					img.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.ic_nav_history));
				} else if (position == 6) {
					img.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.ic_nav_coupon));
				} 
				else if (position == 7) {
					img.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.ic_nav_invitefriend));
				}
				else if (position == 8) {
					img.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.mk_a_setting));
				} else if (position == 9) {
					img.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.mk_a_signout));
				}
				TextView txt = (TextView) view.findViewById(R.id.txtDrawer);
				txt.setText(mPlanetTitles.get(position));

				view.setId(position);
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						selectItem(v.getId());

					}
				});

			} else {
				/*
				 * view = inflater.inflate(R.layout.drawer_list_item, parent,
				 * false); if(position==0){ view =
				 * inflater.inflate(R.layout.drawer_list_item_first, parent,
				 * false);
				 * 
				 * holder=(ViewHolder) convertView.getTag(); holder.img=
				 * (ImageView) view.findViewById(R.id.imgDrawer);
				 * holder.img.setBackgroundDrawable(getResources().getDrawable(
				 * R.drawable.mk_a_reservation)); holder.txtTitle = (TextView)
				 * view.findViewById(R.id.txtDrawer);
				 * holder.txtTitle.setText(mPlanetTitles.get(position)); } else
				 * if (position==1){ holder=(ViewHolder) convertView.getTag();
				 * holder.img= (ImageView) view.findViewById(R.id.imgDrawer);
				 * holder.img.setBackgroundDrawable(getResources().getDrawable(
				 * R.drawable.mk_a_online)); holder.txtTitle = (TextView)
				 * view.findViewById(R.id.txtDrawer);
				 * holder.txtTitle.setText(mPlanetTitles.get(position)); } else
				 * if (position == 2) { holderWithText=(ViewHolderWithText)
				 * convertView.getTag(); holderWithText.img= (ImageView)
				 * view.findViewById(R.id.imgDrawer); holderWithText.txt=new
				 * TextView(context);
				 * holderWithText.img.setBackgroundDrawable(getResources
				 * ().getDrawable( R.drawable.orange_dot));
				 * holderWithText.txt.setText("" +
				 * NaanKing_Urls.NOTIFICATION_NO); holderWithText.txtTitle =
				 * (TextView) view.findViewById(R.id.txtDrawer);
				 * holderWithText.txtTitle.setText(mPlanetTitles.get(position));
				 * 
				 * } else if(position ==3){ holderWithText=(ViewHolderWithText)
				 * convertView.getTag(); holderWithText.img= (ImageView)
				 * view.findViewById(R.id.imgDrawer); holderWithText.txt=new
				 * TextView(context);
				 * holderWithText.img.setBackgroundDrawable(getResources
				 * ().getDrawable( R.drawable.blude_dot));
				 * 
				 * holderWithText.txt.setText(""+NaanKing_Urls.FAVORITE_NO);
				 * holderWithText.txt.setTextColor(getResources().getColor(
				 * R.color.hotel_blue)); holderWithText.txtTitle = (TextView)
				 * view.findViewById(R.id.txtDrawer);
				 * holderWithText.txtTitle.setText(mPlanetTitles.get(position));
				 * } else if (position == 4) { holder=(ViewHolder)
				 * convertView.getTag(); holder.img= (ImageView)
				 * view.findViewById(R.id.imgDrawer);
				 * holder.img.setBackgroundDrawable(getResources().getDrawable(
				 * R.drawable.mk_a_fav)); holder.txtTitle = (TextView)
				 * view.findViewById(R.id.txtDrawer);
				 * holder.txtTitle.setText(mPlanetTitles.get(position)); } else
				 * if (position == 5) { holder=(ViewHolder)
				 * convertView.getTag(); holder.img= (ImageView)
				 * view.findViewById(R.id.imgDrawer);
				 * holder.img.setBackgroundDrawable(getResources().getDrawable(
				 * R.drawable.mk_a_fav)); holder.txtTitle = (TextView)
				 * view.findViewById(R.id.txtDrawer);
				 * holder.txtTitle.setText(mPlanetTitles.get(position)); } else
				 * if (position == 6) {
				 * 
				 * holder=(ViewHolder) convertView.getTag(); holder.img=
				 * (ImageView) view.findViewById(R.id.imgDrawer);
				 * holder.img.setBackgroundDrawable(getResources().getDrawable(
				 * R.drawable.mk_a_setting)); holder.txtTitle = (TextView)
				 * view.findViewById(R.id.txtDrawer);
				 * holder.txtTitle.setText(mPlanetTitles.get(position)); } else
				 * if (position == 7) { holder=(ViewHolder)
				 * convertView.getTag(); holder.img= (ImageView)
				 * view.findViewById(R.id.imgDrawer);
				 * holder.img.setBackgroundDrawable(getResources().getDrawable(
				 * R.drawable.mk_a_signout)); holder.txtTitle = (TextView)
				 * view.findViewById(R.id.txtDrawer);
				 * holder.txtTitle.setText(mPlanetTitles.get(position)); }
				 */
			}

			return view;
		}

	}

}
