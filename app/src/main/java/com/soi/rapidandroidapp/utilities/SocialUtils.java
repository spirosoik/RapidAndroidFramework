package com.soi.rapidandroidapp.utilities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.Html;

import com.facebook.FacebookException;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.soi.rapidandroidapp.R;
import com.soi.rapidandroidapp.models.SocialType;

import java.util.List;

/**
 * Created by Spiros I. Oikonomakis on 10/16/14.
 */
public class SocialUtils {


    /**
     * Open each social app or browser to share
     * content
     * @param act current activity
     * @param uiHelper UI helper of current activity for FB
     * @param socialToShare the type of the share
     * @param title the title if is the Facebook or anything else
     * @param text the text which will be shared
     * @param shareUrl the url which will be shared
     */
    public void shareSocial(Activity act,
                            UiLifecycleHelper uiHelper,
                            SocialType socialToShare,
                            String title,
                            String text,
                            String shareUrl,
                            String[] recipients,
                            boolean fromGmail)
    {
        switch (socialToShare) {
            case FACEBOOK:
                this._fbAppShare(act, uiHelper, shareUrl, text);
                break;
            case TWITTER:
                this._tweetShare(act, title, text);
                break;
            case EMAIL:
                this._emailShare(act, title, text, recipients, fromGmail);
                break;
        }
    }

    /**
     * Share via facebook app and fail over scenario
     * to open the browser for sharing
     * @param act
     * @param uiHelper
     * @param link
     * @param description
     */
    private void _fbAppShare(Activity act, UiLifecycleHelper uiHelper, String link, String description)
    {
        try {
            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(act)
                    .setLink(link != null && !link.equals("") ? link : "")
                    .setDescription(description != null && !description.equals("") ? description : "")
                    .build();
            uiHelper.trackPendingDialogCall(shareDialog.present());
        } catch (FacebookException ex) {
            String urlParameters = String.format(act.getString(R.string.facebook_http_url),
                    act.getString(R.string.app_url),
                    act.getString(R.string.app_name));
            Utils.getInstance().openURL(act, urlParameters);
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
            String tweetUrl = StringUtils.join(" ","https://twitter.com/intent/tweet?text=", title, description);
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
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name="+twitterId));
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

    /**
     * Open soundcloud page from the app
     * otherwise in browser
     * @param act
     */
    public final void openSoundCloud(Activity act) {
        final String urlFb = act.getString(R.string.share_soundcloud);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If Facebook application is installed, use that else launch a browser
        final PackageManager packageManager = act.getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() == 0) {
            final String urlBrowser = act.getString(R.string.soundcloud_http_url);
            intent.setData(Uri.parse(urlBrowser));
        }

        act.startActivity(intent);
    }

    /**
     * Open podcast in browser
     * @param act
     */
    public final void openPodcast(Activity act)
    {
        final String urlBrowser = act.getString(R.string.podcast_url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlBrowser));
        act.startActivity(intent);

    }
}
