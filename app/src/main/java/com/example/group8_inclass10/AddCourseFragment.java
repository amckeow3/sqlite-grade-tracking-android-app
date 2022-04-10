package com.example.group8_inclass10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.group8_inclass10.databinding.FragmentAddCourseBinding;

public class AddCourseFragment extends Fragment {

    AddCourseFragment.AddCourseFragmentListener mListener;
    FragmentAddCourseBinding binding;
    String grade = "A";
    DatabaseManager dm;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddCourseFragment() {
        // Required empty public constructor
    }


    public static AddCourseFragment newInstance(String param1, String param2) {
        AddCourseFragment fragment = new AddCourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddCourseBinding.inflate(inflater, container, false);

        setupUI();

        return binding.getRoot();
    }

    void setupUI() {
        binding.textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelNewCourse();
            }
        });

        binding.radioGroupCourseGrade.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioButton_A) {
                    grade = "A";
                } else if (checkedId == R.id.radioButton_B) {
                    grade = "B";
                } else if (checkedId == R.id.radioButton_C) {
                    grade = "C";
                } else if (checkedId == R.id.radioButton_D) {
                    grade = "D";
                } else if (checkedId == R.id.radioButton_F) {
                    grade = "F";
                }
            }
        });

        binding.buttonSubmitCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseNumber = binding.editTextCourseNumber.getText().toString();
                String courseName = binding.editTextCourseName.getText().toString();
                String creditHours = binding.editTextHours.getText().toString();

                if (courseNumber == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Course number is required.", Toast.LENGTH_SHORT).show();
                } else if (courseName == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Course name is required.", Toast.LENGTH_SHORT).show();
                } else if (creditHours == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Number of credit hours is required.", Toast.LENGTH_SHORT).show();
                } else {
                    dm = new DatabaseManager(getActivity().getApplicationContext());

                    dm.getGradesDAO().save(new Grade(courseNumber, courseName, creditHours, grade));
                    mListener.addNewCourseAndRefresh();
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Course");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddCourseFragment.AddCourseFragmentListener) context;
    }

    interface AddCourseFragmentListener {
        void cancelNewCourse();
        void addNewCourseAndRefresh();
    }
}