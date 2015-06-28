package org.yyama.moneycounter.eur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class Screenshot {
	static View view;
	private static final String baseDir = Environment
			.getExternalStorageDirectory().toString()
			+ "/"
			+ Environment.DIRECTORY_PICTURES;
	private static final String directory = baseDir + "/" + "MoneyConterEUR/";

	public static void saveScreen(final MainActivity act) {
		if (!new File(baseDir).isDirectory()) {
			// sdカードが認識できない場合
			Toast.makeText(act, R.string.no_sd_card, Toast.LENGTH_LONG).show();
			return;
		}
		try {
			createDir();
		} catch (Exception e1) {
			// フォルダが作成できない場合
			Toast.makeText(act, R.string.can_not_be_saved, Toast.LENGTH_LONG)
					.show();
			e1.printStackTrace();
			return;
		}

		final String fileName = String.format(
				"MoneyCounter_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS_%1$tL.png",
				Calendar.getInstance());

		// 広告とボタンを消す
		final View adView = (View) act.findViewById(R.id.adSpace);
		adView.setVisibility(View.INVISIBLE);
		final View crView = (View) act.findViewById(R.id.btnClear);
		crView.setVisibility(View.INVISIBLE);
		// スクショ用にマージンサイズの変更
		LinearLayout ll = (LinearLayout) act.findViewById(R.id.TableLayout1);
		final int orgMarginSize = ((MarginLayoutParams) ll.getChildAt(1)
				.getLayoutParams()).topMargin;
		for (int i = 0; i < ll.getChildCount(); i++) {
			TableRow tr = (TableRow) ll.getChildAt(i);
			MarginLayoutParams mlp = (MarginLayoutParams) tr.getLayoutParams();
			mlp.setMargins(mlp.leftMargin, 2, mlp.rightMargin, mlp.bottomMargin);
		}
		act.draw();
		// スクリーンショットを取る
		view = (View) act.findViewById(R.id.LinearLayout1).getParent()
				.getParent();
		view.setDrawingCacheEnabled(false);
		view.setDrawingCacheEnabled(true);

		new Thread(new Runnable() {
			Handler handler = new Handler();

			@Override
			public void run() {
				// 描画が終わるであろう時間スリープ
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				// 帯を書く
				final Bitmap saveBitmap = drowRibbon(Bitmap.createBitmap(view
						.getDrawingCache()));

				// 保存
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(new File(directory + fileName));
					saveBitmap.compress(CompressFormat.PNG, 100, fos);
					fos.flush();

				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException();
				} finally {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException();
					}
				}

				handler.post(new Runnable() {
					@Override
					public void run() {
						// マージンサイズを元に戻す
						LinearLayout ll = (LinearLayout) act
								.findViewById(R.id.TableLayout1);
						Log.d("yyama",
								"orgMarginSize(restore):"
										+ String.valueOf(orgMarginSize));
						for (int i = 0; i < ll.getChildCount(); i++) {
							TableRow tr = (TableRow) ll.getChildAt(i);
							MarginLayoutParams mlp = (MarginLayoutParams) tr
									.getLayoutParams();
							mlp.setMargins(mlp.leftMargin, orgMarginSize,
									mlp.rightMargin, mlp.bottomMargin);
						}

						// 日時を消して広告を再表示
						adView.setVisibility(View.VISIBLE);
						crView.setVisibility(View.VISIBLE);
						// スクショ保存完了メッセージ
						Toast.makeText(
								act,
								act.getString(R.string.saved_screenshot)
										+ System.getProperty("line.separator")
										+ act.getString(R.string.destination)
										+ "[" + Screenshot.getFolder() + "]",
								Toast.LENGTH_LONG).show();
						act.draw();
					}
				});

			}
		}).start();
	}

	private static Bitmap drowRibbon(Bitmap bitmap) {
		Canvas cvsBase = new Canvas(bitmap);

		// Paintの作成
		Paint paint;
		paint = new Paint();
		paint.setColor(0xB0b0e0e6);
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);

		// サイズを得る。
		int width = view.getWidth();
		int height = view.getHeight();
		Bitmap ribbon = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas cvsRbn = new Canvas(ribbon);
		cvsRbn.drawARGB(0, 0, 0, 0);

		// キャンバスに帯を書く
		int left = 0;
		int right = width;
		int top = Math.round(height * 0.88f);
		int bottom = Math.round(height * 0.99f);
		cvsRbn.drawRect(left, top, right, bottom, paint);
		paint.setColor(Color.WHITE);
		int w = (bottom - top) / 15;
		cvsRbn.drawRect(left, top + ((bottom - top) / 10), right, top
				+ ((bottom - top) / 10) + w, paint);
		cvsRbn.drawRect(left, bottom - ((bottom - top) / 10) - w, right, bottom
				- ((bottom - top) / 10), paint);

		paint.setColor(Color.TRANSPARENT);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

		Path path = new Path();
		// path.moveTo(left, top);
		// path.lineTo(width / 10, top + ((bottom - top) / 2));
		// path.lineTo(left, bottom);
		// path.close();

		path.moveTo(right, top);
		path.lineTo(right - (width / 10), top + ((bottom - top) / 2));
		path.lineTo(right, bottom);
		path.close();

		cvsRbn.drawPath(path, paint);
		cvsBase.drawBitmap(ribbon, 0, 0, null);

		String text1 = "Shooting date and time";
		String text2 = String.format(Locale.ENGLISH,
				"    %1$tb. %1$td,%1$tY %1$tH:%1$tM:%1$tS",
				Calendar.getInstance());
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(55);
		textPaint.setColor(0x90000080);
		FontMetrics fontMetrics = textPaint.getFontMetrics();
		float baseY = top + ((bottom - top) / 2.8f)
				- (fontMetrics.ascent + fontMetrics.descent) / 2;

		cvsBase.drawText(text1, width / 12, baseY, textPaint);
		baseY = top + ((bottom - top) / 1.5f)
				- (fontMetrics.ascent + fontMetrics.descent) / 2;
		cvsBase.drawText(text2, width / 7, baseY, textPaint);

		return bitmap;
	}

	private static void createDir() {
		File file = new File(directory);
		if (file.exists()) {
			return;
		}

		if (!(new File(directory).mkdirs())) {
			throw new RuntimeException("can't make directory!");
		}
	}

	public static boolean isFolderExists() {
		File dir = new File(directory);
		if (dir.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

	public static String getFolder() {
		return Uri.parse(directory).toString();
	}
}
