package eniversity.com;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.eniversity.app.R;

public class WebViewActivity extends Activity {

	private WebView webView;

	public static String EXTRA_URL = "extra_url";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.webview_layout);

		setTitle("Login");

		final String url = this.getIntent().getStringExtra(EXTRA_URL);
		if (null == url) {
			Log.e("Twitter", "URL cannot be null");
			finish();
		}

		webView = (WebView) findViewById(R.id.webView);
		webView.setWebViewClient(new MyWebViewClient());
		webView.loadUrl(url);
	}

	class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			if (url.contains(MainActivity.TWITTER_CALLBACK_URL)) {
				Uri uri = Uri.parse(url);

				/* Sending results back */
				String verifier = uri
						.getQueryParameter(MainActivity.URL_TWITTER_OAUTH_VERIFIER);
				Intent resultIntent = new Intent();
				resultIntent.putExtra(MainActivity.URL_TWITTER_OAUTH_VERIFIER,
						verifier);

				setResult(RESULT_OK, resultIntent);

				/* closing webview */
				finish();
				return true;
			}
			return false;
		}
	}

}
