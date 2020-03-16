package com.example.irrigationmanegmentapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;

public class ValveScheduleFragment extends Fragment {

    private GridView Schedile_VIEW_grid;
    private Context mContext;
    private List<IrrigationPlan> mIrrigationSchedule;
    private dialogCallBack mDialogCallBack;

    public ValveScheduleFragment(List<IrrigationPlan> IrrigationSchedule, Context context, dialogCallBack dialog) {
        this.mIrrigationSchedule = IrrigationSchedule;
        this.mContext = context;
        this.mDialogCallBack = dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        if (mIrrigationSchedule != null) {
            Schedile_VIEW_grid = view.findViewById(R.id.Schedile_VIEW_grid);
            Schedile_VIEW_grid.setAdapter(new SchedulesGridAdapter(mContext, mIrrigationSchedule));

            //Long click listener for deleting irrigation plan
            Schedile_VIEW_grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    new AndExAlertDialog.Builder(mContext)
                            .setTitle("Confirmation")
                            .setImage(R.drawable.ic_delete, 20)
                            .setMessage("Are You Sure You Want To Delete?")
                            .setPositiveBtnText("Yes")
                            .OnPositiveClicked(new AndExAlertDialogListener() {
                                @Override
                                public void OnClick(String input) {
                                    deleteValve(position);
                                    Toast.makeText(mContext, "Irrigation Plan Deleted!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeBtnText("No")
                            .OnNegativeClicked(new AndExAlertDialogListener() {
                                @Override
                                public void OnClick(String input) {

                                }
                            })
                            .setCancelableOnTouchOutside(false)
                            .build();


                    return true;
                }
            });
        }
        return view;
    }

    /**
     * Delete the irrigation plan from the ArrayList of plans
     * @param position
     */
    private void deleteValve(int position) {
        mIrrigationSchedule.remove(position);
        //Refresh the fragment
        mDialogCallBack.refresh();
    }
}
