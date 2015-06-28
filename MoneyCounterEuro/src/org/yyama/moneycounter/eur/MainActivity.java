package org.yyama.moneycounter.eur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/*
 * 
 */
public class MainActivity extends AppCompatActivity implements OnClickListener,
		OnTouchListener {
	int[] centValue = { 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000,
			10000, 20000, 50000 };
	Map<Integer, Integer> data = new HashMap<Integer, Integer>();
	CountDialog cDialog = new CountDialog();
	Map<Integer, Integer> idMapNum = new HashMap<Integer, Integer>();
	Map<Integer, Integer> idMapSum = new HashMap<Integer, Integer>();
	Map<Integer, Integer> idMapTxt = new HashMap<Integer, Integer>();
	CountDialog countDialog;
	int[] numIds = { R.id.Num001ct, R.id.Num002ct, R.id.Num005ct,
			R.id.Num010ct, R.id.Num020ct, R.id.Num050ct, R.id.Num001er,
			R.id.Num002er, R.id.Num005er, R.id.Num010er, R.id.Num020er,
			R.id.Num050er, R.id.Num100er, R.id.Num200er, R.id.Num500er };
	int[] sumIds = { R.id.Sum001ct, R.id.Sum002ct, R.id.Sum005ct,
			R.id.Sum010ct, R.id.Sum020ct, R.id.Sum050ct, R.id.Sum001er,
			R.id.Sum002er, R.id.Sum005er, R.id.Sum010er, R.id.Sum020er,
			R.id.Sum050er, R.id.Sum100er, R.id.Sum200er, R.id.Sum500er };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		for (int i : centValue) {
			data.put(i, 0);
		}

		((Button) findViewById(R.id.btnClear)).setOnClickListener(this);

		((TextView) findViewById(R.id.Text500er)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text200er)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text100er)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text050er)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text020er)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text010er)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text005er)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text002er)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text001er)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text050ct)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text020ct)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text020ct)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text010ct)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text005ct)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text002ct)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text001ct)).setOnClickListener(this);

		findViewById(R.id.img500er).setOnTouchListener(this);
		findViewById(R.id.img200er).setOnTouchListener(this);
		findViewById(R.id.img100er).setOnTouchListener(this);
		findViewById(R.id.img050er).setOnTouchListener(this);
		findViewById(R.id.img020er).setOnTouchListener(this);
		findViewById(R.id.img010er).setOnTouchListener(this);
		findViewById(R.id.img005er).setOnTouchListener(this);
		findViewById(R.id.img002er).setOnTouchListener(this);
		findViewById(R.id.img001er).setOnTouchListener(this);
		findViewById(R.id.img050ct).setOnTouchListener(this);
		findViewById(R.id.img020ct).setOnTouchListener(this);
		findViewById(R.id.img010ct).setOnTouchListener(this);
		findViewById(R.id.img005ct).setOnTouchListener(this);
		findViewById(R.id.img002ct).setOnTouchListener(this);
		findViewById(R.id.img001ct).setOnTouchListener(this);

		idMapNum.put(R.id.Text001ct, R.id.Num001ct);
		idMapNum.put(R.id.Text002ct, R.id.Num002ct);
		idMapNum.put(R.id.Text005ct, R.id.Num005ct);
		idMapNum.put(R.id.Text010ct, R.id.Num010ct);
		idMapNum.put(R.id.Text020ct, R.id.Num020ct);
		idMapNum.put(R.id.Text050ct, R.id.Num050ct);
		idMapNum.put(R.id.Text001er, R.id.Num001er);
		idMapNum.put(R.id.Text002er, R.id.Num002er);
		idMapNum.put(R.id.Text005er, R.id.Num005er);
		idMapNum.put(R.id.Text010er, R.id.Num010er);
		idMapNum.put(R.id.Text020er, R.id.Num020er);
		idMapNum.put(R.id.Text050er, R.id.Num050er);
		idMapNum.put(R.id.Text100er, R.id.Num100er);
		idMapNum.put(R.id.Text200er, R.id.Num200er);
		idMapNum.put(R.id.Text500er, R.id.Num500er);

		idMapSum.put(R.id.Text001ct, R.id.Sum001ct);
		idMapSum.put(R.id.Text002ct, R.id.Sum002ct);
		idMapSum.put(R.id.Text005ct, R.id.Sum005ct);
		idMapSum.put(R.id.Text010ct, R.id.Sum010ct);
		idMapSum.put(R.id.Text020ct, R.id.Sum020ct);
		idMapSum.put(R.id.Text050ct, R.id.Sum050ct);
		idMapSum.put(R.id.Text001er, R.id.Sum001er);
		idMapSum.put(R.id.Text002er, R.id.Sum002er);
		idMapSum.put(R.id.Text005er, R.id.Sum005er);
		idMapSum.put(R.id.Text010er, R.id.Sum010er);
		idMapSum.put(R.id.Text020er, R.id.Sum020er);
		idMapSum.put(R.id.Text050er, R.id.Sum050er);
		idMapSum.put(R.id.Text100er, R.id.Sum100er);
		idMapSum.put(R.id.Text200er, R.id.Sum200er);
		idMapSum.put(R.id.Text500er, R.id.Sum500er);

		int idx = 0;
		idMapTxt.put(R.id.Text001ct, centValue[idx++]);
		idMapTxt.put(R.id.Text002ct, centValue[idx++]);
		idMapTxt.put(R.id.Text005ct, centValue[idx++]);
		idMapTxt.put(R.id.Text010ct, centValue[idx++]);
		idMapTxt.put(R.id.Text020ct, centValue[idx++]);
		idMapTxt.put(R.id.Text050ct, centValue[idx++]);
		idMapTxt.put(R.id.Text001er, centValue[idx++]);
		idMapTxt.put(R.id.Text002er, centValue[idx++]);
		idMapTxt.put(R.id.Text005er, centValue[idx++]);
		idMapTxt.put(R.id.Text010er, centValue[idx++]);
		idMapTxt.put(R.id.Text020er, centValue[idx++]);
		idMapTxt.put(R.id.Text050er, centValue[idx++]);
		idMapTxt.put(R.id.Text100er, centValue[idx++]);
		idMapTxt.put(R.id.Text200er, centValue[idx++]);
		idMapTxt.put(R.id.Text500er, centValue[idx++]);

		addSetting();
		add2Init();
		add2Setting();
		load(FILE_NAME);
		draw();
	}

	private boolean isStarted;

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (!isStarted) {
			initVew();
			isStarted = true;
		}
	}

	private void initVew() {
		int hight = (int) ((findViewById(R.id.Text500er).getHeight()) * 0.9);
		Log.d("yyama", "高さ" + String.valueOf(hight));
		ImageView iv;

		iv = (ImageView) findViewById(R.id.img500er);
		int orgH = iv.getHeight();
		Log.d("yyama", "オリジナルの高さ" + orgH);
		int orgW = iv.getWidth();
		Log.d("yyama", "オリジナルの幅" + orgW);
		double raito = (double) hight / (double) orgH;
		Log.d("yyama", "比率" + raito);
		TableRow.LayoutParams prm = new TableRow.LayoutParams(
				(int) (orgW * raito), hight);
		prm.rightMargin = 0;
		prm.leftMargin = 0;
		prm.weight = 0;
		prm.gravity = Gravity.CENTER;
		prm.column = 0;

		// イメージを縮小して表示
		iv = (ImageView) findViewById(R.id.img500er);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img200er);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img100er);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img050er);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img020er);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img010er);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img005er);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img002er);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img001er);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img050ct);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img020ct);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img010ct);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img005ct);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img002ct);
		iv.setLayoutParams(prm);
		iv = (ImageView) findViewById(R.id.img001ct);
		iv.setLayoutParams(prm);
	}

	private AdView adView;

	private void addSetting() {
		// adView を作�?する
		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-2505812570403600/5077055775");
		adView.setAdSize(AdSize.BANNER);

		// 属�? android:id="@+id/mainLayout" が与えられて�?��も�?として
		// LinearLayout をル�?��ア�??する
		LinearLayout layout = (LinearLayout) findViewById(R.id.adSpace);

		// adView を追�?���?
		layout.addView(adView);

		// �?���?��リクエストを行う
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				"F3B1B2779DEF816F9B31AA6C6DC57C3F").build();
		// AdRequest adRequest = new AdRequest.Builder().build();

		// �?��リクエストを行って adView を読み込�?
		adView.loadAd(adRequest);

	}

	private InterstitialAd interstitial;

	// 全画面�?��の方�?
	private void add2Init() {
		// インタース�?��シャルを作�?する�?
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId("ca-app-pub-2505812570403600/1983988577");

		// Set the AdListener.
		interstitial.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				add2Setting();
			}
		});
	}

	// 全画面�?��の方�?回表示するた�?にロードしなおす�?��があるみたい�?
	private void add2Setting() {
		if (!interstitial.isLoaded()) {
			Log.d("yyama", "未ロード�?ため、リクエストします�?");
			// �?��リクエストを作�?する�?
			AdRequest adRequest = new AdRequest.Builder().addTestDevice(
					"F3B1B2779DEF816F9B31AA6C6DC57C3F").build();
			// インタース�?��シャルの読み込みを開始する�?
			interstitial.loadAd(adRequest);
		}
	}

	public void draw() {
		int sum = 0;
		NumberFormat nf = NumberFormat.getInstance();
		for (int i = 0; i < numIds.length; i++) {
			((TextView) findViewById(numIds[i])).setText(" "
					+ String.format("%,d", data.get(centValue[i])));
			int num = 0;

			num = centValue[i];
			((TextView) findViewById(sumIds[i])).setText("€ "
					+ nf.format((double) data.get(centValue[i]) * (double) num
							/ 100.0d));
			sum += data.get(centValue[i]) * num;
		}
		((TextView) findViewById(R.id.allSum)).setText("€ "
				+ nf.format((double) sum / 100.0d));
	}

	@Override
	public void onClick(final View v) {
		add2Setting();
		if (v.getId() == R.id.btnClear) {
			Clear();
		} else {
			showCountDialog(v);
		}
	}

	private void save() {
		OutputStream out;
		try {
			out = openFileOutput(FILE_NAME, MODE_PRIVATE);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,
					"UTF-8"));
			// 追記す�?
			for (int i = 0; i < centValue.length; i++) {
				writer.append(centValue[i] + "," + data.get(centValue[i])
						+ System.getProperty("line.separator"));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static String FILE_NAME = "CountDialog.dat";

	private void load(String fileName) {
		InputStream in;
		String lineBuffer;

		try {
			in = openFileInput(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "UTF-8"));
			while ((lineBuffer = reader.readLine()) != null) {
				String[] strs = lineBuffer.split(",");
				Integer kind = Integer.parseInt(strs[0]);
				data.put(kind, Integer.parseInt(strs[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showCountDialog(final View v) {
		LayoutInflater li = LayoutInflater.from(this);
		final View dialog = li.inflate(R.layout.dialog, null);
		int now = getNow(v.getId());
		((TextView) dialog.findViewById(R.id.edit))
				.setText(String.valueOf(now));
		((TextView) dialog.findViewById(R.id.edit))
				.setText(String.valueOf(now));
		AlertDialog.Builder builder = new Builder(this);
		builder.setView(dialog);
		builder.setTitle(((TextView) v).getText() + " ");
		builder.setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface df, int which) {
				String editStr = ((EditText) dialog.findViewById(R.id.edit))
						.getText().toString();
				int num;
				// 数値判�?
				try {
					num = Integer.parseInt(editStr);
				} catch (NumberFormatException e) {
					return;
				}
				Log.d("yyama2", String.valueOf(v.getId()));
				data.put(idMapTxt.get(v.getId()), num);
				save();
				draw();
			}
		});
		((Button) dialog.findViewById(R.id.btnPlus1))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnPlus5))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnPlus10))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnPlus50))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus1))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus5))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus10))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus50))
				.setOnClickListener(cDialog);
		builder.show();
	}

	private void Clear() {
		// 確認ダイアログの生�?
		AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
		alertDlg.setTitle(getString(R.string.all_crear_confirm_title));
		alertDlg.setMessage(getString(R.string.all_crear_confirm_msg));
		alertDlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				for (int i : centValue) {
					data.put(i, 0);
				}
				save();
				draw();
			}
		});
		alertDlg.setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		alertDlg.create().show();
	}

	private int getNow(int id) {
		String str = ((TextView) findViewById(idMapNum.get(id))).getText()
				.toString().replace(",", "").replace(" ", "");
		Log.d("yyama", str);
		int now = Integer.parseInt(str);
		Log.d("yyama", String.valueOf(now));
		return now;
	}

	@Override
	public void onPause() {
		adView.pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		adView.resume();
	}

	@Override
	public void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	public static final int REQUEST_CD = 100;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.screenshot:

			// TODO
			// ・文字を小さくする→全ての操作を無効→スクリーンショットを非同期でとる→操作を有効にする。
			Screenshot.saveScreen(MainActivity.this);
			if (interstitial.isLoaded()) {
				Log.d("yyama", "インターステシャルはロードされています。");
				interstitial.show();
			} else {
				Log.d("yyama", "インターステシャルはロードされていません。");
			}
			add2Setting();

			break;
		case R.id.file_save:
			// ファイル保�?
			saveLocalFile();
			break;
		case R.id.file_open:
			// ファイル選択アク�?��ビティを表示
			Intent intent = new Intent(this, LocalFileActivity.class);
			this.startActivityForResult(intent, REQUEST_CD);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void saveLocalFile() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final EditText edit = new EditText(this);
		edit.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(edit);
		builder.setTitle(this.getString(R.string.please_memo));

		builder.setNegativeButton(R.string.cancel,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.setPositiveButton(R.string.saveFile,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String fileName = String.format(
								"MC_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS_%2$s.txt",
								Calendar.getInstance(), edit.getText());
						PrintWriter pw = null;
						try {
							pw = new PrintWriter(MainActivity.this
									.openFileOutput(fileName,
											Activity.MODE_PRIVATE));
							// 追記す�?
							for (int i : centValue) {
								pw.append(i + "," + data.get(i)
										+ System.getProperty("line.separator"));
							}
							pw.flush();
							Toast.makeText(MainActivity.this,
									R.string.has_been_saved, Toast.LENGTH_LONG)
									.show();
							if (interstitial.isLoaded()) {
								Log.d("yyama", "インターステシャルはロードされています。?");
								Random rnd = new Random();
								if (rnd.nextInt(2) == 0) {
									interstitial.show();
								}
							} else {
								Log.d("yyama", "インターステシャルはロードされていません。");
							}
							add2Setting();

						} catch (Exception e) {
							Toast.makeText(MainActivity.this,
									R.string.failed_to_save, Toast.LENGTH_LONG)
									.show();
						} finally {
							if (!(pw == null)) {
								pw.close();
							}
						}
					}
				});
		final AlertDialog dialog = builder.create();
		edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					dialog.getButton(AlertDialog.BUTTON_POSITIVE)
							.performClick();
				}
				return true;
			}
		});
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				// ソフトキーボ�?ドを出�?
				InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.showSoftInput(edit, 0);
			}
		});
		dialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CD) {
			switch (resultCode) {
			case RESULT_OK:
				try {
					load(data.getStringExtra("file_name"));
					save();
					draw();
					Toast.makeText(
							this,
							getString(R.string.opend_file)
									+ System.getProperty("line.separator")
									+ data.getStringExtra("file_title"),
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(this, getString(R.string.file_open_error),
							Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_CANCELED:
				break;
			default:
				break;
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent arg1) {
		switch (arg1.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			ColorFilter cf = new LightingColorFilter(Color.rgb(157, 204, 224),
					getResources().getColor(R.color.blue));
			((ImageView) v).setColorFilter(cf);
			v.setBackgroundColor(getResources().getColor(R.color.skyblue));
			break;
		}
		case MotionEvent.ACTION_UP: {
			switch (v.getId()) {
			case R.id.img500er: {
				findViewById(R.id.Text500er).performClick();
				break;
			}
			case R.id.img200er: {
				findViewById(R.id.Text200er).performClick();
				break;
			}
			case R.id.img100er: {
				findViewById(R.id.Text100er).performClick();
				break;
			}
			case R.id.img050er: {
				findViewById(R.id.Text050er).performClick();
				break;
			}
			case R.id.img020er: {
				findViewById(R.id.Text020er).performClick();
				break;
			}
			case R.id.img010er: {
				findViewById(R.id.Text010er).performClick();
				break;
			}
			case R.id.img005er: {
				findViewById(R.id.Text005er).performClick();
				break;
			}
			case R.id.img002er: {
				findViewById(R.id.Text002er).performClick();
				break;
			}
			case R.id.img001er: {
				findViewById(R.id.Text001er).performClick();
				break;
			}
			case R.id.img050ct: {
				findViewById(R.id.Text050ct).performClick();
				break;
			}
			case R.id.img020ct: {
				findViewById(R.id.Text020ct).performClick();
				break;
			}
			case R.id.img010ct: {
				findViewById(R.id.Text010ct).performClick();
				break;
			}
			case R.id.img005ct: {
				findViewById(R.id.Text005ct).performClick();
				break;
			}
			case R.id.img002ct: {
				findViewById(R.id.Text002ct).performClick();
				break;
			}
			case R.id.img001ct: {
				findViewById(R.id.Text001ct).performClick();
				break;
			}
			}
		}
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_OUTSIDE: {
			((ImageView) v).setColorFilter(null);
			v.setBackgroundColor(Color.TRANSPARENT);
			break;
		}
		}
		return true;
	}
}
