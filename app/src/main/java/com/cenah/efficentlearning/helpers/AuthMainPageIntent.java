package com.cenah.efficentlearning.helpers;

import android.app.Activity;
import android.content.Intent;

import com.cenah.efficentlearning.admin.activities.AdminHomeActivity;
import com.cenah.efficentlearning.models.UserRole;
import com.cenah.efficentlearning.student.activities.StudentHomeActivity;
import com.cenah.efficentlearning.teacher.activites.TeacherHomeActivity;

public class AuthMainPageIntent {

    private UserRole userRole;
    private Activity context;
    public AuthMainPageIntent(UserRole userRole, Activity context) {
        this.userRole = userRole;
        this.context = context;
    }

    public void Page(){
        if(userRole.getRoleName().equals("Admin")){
            context.startActivity(new Intent(context, AdminHomeActivity.class));
            context.finish();
        }else if(userRole.getRoleName().equals("Student")){
            context.startActivity(new Intent(context, StudentHomeActivity.class));
            context.finish();
        }else{
            context.startActivity(new Intent(context, TeacherHomeActivity.class));
            context.finish();
        }


    }
}
