package com.example.irrigationmanegmentapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;

import static com.example.irrigationmanegmentapp.Finals.REQUEST_CODE;
import static com.example.irrigationmanegmentapp.Finals.RESULT_CODE_OK;
import static com.example.irrigationmanegmentapp.Finals.VALVE;

public class vavlesFragment extends Fragment {


    private User mUser;
    private Context mContext;
    private ArrayList<Integer> iconArrayList;
    private GridView valveFragment_LAY_grid;
    private int pressedImage, selectedValvePosition;
    private dialogCallBack mDialogCallBack;


    public vavlesFragment(User user, Context context, dialogCallBack dialogCallBack) {
        this.mContext = context;
        this.mUser = user;
        this.iconArrayList = fillValvePicArr();
        this.pressedImage = -1;
        this.mDialogCallBack = dialogCallBack;
    }

    //Empty constructor
    public vavlesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.valves_fragment, container, false);
        valveFragment_LAY_grid = view.findViewById(R.id.valveFragment_VIEW_grid);
        valveFragment_LAY_grid.setAdapter(new CustomGridAdapter(mContext, mUser.getWaterValveArrayList()));
        valveFragment_LAY_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //If the item is "add new valve"-->dialog is inflated. else-->move to valve activity
                if (((TextView) view.findViewById(R.id.grid_item_label)).getText().equals("Tap To Add")) {
                    createNewValveDialog();
                } else {
                    startValveActivity(ValveActivity.class, position);

                }
            }
        });
        //Long click listener
        valveFragment_LAY_grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (!((TextView) view.findViewById(R.id.grid_item_label)).getText().equals("Tap To Add")) {
                    new AndExAlertDialog.Builder(mContext)
                            .setTitle("Confirmation")
                            .setImage(R.drawable.ic_delete, 20)
                            .setMessage("Are You Sure You Want To Remove The Valve?")
                            .setPositiveBtnText("Yes")
                            .OnPositiveClicked(new AndExAlertDialogListener() {
                                @Override
                                public void OnClick(String input) {
                                    deleteValve(position);
                                    Toast.makeText(mContext, "Valve Removed!", Toast.LENGTH_SHORT).show();
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

                }
                return true;
            }
        });
        return view;
    }

    /**
     * Initiate the icon arrayList
     *
     * @return icon array list
     */
    private ArrayList<Integer> fillValvePicArr() {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(R.drawable.ic_tree);
        arr.add(R.drawable.ic_carrot);
        arr.add(R.drawable.ic_flower);
        arr.add(R.drawable.ic_grass);
        return arr;
    }

    /**
     * Generic method for start new activity
     *
     * @param activity
     * @param position
     */
    private void startValveActivity(Class activity, int position) {
        Intent intent = new Intent(mContext, activity);
        if (activity == ValveActivity.class) {
            intent.putExtra(VALVE, mUser.getWaterValveArrayList().get(position));
            selectedValvePosition = position;
        }
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_CODE_OK) {
                WaterValve v = (WaterValve) data.getSerializableExtra(VALVE);
                mUser.updateValve(v, selectedValvePosition);
                mDialogCallBack.refresh();
            }
        } catch (Exception e) {
            Log.i("problem", e.getStackTrace() + "");
        }
    }

    /**
     * The dialog inflates after pressing the 'Tap to add' button. In this dialog the user choose icon and name for the new valve
     */
    private void createNewValveDialog() {
        //Start alert dialog for creating new water valve
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        View mView = getLayoutInflater().inflate(R.layout.input_dialog_new_valve, null);
        final TextView mValveName = mView.findViewById(R.id.inputDialog_title);
        final GridView gridView = mView.findViewById(R.id.inputDialog_VEW_grid);
        gridView.setAdapter(new NewValveGridAdapter(mContext, iconArrayList));
        //Click on item listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
                view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                if (pressedImage != -1 && pressedImage != position) {
                    gridView.getChildAt(pressedImage).setBackgroundColor(Color.WHITE);
                }
                pressedImage = position;
            }
        });

        mBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pressedImage = -1;
                dialog.dismiss();
            }
        });
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        //The dialog will not dismiss if there is no NAME or no selected ICON
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mValveName.getText().toString().isEmpty()) {
                    mValveName.setError("Give your valve a name!");
                    mValveName.requestFocus();
                    return;
                }
                if (pressedImage == -1) {
                    Toast.makeText(mContext, "Please choose Icon!", Toast.LENGTH_SHORT).show();
                    return;
                }
                createValve(mValveName.getText().toString(), iconArrayList.get(pressedImage));
                Toast.makeText(mContext, "New Valve Created!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                pressedImage = -1;
            }
        });
        //Refresh the fragment and add the new valve icon
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mDialogCallBack.refresh();
            }
        });
    }

    /**
     *  Store the new valve inside the valves arraylist of the user object
     * @param valveName
     * @param icon
     */
    private void createValve(String valveName, int icon) {
        mUser.addNewValveToArrayList(new WaterValve(valveName, icon));
    }

    /**
     * delete the vavle from the valve's arraylist in the user object
     * @param position
     */
    private void deleteValve(int position) {
        mUser.deleteValveFromArrayList(position);
        mDialogCallBack.refresh();

    }
}
