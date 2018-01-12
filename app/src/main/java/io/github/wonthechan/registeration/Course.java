package io.github.wonthechan.registeration;

import android.util.Log;

/**
 * Created by YeChan on 2018-01-12.
 */

public class Course {

    String courseID; // 강의 고유 번호 (학수번호)
    char courseUniversity; // 학부 혹은 대학원
    String courseYear; // 해당 년도
    String courseTerm; // 해당 학기
    String courseArea; // 개설 영역
    String courseMajor; // 해당 학과
    String courseGrade; // 해당학년
    String courseTitle; // 강의 제목
    String courseCredit; // 강의 학점
    String coursePersonnel; // 강의 제한 인원
    String courseProfessor; // 강의 교수
    String courseTime; // 강의 시간대 와 강의실

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public char getCourseUniversity() {
        return courseUniversity;
    }

    public void setCourseUniversity(char courseUniversity) {
        this.courseUniversity = courseUniversity;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseArea() {
        return courseArea;
    }

    public void setCourseArea(String courseArea) {
        this.courseArea = courseArea;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public void setCourseMajor(String courseMajor) {
        this.courseMajor = courseMajor;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getCoursePersonnel() {
        return coursePersonnel;
    }

    public void setCoursePersonnel(String coursePersonnel) {
        this.coursePersonnel = coursePersonnel;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }


    public Course(String courseID, char courseUniversity, String courseYear, String courseTerm, String courseArea, String courseMajor, String courseGrade, String courseTitle, String courseCredit, String coursePersonnel, String courseProfessor, String courseTime) {
        Log.e("TAG", "Course 생성자");
        this.courseID = courseID;
        this.courseUniversity = courseUniversity;
        this.courseYear = courseYear;
        this.courseTerm = courseTerm;
        this.courseArea = courseArea;
        this.courseMajor = courseMajor;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.coursePersonnel = coursePersonnel;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
    }
}
