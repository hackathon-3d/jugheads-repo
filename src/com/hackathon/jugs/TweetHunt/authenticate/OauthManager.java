package com.hackathon.jugs.TweetHunt.authenticate;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.hackathon.jugs.TweetHunt.R;
import com.hackathon.jugs.TweetHunt.TweetData;

import java.util.logging.Handler;

/**
 * Created with IntelliJ IDEA.
 * User: RobCastellow
 * Date: 8/23/13
 * Time: 10:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class OauthManager extends ListActivity {
    //            com.twitter.android.oauth.token
    //            com.twitter.android.oauth.token.secret
    private static String TWITTER_ACCOUNT_TYPE = "com.twitter.android.auth.login";

    public void onCreate(Bundle savedInstanceState) {
        Log.i("OauthManager", "BAM");
        super.onCreate(savedInstanceState);
        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Bundle options = new Bundle();
        Account[] accountsByType = accountManager.getAccountsByType(TWITTER_ACCOUNT_TYPE);
        Log.d("accountsSize",String.valueOf(accountsByType.length));
        for (Account account: accountsByType){
            Log.d("accountName",account.name);
        }
        this.setListAdapter(new ArrayAdapter<Account>(this, R.layout.list_accounts, accountsByType));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Account account = (Account)getListView().getItemAtPosition(position);
        Intent intent = new Intent(this, TwitterInfo.class);
        intent.putExtra("account", account);
        TweetData data = (TweetData) getIntent().getExtras().get("data");
        intent.putExtra("data", data);
        startActivity(intent);
    }
}
