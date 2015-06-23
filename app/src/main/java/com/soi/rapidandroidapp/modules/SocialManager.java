package com.soi.rapidandroidapp.modules;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.text.Html;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.soi.rapidandroidapp.R;
import com.soi.rapidandroidapp.models.SocialShareModel;
import com.soi.rapidandroidapp.models.SocialType;
import com.soi.rapidandroidapp.utilities.StringUtils;
import com.soi.rapidandroidapp.utilities.Utils;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Spiros I. Oikonomakis on 11/8/14.
 */
public class SocialManager {
    
    @Inject
    Utils utils;

    public static final String FEED_DESC    = "description";
    public static final String FEED_TITLE   = "title";
    public static final String FEED_CAPTION = "caption";
    public static final String FEED_DESCR   = "description";
    public static final String FEED_PICTURE = "picture";
    public static final String FEED_LINK    = "link";
    
    private static SocialManager _instance;
    private Context mContext;
    
    public synchronized static SocialManager getInstance(Context context)
    {
        if (_instance == null) {
            _instance = new SocialManager(context);
        }
        return _instance;
    }

    private SocialManager() {
    }


    /**
     * Initialises facebook configuration
     */
    public void facebookInit()
    {
        FacebookSdk.sdkInitialize(mContext);
    }

    /**
     * Initialises twitter configuration
     */
    public Twitter twitterInit()
    {
        TwitterAuthConfig twitterAuthConfig = new TwitterAuthConfig(mContext.getString(R.string.twitter_consumer_key),
                mContext.getString(R.string.twitter_consumer_secret));
        return new Twitter(twitterAuthConfig);
    }

    /**
     * Returns the ShareLinkContent for FB sharing
     * @param params
     * @return
     */
    public ShareLinkContent getFbShareLinKContent(Map<String,String> params)
    {
        ShareLinkContent.Builder builder = new ShareLinkContent.Builder();
        if (params.containsKey(FEED_DESCR))
            builder.setContentDescription(params.get(FEED_DESCR));

        if (params.containsKey(FEED_TITLE))
            builder.setContentTitle(params.get(FEED_TITLE));

        if (params.containsKey(FEED_PICTURE))
            builder.setImageUrl(Uri.parse(params.get(FEED_PICTURE)));


        if (params.containsKey(FEED_LINK))
            builder.setContentUrl(Uri.parse(params.get(FEED_LINK)));


        return builder.build();
    }

    /**
     * Returns the tweet composer
     * @param message
     * @return TweetComposer.Builder
     */
    public TweetComposer.Builder getTweetContent(String message)
    {
        return new TweetComposer.Builder(mContext).text(message);
    }

    /**
     * Returns the standard text with the given sponsor code
     * @return
     */
    public ShareLinkContent getFbShareLinKContent(String descr, String url)
    {
        return new ShareLinkContent.Builder()
                .setContentDescription(descr)
                .setContentUrl(Uri.parse(url)).build();
    }

    private SocialManager(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Open each social app or browser to share
     * content
     * @param act current activity
     * @param socialToShare the type of the share
     * @param title the title if is the Facebook or anything else
     * @param text the text which will be shared
     * @param shareUrl the url which will be shared
     */
    public void shareSocial(Activity act,
                            SocialType socialToShare,
                            String title,
                            String text,
                            String shareUrl,
                            String[] recipients,
                            boolean fromGmail)
    {
        switch (socialToShare) {
            case TWITTER:
                this._tweetShare(act, title, text);
                break;
            case EMAIL:
                this._emailShare(act, title, text, recipients, fromGmail);
                break;
        }
    }


    /**
     * Share via twitter app and fail over scenario
     * to open the browser for sharing
     * @param act
     * @param title
     * @param description
     */
    private void _tweetShare(Activity act, String title, String description)
    {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.setPackage("com.twitter.android");
            shareIntent.putExtra(Intent.EXTRA_TITLE, title);
            shareIntent.putExtra(Intent.EXTRA_TEXT, description);
            act.startActivity(shareIntent);
        } catch (ActivityNotFoundException ex) {
            String tweetUrl = StringUtils.join(" ", "https://twitter.com/intent/tweet?text=", title, description);
            Uri uri = Uri.parse(tweetUrl);
            act.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    /**
     * Share via email
     * @param act
     * @param title
     * @param text
     */
    private void _emailShare(Activity act, String title, String text, String[] recipients, boolean fromGmail)
    {

        if ( fromGmail ) {
            this.emailFromGmail(act, title, text, recipients);
            return;
        }

        // Email to Send
        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        if ( recipients.length > 0) {
            emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
        }
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(text));
        emailIntent.setType("message/rfc822");
        act.startActivity(emailIntent);
    }

    private void emailFromGmail(Activity act, String title, String text, String[] recipients) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("plain/text");
        sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        act.startActivity(sendIntent);
    }


    /**
     * Open youtube page from the app
     * otherwise in browser
     * @param activity
     */
    public final void openYoutube(Activity activity,  String url) {
        Intent intent = new Intent(
                Intent.ACTION_VIEW ,
                Uri.parse(url));
        intent.setComponent(new ComponentName("com.google.android.youtube","com.google.android.youtube.PlayerActivity"));

        PackageManager manager = activity.getPackageManager();
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
        if (infos.size() > 0) {
            activity.startActivity(intent);
        } else{
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    /**
     * Open twitter page from the app
     * otherwise in browser
     * @param act
     */
    public final void openTwitter(Activity act, String twitterId)
    {
        Intent intent = null;
        try {
            // get the Twitter app if possible
            act.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitterId));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+twitterId));
        }
        act.startActivity(intent);
    }


    /**
     * Open facebook page from the app
     * otherwise in browser
     * @param act
     */
    public final void openFacebook(Activity act, String pageName) {
        final String urlFb = "fb://profile/"+pageName;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If Facebook application is installed, use that else launch a browser
        final PackageManager packageManager = act.getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() == 0) {
            final String urlBrowser = "https://www.facebook.com/"+pageName;
            intent.setData(Uri.parse(urlBrowser));
        }

        act.startActivity(intent);
    }

    public List<SocialShareModel> getAvailableSocialList(Context context)
    {
        List<SocialShareModel> socialList = new ArrayList<SocialShareModel>();
        socialList.add(new SocialShareModel("Share on facebook", context.getResources().getDrawable(R.drawable.fb_icon), SocialType.FACEBOOK));
        socialList.add(new SocialShareModel("Share on twitter",  context.getResources().getDrawable(R.drawable.twitter_icon), SocialType.TWITTER));
        return socialList;
    }

}