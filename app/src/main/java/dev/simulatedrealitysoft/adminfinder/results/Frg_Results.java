package dev.simulatedrealitysoft.adminfinder.results;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import dev.simulatedrealitysoft.adminfinder.R;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Frg_Results extends Fragment {
    ListView simpleListView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.frg_results, viewGroup, false);
        setHasOptionsMenu(true);
        final String[] split = getActivity().getSharedPreferences("Save200", 0).getString("Add200", "").split(",");
        simpleListView = (ListView) inflate.findViewById(R.id.simpleListView);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < split.length; i++) {
            HashMap hashMap = new HashMap();
            try {
                hashMap.put("host", new URL(split[i]).getHost());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                hashMap.put("host", "No data Found!");
                simpleListView.setVisibility(8);
                TextView textView = (TextView) inflate.findViewById(R.id.list_empty);
                textView.setVisibility(0);
                textView.setText(getString(R.string.results_no_data));
            }
            hashMap.put("url", split[i]);
            hashMap.put("image", R.drawable.ent_database);
            arrayList.add(hashMap);
        }
        simpleListView.setAdapter(new SimpleAdapter(getActivity(), arrayList, R.layout.frg_result_row, new String[]{"url", "image", "host"}, new int[]{R.id.urlview, R.id.imageView, R.id.hostview}));
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long j) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle(getString(R.string.dialog_results_title));
                    builder.setIcon(R.drawable.ent_database);
                    builder.setItems(new CharSequence[]{getString(R.string.dialog_results_open), getString(R.string.dialog_results_copy)}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        Frg_Results.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(split[i])));
                                        return;
                                    case 1:
                                        ((ClipboardManager) Frg_Results.this.getActivity().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Source Text", split[i]));
                                        Toast.makeText(Frg_Results.this.getActivity(), getString(R.string.toast_results_copy), Toast.LENGTH_SHORT).show();
                                        return;
                                    default:
                                        return;
                                }
                            }
                        });
                    builder.create().show();
                }
            });
        return inflate;
    }

    public void onDestroy() {
        super.onDestroy();
    }
}

