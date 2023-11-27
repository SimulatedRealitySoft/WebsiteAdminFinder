package dev.simulatedrealitysoft.adminfinder.about;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import dev.simulatedrealitysoft.adminfinder.LicensesActivity;
import dev.simulatedrealitysoft.adminfinder.R;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Frg_About extends Fragment implements View.OnClickListener {
    ListView simpleListView;
    ImageView iv_srs;
    
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        View inflate = layoutInflater.inflate(R.layout.frg_about, viewGroup, false);
        setHasOptionsMenu(true);

        iv_srs = inflate.findViewById(R.id.iv_srs);

        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(iv_srs, "translationX", 0f, 16f);
        objectAnimatorX.setDuration(1000);
        objectAnimatorX.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimatorX.setRepeatMode(ObjectAnimator.REVERSE);
        objectAnimatorX.start();

        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(iv_srs, "translationY", 0f, 19f);
        objectAnimatorY.setDuration(1000);
        objectAnimatorY.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimatorY.setRepeatMode(ObjectAnimator.REVERSE);
        objectAnimatorY.start();

        RelativeLayout cv_developer = inflate.findViewById(R.id.rl_developer);
        LinearLayout cv_about_github = inflate.findViewById(R.id.cv_about_github);
        LinearLayout cv_about_source_code = inflate.findViewById(R.id.cv_about_source_code);
        LinearLayout cv_about_licenses = inflate.findViewById(R.id.cv_about_licenses);
        LinearLayout cv_about_app = inflate.findViewById(R.id.cv_about_app);

        cv_about_github.setOnClickListener(this);
        cv_about_source_code.setOnClickListener(this);
        cv_about_licenses.setOnClickListener(this);
        cv_about_app.setOnClickListener(this);

        cv_developer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
					Toast.makeText(getActivity(), getString(R.string.you), Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        
        return inflate;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getActivity() != null) {
                getActivity().finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.cv_about_github:
                intent.setData(Uri.parse("https://github.com/SimulatedRealitySoft"));
                intent.setAction(Intent.ACTION_VIEW);
                if (getActivity() != null) {
                    getActivity().startActivity(intent);
                }
                break;

            case R.id.cv_about_source_code:
                intent.setData(Uri.parse("https://github.com/simulatedRealitySoft/WebsiteAdminFinder"));
                intent.setAction(Intent.ACTION_VIEW);
                if (getActivity() != null) {
                    getActivity().startActivity(intent);
                }
                break;

            case R.id.cv_about_licenses:
                Intent intent_licenses = new Intent(getActivity(), LicensesActivity.class);
                startActivity(intent_licenses);
                break;
            case R.id.cv_about_app:
                AlertDialog.Builder builder = new AlertDialog.Builder(Frg_About.this.getActivity());
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage(getString(R.string.about_dialog)).setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(R.color.bgCardView);

                dialog.show();
                
                break;
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }
    
    public void dialogAboutApp(){
        
    }
}
