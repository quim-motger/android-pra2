package com.example.quim.bykeapp.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quim.bykeapp.R;
import com.example.quim.bykeapp.entity.Bike;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddBikeFragment.AddBikeListener} interface
 * to handle interaction events.
 * Use the {@link AddBikeFragment#newAddBikeFragment()} factory method to
 * create an instance of this fragment.
 */
public class AddBikeFragment extends Fragment {


    private Button mButton;
    private TextInputEditText mIdentifier;
    private TextInputEditText mContent;

    private AddBikeListener mListener;

    public AddBikeFragment() {
        // Required empty public constructor
    }

    public static AddBikeFragment newAddBikeFragment() {
        AddBikeFragment fragment = new AddBikeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_bike, container, false);

        mButton = view.findViewById(R.id.add_button);
        mIdentifier = view.findViewById(R.id.identifier_edit_text);
        mContent = view.findViewById(R.id.content_edit_text);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mIdentifier.getText().toString().trim();
                String content = mContent.getText().toString().trim();
                if (id.isEmpty() || content.isEmpty()) {
                    if (id.isEmpty()) mIdentifier.setError("Empty");
                    if (content.isEmpty()) mContent.setError("Empty");
                } else {
                    onBikeCreated(new Bike(id, content));
                }
            }
        });

        return view;
    }

    public void onBikeCreated(Bike bike) {
        if (mListener != null) {
            mListener.onAddedBikeListener(bike);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddBikeListener) {
            mListener = (AddBikeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface AddBikeListener {
        // TODO: Update argument type and name
        void onAddedBikeListener(Bike bike);
    }
}
