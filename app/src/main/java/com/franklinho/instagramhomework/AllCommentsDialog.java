package com.franklinho.instagramhomework;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by franklinho on 2/3/16.
 */

//Dialog fragment that displays all comments
public class AllCommentsDialog extends DialogFragment {
    @Bind(R.id.lvComments) ListView lvComments;

    public AllCommentsDialog() {

    }

    public static AllCommentsDialog newInstance(ArrayList<Parcelable> comments) {
        //Receive arraylist of Parcelable comments and set as arguments for fragment
        AllCommentsDialog frag = new AllCommentsDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList("comments",comments);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ArrayList<Parcelable> commentsArray = getArguments().getParcelableArrayList("comments");
        ArrayList<InstagramComment> comments = new ArrayList<>();
        //Unpack arraylist of Parcelables
        for (int i =0; i < commentsArray.size(); i++) {
            InstagramComment currentComment =  Parcels.unwrap(commentsArray.get(i));
            comments.add(currentComment);
        }
        View view = inflater.inflate(R.layout.fragment_all_comments, container);
        ButterKnife.bind(this,view);

        InstagramCommentsAdapter aComments = new InstagramCommentsAdapter(getContext(), comments);
        lvComments.setAdapter(aComments);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        //Requesting window with no title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }
}
