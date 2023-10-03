package com.example.guardianhelmet;

// EmployeeAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> employeeList;

    public EmployeeAdapter(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.bind(employee);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView employeeNameTextView;
        private TextView workTimeTextView;
        private TextView breakTimeTextView;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeNameTextView = itemView.findViewById(R.id.employeeNameTextView);
            workTimeTextView = itemView.findViewById(R.id.workTimeTextView);
            breakTimeTextView = itemView.findViewById(R.id.breakTimeTextView);
        }

        public void bind(Employee employee) {
            employeeNameTextView.setText(employee.getEmployeeName());
            workTimeTextView.setText("Work Time: " + formatTime(employee.getTotalWorkTime()));
            breakTimeTextView.setText("Break Time: " + formatTime(employee.getTotalBreakTime()));
        }

        private String formatTime(long milliseconds) {
            // Convert milliseconds to seconds, minutes, and hours
            int seconds = (int) (milliseconds / 1000) % 60;
            int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
            int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

            // Format the time as HH:MM:SS
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

    }
}

