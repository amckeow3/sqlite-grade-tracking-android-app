package com.example.group8_inclass10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group8_inclass10.databinding.CourseLineItemBinding;
import com.example.group8_inclass10.databinding.FragmentGradesBinding;

import java.util.ArrayList;

public class GradesFragment extends Fragment {

    private static final String TAG = "grades fragment";
    GradesFragment.GradesFragmentListener mListener;
    FragmentGradesBinding binding;
    Double gradePoints;
    Double totalGradePoints = 0.0;
    Double totalCreditHours = 0.0;
    Double calculatedGPA = 0.0;

    DatabaseManager dm;
    ArrayList<Grade> grades = new ArrayList<>();
    ArrayList<Double> gradePointsArray = new ArrayList<>();

    CourseListAdapter courseListAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GradesFragment() {
        // Required empty public constructor
    }

    public static GradesFragment newInstance(String param1, String param2) {
        GradesFragment fragment = new GradesFragment();
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

        dm = new DatabaseManager(getActivity().getApplicationContext());

        Log.d(TAG, "onCreate: " + dm.getGradesDAO().getAll());

        grades.clear();
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGradesBinding.inflate(inflater, container, false);
        Log.d(TAG, "onCreateView: ");

        totalCreditHours = 0.0;
        totalGradePoints = 0.0;
        calculatedGPA = 0.0;

        setupUI();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        getActivity().setTitle("Grades");

        totalCreditHours = 0.0;
        totalGradePoints = 0.0;
        calculatedGPA = 0.0;

        setupUI();
    }


    class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder> {
        ArrayList<Grade> mGrades;

        public CourseListAdapter(ArrayList<Grade> data) {
            this.mGrades = data;
        }

        @NonNull
        @Override
        public CourseListAdapter.CourseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CourseLineItemBinding binding = CourseLineItemBinding.inflate(getLayoutInflater(), parent, false);
            return new CourseListAdapter.CourseListViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseListAdapter.CourseListViewHolder holder, int position) {
            Grade grade = mGrades.get(position);
            holder.setupUI(grade);
        }

        @Override
        public int getItemCount() {
            return this.mGrades.size();
        }

        public class CourseListViewHolder extends RecyclerView.ViewHolder {
            CourseLineItemBinding mBinding;
            Grade mGrade;
            int position;


            public CourseListViewHolder(@NonNull CourseLineItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Grade grade) {
                mGrade = grade;
                mBinding.textViewCourse.setText(mGrade.courseNumber);
                mBinding.textViewLetterGrade.setText(mGrade.grade);
                mBinding.textViewCourseName.setText(mGrade.course);
                mBinding.textViewCourseCreditHours.setText(mGrade.creditHours + " Credit Hours");

                mBinding.imageViewTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dm = new DatabaseManager(getActivity().getApplicationContext());
                        dm.getGradesDAO().delete(mGrade);
                        mListener.deleteAndRefresh();
                    }
                });
            }
        }
    }

    void createRecyclerView() {
        grades.clear();
        grades.addAll(dm.getGradesDAO().getAll());
        recyclerView = binding.recyclerViewCourses;
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        courseListAdapter = new CourseListAdapter(grades);
        recyclerView.setAdapter(courseListAdapter);
    }

    void setupUI() {
        grades.clear();

        createRecyclerView();
        calculateGPA(grades);

        binding.imageViewAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToAddCourse();
            }
        });
    }


    public Double calculateGradePoints (Grade grade) {
        if (grade.grade.matches("A")) {
            return gradePoints = 4.0;
        } else if (grade.grade.matches("B")) {
            return gradePoints = 3.0;
        } else if (grade.grade.matches("C")) {
            return gradePoints = 2.0;
        } else if (grade.grade.matches("D")) {
            return gradePoints = 1.0;
        } else {
            return gradePoints = 0.0;
        }
    }

    void calculateGPA(ArrayList<Grade> gradesArray) {
        Double gpa = 0.0;
        Double points;
        gradePointsArray.clear();

        for (Grade grade : gradesArray) {
           points = calculateGradePoints(grade);
           Log.d(TAG, "points = " + points);

           totalGradePoints += Double.valueOf(grade.getCreditHours()) * points;
           totalCreditHours += Double.valueOf(grade.getCreditHours());
        }

        calculatedGPA = totalGradePoints / totalCreditHours;

        binding.textViewGPA.setText("GPA: " + calculatedGPA);
        binding.textViewTotalHours.setText("Hours: " + totalCreditHours);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (GradesFragment.GradesFragmentListener) context;
    }

    interface GradesFragmentListener {
        void goToAddCourse();
        void deleteAndRefresh();
    }
}