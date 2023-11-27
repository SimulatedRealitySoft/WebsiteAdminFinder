package dev.simulatedrealitysoft.adminfinder.scanner;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import dev.simulatedrealitysoft.adminfinder.R;
import dev.simulatedrealitysoft.adminfinder.utils.NotificationBar;
import dev.simulatedrealitysoft.adminfinder.utils.Utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Frg_Scanner extends Fragment {
    String Status;
    public AsyncTask TaskStart;
    public AsyncTask TaskStart2;
    Button browse;
    private Set<String> duplilines = new HashSet();
    List<String> listOfurl200;
    private ArrayList<String> logarray = null;
    String[] loginpath;
    String[] loginpath2;
    int loginpathint;
    int loginpathint2;
    CheckBox multiplesite;
    Boolean notifications;
    Spinner options;
    Spinner options2;
    TextView ouput;
    SharedPreferences saveoutput;
    Button scan;
    SharedPreferences sharedPrefs;
    CheckBox singlesite;
    TextView site;
    TextView sites;
    private Start start = null;
    Boolean timestampstatus;
    
    private static final String PREF_NAME = "MiPref";
    private static final String KEY_BOOLEAN = "miBooleano";

    private SharedPreferences sharedPreferences;
    
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.frg_scanner, viewGroup, false);
        setHasOptionsMenu(true);
        
        scan = (Button) inflate.findViewById(R.id.buttonscan);
        browse = (Button) inflate.findViewById(R.id.buttonbrowse);
        options = (Spinner) inflate.findViewById(R.id.spinner1);
        options2 = (Spinner) inflate.findViewById(R.id.spinner2);
        singlesite = (CheckBox) inflate.findViewById(R.id.checksingle);
        multiplesite = (CheckBox) inflate.findViewById(R.id.checkmultiple);
        site = (TextView) inflate.findViewById(R.id.site);
        sites = (TextView) inflate.findViewById(R.id.sites);
        ouput = (TextView) inflate.findViewById(R.id.outputView);
        ouput.setMovementMethod(new ScrollingMovementMethod());
        
        PreferenceManager.setDefaultValues(getActivity(), R.xml.settings_main, false);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(inflate.getContext());
        timestampstatus = Boolean.valueOf(this.sharedPrefs.getBoolean("timeStamp", false));
        notifications = Boolean.valueOf(this.sharedPrefs.getBoolean("showNotification", false));
        saveoutput = inflate.getContext().getSharedPreferences("PreferencesOuput", 0);
        if (saveoutput.contains("output")) {
            ouput.setText(saveoutput.getString("output", ""));
        }
        sites.setEnabled(false);
        options2.setEnabled(false);
        browse.setEnabled(false);
        singlesite.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Frg_Scanner.this.multiplesite.setChecked(false);
                    Frg_Scanner.this.site.setEnabled(true);
                    Frg_Scanner.this.options.setEnabled(true);
                    Frg_Scanner.this.sites.setEnabled(false);
                    Frg_Scanner.this.options2.setEnabled(false);
                    Frg_Scanner.this.browse.setEnabled(false);
                }
            });
        multiplesite.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Frg_Scanner.this.singlesite.setChecked(false);
                    Frg_Scanner.this.site.setEnabled(false);
                    Frg_Scanner.this.options.setEnabled(false);
                    Frg_Scanner.this.sites.setEnabled(true);
                    Frg_Scanner.this.options2.setEnabled(true);
                    Frg_Scanner.this.browse.setEnabled(true);
                }
            });
        browse.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                   pickFile();           
                }
            });
        scan.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (Frg_Scanner.this.singlesite.isChecked()) {
                        if (Frg_Scanner.this.scan.getText().toString().equals("STOP")) {
                            Frg_Scanner.this.TaskStart.cancel(true);
                            Frg_Scanner.this.scan.setText("SCAN");
                            return;
                        }
                        Frg_Scanner.this.scan.setText("STOP");
                        CharSequence text = Frg_Scanner.this.site.getText();
                        if (text.toString().equals("")) {
                            Frg_Scanner.this.scan.setText("SCAN");
                            Log.d("TAG", "Single site Empty, please enter a URL");
                            AlertDialog.Builder builder = new AlertDialog.Builder(Frg_Scanner.this.getActivity());
                            builder.setTitle(getString(R.string.dialog_warning_title));
                            builder.setMessage(getString(R.string.dialog_warning_url)).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                            AlertDialog dialog = builder.create();
                            dialog.getWindow().setBackgroundDrawableResource(R.color.bgCardView);
                            dialog.show();
                            
                        } else if (text.toString().endsWith("/")){
                            Frg_Scanner.this.scan.setText("SCAN");
                            Log.d("TAG", "Single site with slash, please enter a correct URL");
                            AlertDialog.Builder builder = new AlertDialog.Builder(Frg_Scanner.this.getActivity());
                            builder.setTitle(getString(R.string.dialog_warning_title));
                            builder.setMessage(getString(R.string.dialog_warning_slash)).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                            AlertDialog dialog = builder.create();
                            dialog.getWindow().setBackgroundDrawableResource(R.color.bgCardView);
                            dialog.show();
                        }else if (Patterns.WEB_URL.matcher(text.toString()).matches()) {
                            AsyncTask unused = Frg_Scanner.this.TaskStart = new Start().execute(new String[]{String.valueOf(Frg_Scanner.this.options.getSelectedItem()), text.toString()});
                        } else {
                            Frg_Scanner.this.scan.setText("SCAN");
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(Frg_Scanner.this.getActivity());
                            builder2.setTitle(getString(R.string.dialog_warning_title));
                            builder2.setMessage(getString(R.string.dialog_warning_malformed)).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                            AlertDialog dialog2 = builder2.create();
                            dialog2.getWindow().setBackgroundDrawableResource(R.color.bgCardView);
                            dialog2.show();
                        }
                    } else if (!Frg_Scanner.this.multiplesite.isChecked()) {
                        Log.d("TAG", "Nothing to do");
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(Frg_Scanner.this.getActivity());
                        builder3.setTitle(getString(R.string.dialog_warning_title));
                        builder3.setMessage(getString(R.string.dialog_warning_option)).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                        AlertDialog dialog3 = builder3.create();
                        dialog3.getWindow().setBackgroundDrawableResource(R.color.bgCardView);
                        dialog3.show();
                    } else if (Frg_Scanner.this.scan.getText().toString().equals("STOP")) {
                        Frg_Scanner.this.TaskStart2.cancel(true);
                        Frg_Scanner.this.scan.setText("SCAN");
                    } else {
                        Frg_Scanner.this.scan.setText("STOP");
                        CharSequence text2 = Frg_Scanner.this.sites.getText();
                        if (text2.toString().equals("")) {
                            Frg_Scanner.this.scan.setText("SCAN");
                            Log.d("TAG", "Multiple site Empty, please choose a File with the all URLs");
                            AlertDialog.Builder builder4 = new AlertDialog.Builder(Frg_Scanner.this.getActivity());
                            builder4.setTitle(getString(R.string.dialog_warning_title));
                            builder4.setMessage(getString(R.string.dialog_warning_hosts)).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                            AlertDialog dialog4 = builder4.create();
                            dialog4.getWindow().setBackgroundDrawableResource(R.color.bgCardView);
                            dialog4.show();
                            return;
                        }
                        AsyncTask unused2 = Frg_Scanner.this.TaskStart2 = new Start2().execute(new String[]{String.valueOf(Frg_Scanner.this.options2.getSelectedItem()), text2.toString()});
                    }
                }
            });
        return inflate;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        if (menuItem.getItemId() != R.id.clear) {
            return super.onOptionsItemSelected(menuItem);
        }
        ouput.setText(R.string.consoletext);
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor edit = getActivity().getSharedPreferences("PreferencesOuput", 0).edit();
        edit.putString("output", ouput.getText().toString());
        edit.commit();
    }
    
    // pick file
    
    public void pickFile(){
        DialogProperties dialogProperties = new DialogProperties();
        dialogProperties.selection_mode = 0;
        dialogProperties.selection_type = 0;
        dialogProperties.root = new File("/sdcard");
        dialogProperties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        dialogProperties.offset = new File(DialogConfigs.DEFAULT_DIR);
        dialogProperties.extensions = null;
        FilePickerDialog filePickerDialog = new FilePickerDialog(Frg_Scanner.this.getActivity(), dialogProperties);
        filePickerDialog.setTitle(getString(R.string.fd_select_file));
        filePickerDialog.setDialogSelectionListener(new DialogSelectionListener() {
                public void onSelectedFilePaths(String[] strArr) {
                    Frg_Scanner.this.sites.setText(strArr[0]);
                }
            });
        filePickerDialog.show();
    }
    

    private class Start extends AsyncTask<String, String, Void> {
        public static final int CONNECTION_TIMEOUT = 15000;
        public static final int READ_TIMEOUT = 15000;
        public static final String REQUEST_METHOD = "GET";

        private Start() {
        }

        public Void doInBackground(String... strArr) {
            String str = strArr[0];
            String[] strArr2 = {strArr[1]};
            if (str.equals("php")) {
                Frg_Scanner.this.loginpathint = LoginsList.php.length;
                Frg_Scanner.this.loginpath = LoginsList.php;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath));
            } else if (str.equals("asp")) {
                Frg_Scanner.this.loginpathint = LoginsList.asp.length;
                Frg_Scanner.this.loginpath = LoginsList.asp;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath));
            } else if (str.equals("js")) {
                Frg_Scanner.this.loginpathint = LoginsList.js.length;
                Frg_Scanner.this.loginpath = LoginsList.js;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath));
            } else if (str.equals("cfm")) {
                Frg_Scanner.this.loginpathint = LoginsList.cfm.length;
                Frg_Scanner.this.loginpath = LoginsList.cfm;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath));
            } else if (str.equals("cgi")) {
                Frg_Scanner.this.loginpathint = LoginsList.cgi.length;
                Frg_Scanner.this.loginpath = LoginsList.cgi;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath));
            } else if (str.equals("brf")) {
                Frg_Scanner.this.loginpathint = LoginsList.brf.length;
                Frg_Scanner.this.loginpath = LoginsList.brf;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath));
            } else if (str.equals("mix1")) {
                Frg_Scanner.this.loginpathint = LoginsList.mix1.length;
                Frg_Scanner.this.loginpath = LoginsList.mix1;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath));
            } else if (str.equals("mix2")) {
                Frg_Scanner.this.loginpathint = LoginsList.mix2.length;
                Frg_Scanner.this.loginpath = LoginsList.mix2;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath));
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Frg_Scanner.this.loginpathint; i++) {
                String str2 = Frg_Scanner.this.loginpath[i];
                for (String str3 : strArr2) {
                    try {
                        URL url = new URL(str3.trim() + DialogConfigs.DIRECTORY_SEPERATOR + str2);
                        Log.d("Options->>>>", String.valueOf(str3 + DialogConfigs.DIRECTORY_SEPERATOR + str2));
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setReadTimeout(15000);
                        httpURLConnection.setConnectTimeout(15000);
                        httpURLConnection.connect();
                        if (String.valueOf(httpURLConnection.getResponseCode()).equals("200")) {
                            Frg_Scanner.this.Status = "Found!";
                        } else {
                            Frg_Scanner.this.Status = "Not Found!";
                        }
                        httpURLConnection.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("ERROR", "Ha fallado");
                    }
                    if (Frg_Scanner.this.Status == "Found!") {
                        sb.append(str3 + DialogConfigs.DIRECTORY_SEPERATOR + str2);
                        sb.append(",");
                    }
                    publishProgress(new String[]{str3 + DialogConfigs.DIRECTORY_SEPERATOR + str2, Frg_Scanner.this.Status});
                }
                if (isCancelled()) {
                    break;
                }
            }
            SharedPreferences.Editor edit = Frg_Scanner.this.getActivity().getSharedPreferences("Save200", 0).edit();
            edit.putString("Add200", sb.toString());
            edit.commit();
            return null;
        }

        public void onCancelled() {
            super.onCancelled();
            Frg_Scanner.this.ouput.append(Html.fromHtml("<br/>&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.process_stopped) + "</font><br/>"));
            Frg_Scanner.this.scan.setText("SCAN");
            if (Frg_Scanner.this.notifications.booleanValue()) {
                NotificationBar.notify(Frg_Scanner.this.getActivity(), "Scanning Process", "Cancelled...", R.mipmap.ic_launcher);
            }
        }

        public void onPreExecute() {
            super.onPreExecute();
            if (Frg_Scanner.this.notifications.booleanValue()) {
                NotificationBar.notify(Frg_Scanner.this.getActivity(), "Scanning Process", "Running...", R.mipmap.ic_launcher);
            }
            TextView textView = Frg_Scanner.this.ouput;
            textView.append(Html.fromHtml("<br/><br/>&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.process_site) + "</font><font color='green'>&nbsp;" + Frg_Scanner.this.site.getText() + "</font>"));
            TextView textView2 = Frg_Scanner.this.ouput;
            textView2.append(Html.fromHtml("<br/>&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.process_options) + "</font><font color='green'>&nbsp;" + String.valueOf(Frg_Scanner.this.options.getSelectedItem()) + "</font><br/>"));
            Frg_Scanner.this.ouput.append(Html.fromHtml("<br/>&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.process_started) + "</font><br/><br/>"));
        }

        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            Frg_Scanner.this.ouput.append(Html.fromHtml("<br/>&nbsp;&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.process_finished) + "</font><br/>"));
            Frg_Scanner.this.ouput.append(Html.fromHtml("<br/>&nbsp;&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.cms_results_help) + "</font><br/>"));
            Frg_Scanner.this.scan.setText("SCAN");
            if (Frg_Scanner.this.notifications.booleanValue()) {
                NotificationBar.notify(Frg_Scanner.this.getActivity(), "Scanning Process", "Finished...", R.mipmap.ic_launcher);
            }
        }

        public void onProgressUpdate(String... strArr) {
            super.onProgressUpdate(strArr);
            if (strArr[1] == "Found!") {
                if (Frg_Scanner.this.timestampstatus.booleanValue()) {
                    String TimeStampOn = Utils.TimeStampOn(Frg_Scanner.this.getActivity());
                    TextView textView = Frg_Scanner.this.ouput;
                    textView.append(Html.fromHtml("&nbsp;&nbsp;" + TimeStampOn + ": " + strArr[0] + " ==> " + "<font color='green'>" + getString(R.string.console_results_positive) + "</font><br>"));
                    return;
                }
                TextView textView2 = Frg_Scanner.this.ouput;
                textView2.append(Html.fromHtml("&nbsp;&nbsp;" + strArr[0] + " ==> " + "<font color='green'>" + getString(R.string.console_results_positive) + "</font><br>"));
            } else if (Frg_Scanner.this.timestampstatus.booleanValue()) {
                String TimeStampOn2 = Utils.TimeStampOn(Frg_Scanner.this.getActivity());
                TextView textView3 = Frg_Scanner.this.ouput;
                textView3.append(Html.fromHtml("&nbsp;&nbsp;" + TimeStampOn2 + ": " + strArr[0] + " ==> " + "<font color='red'>" + getString(R.string.console_results_negative) + "</font><br>"));
            } else {
                TextView textView4 = Frg_Scanner.this.ouput;
                textView4.append(Html.fromHtml("&nbsp;&nbsp;" + strArr[0] + " ==> " + "<font color='red'>" + getString(R.string.console_results_negative) + "</font><br>"));
            }
        }
    }

    private class Start2 extends AsyncTask<String, String, Void> {
        public static final int CONNECTION_TIMEOUT = 15000;
        public static final int READ_TIMEOUT = 15000;
        public static final String REQUEST_METHOD = "GET";

        private Start2() {
        }

        public Void doInBackground(String... strArr) {
            String str = strArr[0];
            String str2 = strArr[1];
            if (str.equals("php")) {
                Frg_Scanner.this.loginpathint2 = LoginsList.php.length;
                Frg_Scanner.this.loginpath2 = LoginsList.php;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath2));
            } else if (str.equals("asp")) {
                Frg_Scanner.this.loginpathint2 = LoginsList.asp.length;
                Frg_Scanner.this.loginpath2 = LoginsList.asp;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath2));
            } else if (str.equals("js")) {
                Frg_Scanner.this.loginpathint2 = LoginsList.js.length;
                Frg_Scanner.this.loginpath2 = LoginsList.js;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath2));
            } else if (str.equals("cfm")) {
                Frg_Scanner.this.loginpathint2 = LoginsList.cfm.length;
                Frg_Scanner.this.loginpath2 = LoginsList.cfm;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath2));
            } else if (str.equals("cgi")) {
                Frg_Scanner.this.loginpathint2 = LoginsList.cgi.length;
                Frg_Scanner.this.loginpath2 = LoginsList.cgi;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath2));
            } else if (str.equals("brf")) {
                Frg_Scanner.this.loginpathint2 = LoginsList.brf.length;
                Frg_Scanner.this.loginpath2 = LoginsList.brf;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath2));
            } else if (str.equals("mix1")) {
                Frg_Scanner.this.loginpathint2 = LoginsList.mix1.length;
                Frg_Scanner.this.loginpath2 = LoginsList.mix1;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath2));
            } else if (str.equals("mix2")) {
                Frg_Scanner.this.loginpathint2 = LoginsList.mix2.length;
                Frg_Scanner.this.loginpath2 = LoginsList.mix2;
                Log.d("Options->>>>", String.valueOf(Frg_Scanner.this.loginpath2));
            }
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(str2)));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    int i = 0;
                    while (true) {
                        if (i >= Frg_Scanner.this.loginpathint2) {
                            break;
                        }
                        String str3 = Frg_Scanner.this.loginpath2[i];
                        Log.d("URL->", readLine + DialogConfigs.DIRECTORY_SEPERATOR + str3);
                        try {
                            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(readLine.trim() + DialogConfigs.DIRECTORY_SEPERATOR + str3).openConnection();
                            httpURLConnection.setRequestMethod("GET");
                            httpURLConnection.setReadTimeout(15000);
                            httpURLConnection.setConnectTimeout(15000);
                            httpURLConnection.connect();
                            if (String.valueOf(httpURLConnection.getResponseCode()).equals("200")) {
                                Frg_Scanner.this.Status = "Found!";
                            } else {
                                Frg_Scanner.this.Status = "Not Found!";
                            }
                            httpURLConnection.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("ERROR", "Ha fallado");
                        }
                        if (Frg_Scanner.this.Status == "Found!") {
                            sb.append(readLine + DialogConfigs.DIRECTORY_SEPERATOR + str3);
                            sb.append(",");
                        }
                        if (isCancelled()) {
                            break;
                        }
                        publishProgress(new String[]{readLine + DialogConfigs.DIRECTORY_SEPERATOR + str3, Frg_Scanner.this.Status});
                        i++;
                    }
                }
            } catch (IOException unused) {
            }
            SharedPreferences.Editor edit = Frg_Scanner.this.getActivity().getSharedPreferences("Save200", 0).edit();
            edit.putString("Add200", sb.toString());
            edit.commit();
            return null;
        }

        public void onCancelled() {
            super.onCancelled();
            Frg_Scanner.this.ouput.append(Html.fromHtml("<br/>&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.process_stopped) + "</font><br/>"));
            Frg_Scanner.this.scan.setText("SCAN");
            if (Frg_Scanner.this.notifications.booleanValue()) {
                NotificationBar.notify(Frg_Scanner.this.getActivity(), "Scanning Process", "Cancelled...", R.mipmap.ic_launcher);
            }
        }
        
        public void onPreExecute() {
            super.onPreExecute();
            TextView textView = Frg_Scanner.this.ouput;
            textView.append(Html.fromHtml("<br/>&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.process_file_selected)  + "&nbsp;"  + Frg_Scanner.this.sites.getText() + "</font><br/>"));
            TextView textView2 = Frg_Scanner.this.ouput;
            textView2.append(Html.fromHtml("<br/>&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.process_options) + "</font><font color='green'>&nbsp;" + String.valueOf(Frg_Scanner.this.options2.getSelectedItem()) + "</font><br/>"));
            Frg_Scanner.this.ouput.append(Html.fromHtml("<br/>&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.process_started) + "</font><br/>"));
            if (Frg_Scanner.this.notifications.booleanValue()) {
                NotificationBar.notify(Frg_Scanner.this.getActivity(), "Scanning Process", "Running...", R.mipmap.ic_launcher);
            }
        }

        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            Frg_Scanner.this.ouput.append(Html.fromHtml("<br/>&nbsp;&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.process_finished) + "</font><br/>"));
            Frg_Scanner.this.ouput.append(Html.fromHtml("<br/>&nbsp;&nbsp;<font color='yellow'> [ </font><font color='red'>+</font><font color='yellow'> ] </font><font color='white'>" + getString(R.string.cms_results_help) + "</font><br/>"));
            Frg_Scanner.this.scan.setText("SCAN");
            if (Frg_Scanner.this.notifications.booleanValue()) {
                NotificationBar.notify(Frg_Scanner.this.getActivity(), "Scanning Process", "Finished...", R.mipmap.ic_launcher);
            }
        }

        public void onProgressUpdate(String... strArr) {
            super.onProgressUpdate(strArr);
            if (strArr[1] == "Found!") {
                if (Frg_Scanner.this.timestampstatus.booleanValue()) {
                    String TimeStampOn = Utils.TimeStampOn(Frg_Scanner.this.getActivity());
                    TextView textView = Frg_Scanner.this.ouput;
                    textView.append(Html.fromHtml("&nbsp;&nbsp;" + TimeStampOn + ": " + strArr[0] + " ==> " + "<font color='green'>" + getString(R.string.console_results_positive) + "</font><br>"));
                    return;
                }
                TextView textView2 = Frg_Scanner.this.ouput;
                textView2.append(Html.fromHtml("&nbsp;&nbsp;" + strArr[0] + " ==> " + "<font color='green'>" + getString(R.string.console_results_positive) + "</font><br>"));
            } else if (Frg_Scanner.this.timestampstatus.booleanValue()) {
                String TimeStampOn2 = Utils.TimeStampOn(Frg_Scanner.this.getActivity());
                TextView textView3 = Frg_Scanner.this.ouput;
                textView3.append(Html.fromHtml("&nbsp;&nbsp;" + TimeStampOn2 + ": " + strArr[0] + " ==> " + "<font color='red'>" + getString(R.string.console_results_negative) + "</font><br>"));
            } else {
                TextView textView4 = Frg_Scanner.this.ouput;
                textView4.append(Html.fromHtml("&nbsp;&nbsp;" + strArr[0] + " ==> " + "<font color='red'>" + getString(R.string.console_results_negative) + "</font><br>"));
            }
        }
    }
}

