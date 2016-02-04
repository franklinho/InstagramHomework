package com.franklinho.instagramhomework;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by franklinho on 2/3/16.
 */
public class AllCommentsDialog extends DialogFragment {


    public AllCommentsDialog() {

    }

    public static AllCommentsDialog newInstance(ArrayList<Parcelable> comments) {

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
        for (int i =0; i < commentsArray.size(); i++) {
            InstagramComment currentComment =  Parcels.unwrap(commentsArray.get(i));
            comments.add(currentComment);
        }
        View view = inflater.inflate(R.layout.fragment_all_comments, container);
        ListView lvComments = (ListView) view.findViewById(R.id.lvComments);
        InstagramCommentsAdapter aComments = new InstagramCommentsAdapter(getContext(), comments);
        lvComments.setAdapter(aComments);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // Fetch arguments from bundle and set title
//        String title = getArguments().getString("title", "Enter Name");
//        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_all_comments, null);
//
//        return super.onCreateDialog(savedInstanceState);
//    }
}
