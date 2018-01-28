package com.example.queenieliu.smartclassroom.object;

/**
 * Created by Queenie Liu on 2018/1/21.
 */

public class CourseObject  {
    String time,course, classStr,lesson,teacher,background;
    public CourseObject(String course,String classStr,String lesson,String teacher,String time) {
        this.teacher=teacher;
        this.lesson=lesson;
        this.course=course;
        this.time=time;
        this.classStr=classStr;
    }
    public CourseObject(String course,String classStr,String lesson,String teacher,String time,String background) {
        this.teacher=teacher;
        this.lesson=lesson;
        this.course=course;
        this.time=time;
        this.classStr=classStr;
        this.background=background;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setClassStr(String classStr) {
        this.classStr = classStr;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTime() {
        return time;
    }

    public String getCourse() {
        return course;
    }

    public String getClassStr() {
        return classStr;
    }

    public String getLesson() {
        return lesson;
    }

    public String getTeacher() {
        return teacher;
    }
}
